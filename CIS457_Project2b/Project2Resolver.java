import java.io.*;
import java.net.*;
import java.util.*;

/********************************************************************************
 * CIS 457 Project 2 Recursive DNS caching resolver
 *
 *
 * @author Sean Crowley
 * @author Morrison Cunningham
 * @author Joel Truman
 * @version Fall 2015
 ********************************************************************************/

public class Project2Resolver {

	/* Port value used if user input is invalid */
	static int DEFAULT_PORT = 8025;

	/* Stores results from root servers */
	static ArrayList<String> results;

	/* Stores the final answers */
	static ArrayList<String> answers;

	/* Stores all answers as a hashmap */
	static HashMap<String, ArrayList<String>> cache = new HashMap<String, ArrayList<String>>();

	/* Stores all the dates as a hashmap */
	static HashMap<String, Date> dateMap = new HashMap<String, Date>();

	/**************************************************************************
	 * Main method that prompts user input and opens a DatagramSocket
	 **************************************************************************/
	public static void main(String[] args) throws IOException {
		while (true) {
			String userInput;
			int socketNum;
			DatagramSocket serverSocket = null;

			// Read command line argument
			if (args.length > 0)
				userInput = args[0];
			else
				userInput = "8025";

			// Check for valid argument
			if (isInteger(userInput))
				socketNum = Integer.parseInt(userInput);
			else {
				System.out.printf("The port you specified was not an integer. A default value of %d has been substituted.\n", DEFAULT_PORT);
				socketNum = DEFAULT_PORT;
			}

			// Create a socket that listens on port designated by user.
			try {
				serverSocket = new DatagramSocket(socketNum);
				// Set socket timeout to 20 seconds
				serverSocket.setSoTimeout(20000);
			} catch (IOException e) {
				System.out.printf("The port you specified cannot be used. Please launch the application again.\n");
				System.exit(1);
			}

			System.out.printf("\nDNS resolver started on port %d\n", socketNum);

			// Set server to listen for a DatagramPacket from client
			byte[] receiveData = new byte[1024];
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);

			// Handle server timeouts
			try {
				serverSocket.receive(packet);

				// Print packet info from client using helper class PacketInfo()
				PacketInfo information = new PacketInfo(packet);
				information.getValues();
				information.getQuestions();
				information.getAnswers();
				information.getAuthority();
				System.out.printf("\nReceived query from client for %s\n", information.getNameRequested());

				// Check for question type A and class type IN before proceeding
				if (!information.getValidQuestion()) {
					information.setErrorCode();
					System.out.println("\nSending answer to client\n");

					// send error code back to client
					InetAddress retAddress = InetAddress.getByName("127.0.0.1");
					int port = packet.getPort();
					DatagramPacket toClient = new DatagramPacket(information.getByteArray(), information.getByteArray().length, retAddress, port);
					serverSocket.send(toClient);
				}

				// Send DatagramPacket down server tree with recursion desired bit unset
				information.unsetRecursion();
				String nextServer = "198.41.0.4";
				boolean doneSearching = false;
				boolean error = false;
				InetAddress address;
				DatagramPacket response;
				PacketInfo responseInfo = null;

				// Check if query is in cache
				if (cache.get(information.getNameRequested()) != null) {
					System.out.println("\nResults are in the cache!\n\nPrinting Answers:");
					answers = cache.get(information.getNameRequested());

					for (String s : answers) {
						System.out.println(s);
					}

					System.out.println("\nSending answer to client");

					byte[] fromServer = new byte[1024];
					response = new DatagramPacket(fromServer, fromServer.length);
					serverSocket.receive(response);
					responseInfo = new PacketInfo(response);

					responseInfo.getValues();
					responseInfo.getQuestions();
					responseInfo.getAnswers();
					responseInfo.getAuthority();
					responseInfo.getAdditional();
				} else {
					// Loop until an answer is found or there is an error
					while (!doneSearching && !error) {
						address = InetAddress.getByName(nextServer);
						DatagramPacket sendPacket = new DatagramPacket(information.getByteArray(), information.getByteArray().length, address, 53);
						serverSocket.send(sendPacket);

						byte[] fromServer = new byte[1024];
						response = new DatagramPacket(fromServer, fromServer.length);
						serverSocket.receive(response);

						responseInfo = new PacketInfo(response);

						System.out.printf("\nQuerying server %s\n", nextServer);
						// read information in received packet
						responseInfo.getValues();
						responseInfo.getQuestions();
						responseInfo.getAnswers();
						responseInfo.getAuthority();
						responseInfo.getAdditional();

						System.out.printf("Received answer: %b\n", responseInfo.isAnswer());
						error = responseInfo.isError();
						doneSearching = responseInfo.isAnswer();
						nextServer = responseInfo.nextIP();
						results = responseInfo.getResults();

						if (!doneSearching) {
							System.out.println("Authority records found:");
							for (int i = 0; i < results.size(); i++) {
								System.out.println(results.get(i));
							}
						}
						if (nextServer.equals("")) {
							error = true;
							responseInfo.setErrorCode();
						}
					}

					// Send answer back to client
					answers = responseInfo.getAnswerRecords();
					System.out.println("Answers found:");

					for (int i = 0; i < answers.size(); i++) {
						System.out.println(answers.get(i));
					}
					System.out.println("\nSending answer to client");
				}

				// Send answer to client
				address = InetAddress.getByName("127.0.0.1");
				int port = packet.getPort();
				DatagramPacket toClient = new DatagramPacket(responseInfo.getByteArray(), responseInfo.getByteArray().length, address, port);
				serverSocket.send(toClient);
				System.out.println("Answer sent successfully!\n");

				// Add answers to cache
				cache.put(information.getNameRequested(), answers);

				// Add ttl to date hash map
				Date ttl = new Date();

				// Add ttl from packetInfo
				ttl = new Date(ttl.getTime() + information.getTTL());

				// Add ttl to hashmap
				dateMap.put(information.getNameRequested(), ttl);
			} catch (Exception e) {
				System.out.println("\nNo connections made, checking TTL");
				serverSocket.close();
			}

			// Print elements in cache
			printCache();
			//printTimes();

			// Close the socket
			serverSocket.close();

			// Check for expired answers in hash map
			checkTTL();
		}
	}

	/*********************************************************
	 * Method to check if user generated input is an integer
	 **********************************************************/
	public static boolean isInteger(String str) {

		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/*********************************************************
	 * Method to print the contents of the cache
	 **********************************************************/
	public static void printCache() {
		System.out.println("Printing Cache\n" + cache.toString() + "\n");
	}

	/*********************************************************
	 * Method to print the contents of the cache
	 **********************************************************/
	public static void printTimes() {
		System.out.println("Printing Times\n" + dateMap.toString() + "\n");
	}

	/*********************************************************
	 * Method to check the TTL in cache and remove expired
	 **********************************************************/
	public static void checkTTL() {
		Date current = new Date();

		for (String key : cache.keySet()) {
			if (current.after(dateMap.get(key))) {
				dateMap.remove(key);
				cache.remove(key);
			}
		}
	}
}