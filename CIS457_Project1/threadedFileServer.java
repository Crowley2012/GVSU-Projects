/*********************************************************
 * Sean Crowley
 * Morrison Cummingham 
 * 
 * Project 2b
 * CIS 457
 * 10/6/2015
 * 
 * ThreadedFileServer creates a connection to client
 * 	and spawns a thread of worker
 *********************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class threadedFileServer {

	public static void main(String[] args) throws IOException {
		// Max concurrent connections
		int maxConnections = 10;
		ServerSocket servsock = null;
		Socket sock = null;
		boolean running = true;

		// Port to watch for conenctions
		servsock = new ServerSocket(getPort(), maxConnections);

		while (running) {
			System.out.println("Waiting...");
			// Accepts connections
			sock = servsock.accept();

			// Creates a new thread
			Thread t = new Thread(new worker(sock));
			// Starts the thread
			t.start();
		}

		// Closes connection
		servsock.close();
	}

	// Returns a port to watch
	private static int getPort() {
		int port = 0;
		boolean valid = true;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter a port to watch: ");

		// Checks if port entered is valid
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
		System.out.println("Watching on port: " + port);

		return port;
	}
}
