//Sean Crowley
//Morrison Cummingham

import java.io.*;
import java.net.*;

public class fileServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		ServerSocket servsock = null;
		Socket sock = null;

		String fileToSend = "";

		try {
			servsock = new ServerSocket(getPort());
			while (true) {
				System.out.println("Waiting...");
				try {
					sock = servsock.accept();
					System.out.println("New connection : " + sock);

					// Get Input
					BufferedReader inFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					DataOutputStream outToClient = new DataOutputStream(sock.getOutputStream());
					String clientMessage = inFromClient.readLine();
					System.out.println("The client said " + clientMessage);
					outToClient.writeBytes(clientMessage + '\n');

					if (clientMessage.length() > 4) {
						if (clientMessage.substring(0, 4).toLowerCase().equals("send")) {
							fileToSend = clientMessage.substring(5, clientMessage.length());

							// Send File
							File myFile = new File(fileToSend);
							if (myFile.exists()) {
								byte[] mybytearray = new byte[(int) myFile.length()];
								fis = new FileInputStream(myFile);
								bis = new BufferedInputStream(fis);
								bis.read(mybytearray, 0, mybytearray.length);
								os = sock.getOutputStream();
								System.out.println("Sending " + fileToSend + "(" + mybytearray.length + " bytes)");
								os.write(mybytearray, 0, mybytearray.length);
								os.flush();
								System.out.println("Done.");
							}
						}
					}

				} finally {
					if (bis != null)
						bis.close();
					if (os != null)
						os.close();
					if (sock != null)
						sock.close();
				}
			}
		} finally {
			if (servsock != null)
				servsock.close();
		}
	}

	private static int getPort() {
		int port = 0;
		boolean valid = true;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter a port to watch: ");

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