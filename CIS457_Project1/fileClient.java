/*********************************************************
 * Sean Crowley
 * Morrison Cummingham 
 * 
 * Project 2b
 * CIS 457
 * 10/6/2015
 * 
 * FileClient connects the server and allows input from
 *  the user such as: send "filename" "savename"
 *********************************************************/

import java.io.*;
import java.net.Socket;

public class fileClient {

	// Initial file size
	public final static int FILE_SIZE = 6022386;

	public static void main(String[] args) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;
		DataOutputStream outToServer = null;
		BufferedReader inFromServer = null;
		BufferedReader inFromUser = null;
		String message = null;
		String fileName = null;
		String serverMessage = null;

		// Whether to stop client or not
		boolean exit = false;
		int bytesRead;
		int current = 0;

		try {
			// Creates connection given IP and port from user
			String ip = getIP();
			int port = getPort();
			sock = new Socket(ip, port);
			System.out.println("Connecting...");

			while (!exit) {
				// Data Streams
				outToServer = new DataOutputStream(sock.getOutputStream());
				inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				inFromUser = new BufferedReader(new InputStreamReader(System.in));

				// Gets command from user
				System.out.println("Available commands: 'exit' 'send filename savename'");
				System.out.println("Enter a message: ");
				message = inFromUser.readLine();

				// Closes connection if exit is sent
				if (message.equals("exit")) {
					exit = true;
				}

				// Gets the savename of file and stores in fileName
				for (int i = 5; i < message.length(); i++) {
					if (message.charAt(i) == ' ') {
						fileName = message.substring(i + 1, message.length());
						message = message.substring(0, i);
						break;
					}
				}

				// Send message to server
				outToServer.writeBytes(message + '\n');

				// Relayed message from server
				serverMessage = inFromServer.readLine();
				System.out.println("Got from server: " + serverMessage);

				// Receive file
				if (message.length() > 4) {
					if (message.substring(0, 4).toLowerCase().equals("send")) {
						byte[] mybytearray = new byte[FILE_SIZE];
						is = sock.getInputStream();
						fos = new FileOutputStream(fileName);
						bos = new BufferedOutputStream(fos);
						bytesRead = is.read(mybytearray, 0, mybytearray.length);
						current = bytesRead;

						// Reads in the bytes from server and rebuilds the file
						do {
							bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
							if (bytesRead >= 0) {
								current += bytesRead;
							}
						} while (bytesRead > -1);

						// Flushes the file to the system
						try {
							bos.write(mybytearray, 0, current);
							bos.flush();
							System.out.println("File " + fileName + " downloaded (" + current + " bytes read)");
						} catch (Exception e) {
							System.out.println("File does not exist!");
						}
						
						sock = new Socket(ip, port);
					}
				}
			}
			// Closes the connection
		} finally {
			if (fos != null)
				fos.close();
			if (bos != null)
				bos.close();
			if (sock != null)
				sock.close();
		}
	}

	// Returns a port to connect to
	private static int getPort() {
		int port = 0;
		boolean valid = true;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter a port to connect: ");

		// Checks if port is valid
		while (valid) {
			try {
				port = Integer.parseInt(input.readLine());

				if (port > 1024 && port < 65536) {
					valid = false;
				} else {
					System.out.println("Invalid Port. \nEnter a port to watch: ");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("connecting to port: " + port);

		return port;
	}

	// Returns an IP to connect to
	private static String getIP() {
		String ip = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter an IP to connect: ");

		try {
			ip = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ip;
	}
}
