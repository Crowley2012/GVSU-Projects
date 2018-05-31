/*********************************************************
 * Sean Crowley
 * Morrison Cummingham 
 * 
 * Project 2b
 * CIS 457
 * 10/6/2015
 * 
 * Worker handles a client connection
 *********************************************************/

import java.io.*;
import java.net.*;

public class worker implements Runnable {

	// Connected socket
	private Socket client;

	// Name of file to send
	String fileNames = "";

	// Constructor
	worker(Socket client) {
		this.client = client;
	}

	public void run() {
		try {
			BufferedReader inFromClient = null;
			DataOutputStream outToClient = null;
			DataOutputStream fileToClient = null;
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			String clientMessage = null;
			File myFile = null;

			// Whether to close connection or not
			boolean exit = false;

			// Prints new connection info
			System.out.println("New connection : " + client);

			while (!exit) {
				// Get Inputs and Data Streams
				inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
				outToClient = new DataOutputStream(client.getOutputStream());
				fileToClient = new DataOutputStream(client.getOutputStream());
				clientMessage = inFromClient.readLine();

				System.out.println("The client said " + clientMessage);

				// Simulate Computation Time 15s
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					System.out.println("Interrupted Exception");
					System.exit(-1);
				}

				// Repeat sent message from client
				outToClient.writeBytes(clientMessage + '\n');

				// Check if client wants to close connection
				if (clientMessage.equals("exit")) {
					System.out.println("Client Disconnected\nClosing Connection");
					exit = true;
					client.close();
					System.exit(1);
				}

				// Checks if message sent contains the word 'send'
				if (clientMessage.length() > 4) {
					if (clientMessage.substring(0, 4).toLowerCase().equals("send")) {
						// Breaks message up to get file name
						fileNames = clientMessage.substring(5, clientMessage.length());

						// File to be sent
						myFile = new File(fileNames);

						// Checks if file exists
						if (myFile.exists()) {
							// Array of bytes to be sent
							byte mybytearray[] = new byte[(int) myFile.length()];

							fis = new FileInputStream(myFile);
							bis = new BufferedInputStream(fis);
							bis.read(mybytearray, 0, mybytearray.length);

							System.out.println("Sending " + fileNames + "(" + mybytearray.length + " bytes)");

							// Flushes Output Stream
							fileToClient.write(mybytearray, 0, mybytearray.length);
							fileToClient.flush();

							System.out.println("File sent.");

							break;
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Closes connection
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
