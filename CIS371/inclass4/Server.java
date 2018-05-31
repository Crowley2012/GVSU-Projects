/******************************************
 * CIS 371
 * In class 4
 *
 * Sean Crowley
 *
 * This class will run a basic web server
 * That will return a dynamic page
 * 	(ls-color)
 ******************************************/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
	private static boolean command;
	private static DataOutputStream os;

	public static void main(String[] args) throws IOException {

		// Port to listen on
		int port = 8080;
		String file = "";
		os = null;
		command = false;

		try {
			// Create server socket on port
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Listening on port " + port + "\n");

			while (true) {
				// Accept incoming connections
				Socket clientSocket = serverSocket.accept();

				// Setup input and output streams
				os = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				// Stores file information
				String inputLine;
				String info[];

				// Reads the header
				while ((inputLine = in.readLine()) != null) {
					// Breaks loop after end of header
					if (inputLine.equals("")) {
						break;
					}

					// Prints header to console
					System.out.println("From Client : " + inputLine);

					// Parses the GET line
					if (inputLine.contains("GET")) {
						info = inputLine.split(" ");
						file = info[1];
					}
				}

				File fileSize = null;

				fileSize = new File(file.substring(1));

				if (!fileSize.exists()) {
					file = file.substring(1);
					info = file.split("-");

					buildHTML(info[0], info[1]);
					fileSize = new File("command.html");
					file = "command.html";
				}

				InputStream fis = null;

				// Reads the requested file
				fis = new FileInputStream(fileSize);
				// Gets the file extension
				info = file.split("\\.");

				// Creates return header
				os.writeBytes("HTTP/1.1 200 OK" + "\n");

				if (info.length > 1 && (info[1].equals("txt") || info[1].equals("html"))) {
					os.writeBytes("Content-Type: text/" + info[1] + "\n");
					System.out.println("Content-Type: text/" + info[1] + " : " + fileSize.length() + "\n");
					os.writeBytes("Content-Length: " + fileSize.length() + "\n");
				} else {
					os.writeBytes("Content-Type: image/" + info[1] + "\n");
					System.out.println("Content-Type: image/" + info[1] + " : " + fileSize.length() + "\n");
					os.writeBytes("Content-Length: " + fileSize.length() + "\n");
				}

				os.writeBytes("Connection: close");
				os.writeBytes("\r\n\r\n");
				
				byte[] buffer = new byte[1024];
				int i;
				while ((i = fis.read(buffer)) > -1) {
					os.write(buffer, 0, i);
					buffer = new byte[1024];
				}
				fis.close();

				os.flush();
				os.close();

				clientSocket.close();

			}

		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	public static List<String> runCommand(String command) {

		// place to store the output
		ArrayList<String> lines = new ArrayList<String>();

		try {
			// Launch "command" as an external process
			Process p = Runtime.getRuntime().exec(command);

			// Grab each line generated and place it in a List
			Scanner input = new Scanner(p.getInputStream());
			while (input.hasNext()) {
				lines.add(input.nextLine());
			}
		} catch (IOException e) {
			lines.add("There was a problem: " + e);
		}
		return lines;
	}

	public static void buildHTML(String command, String color) {

		try {
			PrintWriter pw = new PrintWriter("command.html", "UTF-8");

			pw.print("<html>\n");
			pw.print("<head>\n");
			pw.print("<title>Command : " + command + "</title>\n");
			pw.print("<style type=\"text/css\">\n");
			pw.print("th, td { margin-right: 10px; padding-right: 10px;}\n");
			pw.print("th, td { text-align: left}\n");
			pw.print("</style>\n");
			pw.print("</head>\n");
			pw.print("<body" + " bgcolor=\"" + color + "\"" + ">\n");
			pw.print("<h1>Command : " + command + "</h1>\n");
			for (String line : runCommand(command)) {
				pw.print("<p>" + line + "<p>");
			}
			pw.print("</body>\n");
			pw.print("</html>\n");

			pw.close();
		} catch (Exception e) {
			System.out.println("ERROR");
		}
	}
}
