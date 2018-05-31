import java.io.*;
import java.net.*;

class udpclient {
	public static void main(String args[]) throws Exception {
		
		/*******************************
		 * Sending packet to server
		 *******************************/
		@SuppressWarnings("resource")
		// Create a socket connection
		DatagramSocket clientSocket = new DatagramSocket();

		// Get input from user
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter a message: ");
		String message = inFromUser.readLine();

		// Create byte array to store data to send
		byte[] sendData = new byte[1024];

		// Stores input from user as byte array
		sendData = message.getBytes();

		// Internet address IP to connect to
		InetAddress IPAddress = InetAddress.getByName("127.0.0.1");

		// Port to connect to
		int port = 9876;

		// Creates a packet connection that can send multiple packets
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

		// Sends packet through socket
		clientSocket.send(sendPacket);
		
		/*******************************
		 * Receiving packet from server
		 *******************************/
		// Data received from server
		byte[] receiveData = new byte[1024];

		// Creates an empty packet to store received packet
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		// Retrieves the packet from server
		clientSocket.receive(receivePacket);

		// Prints data in packet
		String serverMessage = new String(receivePacket.getData());
		System.out.println("Got from server: " + serverMessage);
	}
}
