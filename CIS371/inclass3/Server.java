import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {

		if (args.length != 0) {
			System.err.println("Usage: java EchoServer");
			System.exit(1);
		}

		int port = 8080;
		String file = "";

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Listening on port " + port + "\n");
			Socket clientSocket = serverSocket.accept();

			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("")) {
					break;
				}
				System.out.println("From Client : " + inputLine);
				if(inputLine.contains("GET")){
					file = inputLine.;
				}
			}

			out.print("HTTP/1.1 200 OK");
			out.print("Content-Type: text/plain");
			out.print("Content-Length: 70");
			out.print("Connection: close");
			out.print("\r\n\r\n");
			out.print("This is not the real content because this server is not yet complete.");
			out.print("\r\n\r\n");
			out.flush();

			serverSocket.close();

		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
