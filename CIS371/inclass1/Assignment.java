/************************************************************
* Sean Crowley
* CS 371
* In Class 1: Write a basic web client transaction
************************************************************/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Assignment {

	public static void main(String[] args) throws IOException {

		// Checks number of arguments
		if (args.length != 1) {
			System.err.println("Usage: java Client <host>");
			System.exit(1);
		}

		// Port to connect
		int port = 80;

		// Website
		String site = args[0];

		// Split the web address
		int split = site.indexOf('/');
		if (split < 0) {
			site = site + "/";
			split = site.indexOf('/');
		}

		// Seperated file and host
		String file = site.substring(split);
		String host = site.substring(0, split);

		try {
			// Establishes connection
			Socket socket = new Socket(host, port);
			
			//Data streams 
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream, false);
			InputStream inputStream = socket.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			BufferedWriter fileout = null;

			// Send get request
			printWriter.print("GET " + file + " HTTP/1.0\r\n");
			printWriter.print("Accept: text/plain, text/html, text/*\r\n");
			printWriter.print("\r\n");
			printWriter.flush();

			//True if still in header
			boolean header = true;
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				//Checks for end of header
				if (line.trim().equals("")) {
					header = false;
				}

				//Prints header to console and body to file
				if (header) {
					System.out.println(line);
				} else {
					fileout = new BufferedWriter(new FileWriter("output.txt", true));
					fileout.write(line + "\n");
					fileout.close();
				}
			}

			//Closes socket
			socket.close();

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + site);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + site);
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Error : " + e);
			System.exit(1);
		}
	}
}
