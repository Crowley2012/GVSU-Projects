import java.io.*;
import java.net.*;

class testClient {
	public static void main(String args[]) throws Exception {
			Socket clientSocket = new Socket("127.0.0.1", 5555);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter a message: ");
			String message = inFromUser.readLine();
			outToServer.writeBytes(message + '\n');
			String serverMessage = inFromServer.readLine();
			System.out.println("Got from server: " + serverMessage);
			clientSocket.close();
	}
}