import java.net.*;
import java.util.*;
import java.nio.*;

public class UDPServer {

	public static ArrayList<String> rootServers = new ArrayList<String>();
	public static ArrayList<String> savedAddresses = new ArrayList<String>();
	public static ArrayList<String> usedAddresses = new ArrayList<String>();

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		// Port to listen on
		int port = 9876;

		// Socket to listen on
		DatagramSocket serverSocket;

		// Adds root server addresses
		rootServers.add("128.63.2.53");
		rootServers.add("192.36.148.17");
		rootServers.add("192.203.230.10");

		// Creates a socket for clients to connect to
		try {
			serverSocket = new DatagramSocket(port);
		} catch (Exception e) {
			System.out.println("Socket failed!");
			return;
		}

		// Starts the DNS Resolver
		while (true) {
			// Empty byte array to store data
			byte[] receivedQuery = new byte[1024];
			// Packet
			DatagramPacket dnsPacket = new DatagramPacket(receivedQuery, receivedQuery.length);

			// Receive packet data
			try {
				serverSocket.receive(dnsPacket);
			} catch (Exception e) {
				break;
			}

			// Order bytes from most significant to least significant
			ByteBuffer bytesArrangedQuery = ByteBuffer.wrap(receivedQuery).order(ByteOrder.BIG_ENDIAN);

			// RD flag is set to 0
			short rdFlag = bytesArrangedQuery.getShort(2);
			rdFlag &= ~(1 << 8);
			bytesArrangedQuery.putShort(2, rdFlag);

			// Clear arrays to save original queries into
			usedAddresses.clear();
			savedAddresses.clear();
			savedAddresses.addAll(rootServers);

			byte[] receivedData = new byte[1024];
			ByteBuffer bytesArranged = ByteBuffer.wrap(receivedData).order(ByteOrder.BIG_ENDIAN);
			boolean answerCorrect = true;

			while (answerCorrect && savedAddresses.size() > 0) {

				// Gets next address
				String nextAddress = savedAddresses.remove(savedAddresses.size() - 1);
				usedAddresses.add(nextAddress);
				InetAddress address;

				// Creates an address for the next IP
				try {
					address = InetAddress.getByName(nextAddress);
				} catch (Exception e) {
					System.out.println("Could not create address!");
					break;
				}

				// Send next IP
				try {
					DatagramPacket sendPacket = new DatagramPacket(receivedQuery, receivedQuery.length, address, 53);
					serverSocket.send(sendPacket);

					// 10 second timeout
					serverSocket.setSoTimeout(10000);
				} catch (Exception e) {
					System.out.println("Could not send packet!");
					break;
				}

				// Receive Data
				bytesArranged.clear();
				DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
				try {
					serverSocket.receive(receivePacket);
					// Check if final request has been made
					answerCorrect = !resolve(receivedData, bytesArranged, receivedQuery, bytesArrangedQuery);
				} catch (Exception e) {
					System.out.println("Could not receive packet!");
				}
			}

			if (!answerCorrect) {
				// Send the final query
				try {
					DatagramPacket responsePacket = new DatagramPacket(receivedData, receivedData.length, dnsPacket.getAddress(), dnsPacket.getPort());
					serverSocket.send(responsePacket);
				} catch (Exception e) {
					System.out.println("Could not send last query!");
				}
			} else {
				System.out.println("Error no answer received!");
			}
		}
	}

	private static int moveToNextSection(byte[] receivedData, ByteBuffer receivedDataArranged, int currentIndex) {
		int dataToInt = (int) receivedData[currentIndex] & 0x0FF;
		int savedIndex = -1;
		boolean dataLarge = false;
		currentIndex++;

		while (dataToInt > 0) {
			// If larger than 192 compress
			if (dataToInt >= 192) {
				int offset = (int) receivedDataArranged.getShort(currentIndex - 1) & 0x3FFF;
				// Save old index and point to desired data
				if (!dataLarge) {
					savedIndex = currentIndex;
				}
				currentIndex = offset;
				dataLarge = true;
				// Get the real dataToInt to read next
				dataToInt = (int) receivedData[currentIndex] & 0x0FFFF;
				currentIndex++;
			}
			// Increment index until the end of dataToInt
			for (int j = 0; j < dataToInt; j++) {
				currentIndex++;
			}
			// Get the next bits to read
			dataToInt = receivedData[currentIndex] & 0x0FFFF;
			currentIndex++;
		}
		// Reset index back to original
		if (dataLarge) {
			currentIndex = savedIndex + 1;
		}
		// Returns the index that next section begins
		return currentIndex;
	}

	private static boolean resolve(byte[] receivedData, ByteBuffer receivedDataArranged, byte[] queryData, ByteBuffer queryDataArranged) {
		// Check if answer is correct
		boolean answerCorrect = false;

		// Where to parse the packet
		int currentIndex = 12;

		// Header information
		int qdCount = (int) receivedDataArranged.getShort(4) & 0x0FFFF;
		int anCount = (int) receivedDataArranged.getShort(6) & 0x0FFFF;
		int nsCount = (int) receivedDataArranged.getShort(8) & 0x0FFFF;
		int arCount = (int) receivedDataArranged.getShort(10) & 0x0FFFF;

		// Move through the query
		for (int i = 0; i < qdCount; i++) {
			currentIndex = moveToNextSection(receivedData, receivedDataArranged, currentIndex);
			currentIndex += 4;
		}

		// Check if answers have been received
		if (anCount > 0) {
			System.out.println("Resolved IP Adresses:");
		}

		// Print the IP addresses
		for (int i = 0; i < anCount; i++) {
			// Get the index of the next section
			currentIndex = moveToNextSection(receivedData, receivedDataArranged, currentIndex);

			int qType = (int) receivedDataArranged.getShort(currentIndex) & 0x0FFFF;
			int qClass = (int) receivedDataArranged.getShort(currentIndex + 2) & 0x0FFFF;

			currentIndex += 4;

			if (qType == 1 && qClass == 1) {
				answerCorrect = true;
			}

			int dataLength = (int) receivedDataArranged.getShort(currentIndex + 4) & 0x0FFFF;
			currentIndex += 6;

			String ipAddress = "";
			if (qType == 1) {
				for (int j = 0; j < dataLength; j++) {
					int address = (int) receivedData[currentIndex] & 0x0FF;
					ipAddress += address + ".";
					currentIndex++;
				}
				ipAddress = ipAddress.substring(0, ipAddress.length() - 1);
			}

			// Print the answers
			if (qClass == 1 && (qType == 1 || qType == 5)) {
				System.out.println(ipAddress);
			}
		}

		// Parse through the Name Server Records
		for (int i = 0; i < nsCount; i++) {
			currentIndex = moveToNextSection(receivedData, receivedDataArranged, currentIndex);
			currentIndex += 10;
			currentIndex = moveToNextSection(receivedData, receivedDataArranged, currentIndex);
		}

		// Parse through the Resource Records
		for (int i = 0; i < arCount; i++) {
			currentIndex = moveToNextSection(receivedData, receivedDataArranged, currentIndex);

			int qType = (int) receivedDataArranged.getShort(currentIndex) & 0x0FFFF;
			int qClass = (int) receivedDataArranged.getShort(currentIndex + 2) & 0x0FFFF;
			currentIndex += 4;
			int dataLength = (int) receivedDataArranged.getShort(currentIndex + 4) & 0x0FFFF;
			currentIndex += 6;

			String addRecord = "";
			for (int j = 0; j < dataLength; j++) {
				int address = (int) receivedData[currentIndex] & 0x0FF;
				addRecord += address + ".";
				currentIndex++;
			}

			if (qClass == 1 && qType == 1) {
				addRecord = addRecord.substring(0, addRecord.length() - 1);

				if (!savedAddresses.contains(addRecord) && !usedAddresses.contains(addRecord)) {
					savedAddresses.add(addRecord);
				}
			}
		}
		return answerCorrect;
	}
}