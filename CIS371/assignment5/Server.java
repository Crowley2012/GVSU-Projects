/******************************************
 * CIS 371
 * Assignment 5
 *
 * Sean Crowley
 * Vignesh Suresh
 *
 * This class will run a basic web server
 ******************************************/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {

		//Port to listen on
		int port = 8080;
		String file = "";

		try {
			//Create server socket on port
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Listening on port " + port + "\n");

			//Accept incoming connections
			Socket clientSocket = serverSocket.accept();

			//Setup input and output streams
			DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			//Stores file information
			String inputLine;
			String info[];

			//Reads the header
			while ((inputLine = in.readLine()) != null) {
				//Breaks loop after end of header
				if (inputLine.equals("")) {
					break;
				}
				
				//Prints header to console
				System.out.println("From Client : " + inputLine);
				
				//Parses the GET line
				if (inputLine.contains("GET")) {
					info = inputLine.split(" ");
					file = info[1];
				}
			}

			//Reads the requested file
			FileInputStream fis = new FileInputStream(file.substring(1));

			//Gets the file extension
			info = file.split("\\.");

			//Creates return header
			os.writeBytes("HTTP/1.1 200 OK");
			os.writeBytes("Content-Type: image/" + info[1] + " text/" + info[1] + " text/plain");
			os.writeBytes("Content-Length: 70");
			os.writeBytes("Connection: close");
			os.writeBytes("\r\n\r\n");

			//Sends the file to client
			int i;
			while ((i = fis.read()) > -1)
				os.write(i);

			os.writeBytes("\r\n\r\n");
			os.flush();

			fis.close();
			os.close();
			serverSocket.close();

		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
