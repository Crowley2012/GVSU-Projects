/******************************************
 * CIS 371
 * Assignment 4
 *
 * Sean Crowley
 * Vignesh Suresh
 *
 * This class is a basic web browser
 ******************************************/

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class WebTransactionClient {
	private PrintWriter out;
	private DataInputStream in;
	private Socket socket;
	private String response;
	private HashMap<String, String> headers = new HashMap<String, String>();

	// Port to connect
	private int port = 80;

	@SuppressWarnings("deprecation")
	public WebTransactionClient(MyURL url) throws IOException {

		try {
			// Establishes connection
			socket = new Socket(url.domainName(), port);

			// Output
			OutputStream outputStream = socket.getOutputStream();
			out = new PrintWriter(outputStream, false);

			// Input
			InputStream inputStream = socket.getInputStream();
			in = new DataInputStream(inputStream);

			// Send get request
			out.print("GET " + url.path() + " HTTP/1.0\r\n");
			out.print("Host: " + url.domainName() + ":80\r\n");
			out.print("Accept: image/*, text/plain, text/html, text/*\r\n");
			out.print("\r\n");
			out.flush();

			boolean header = true;
			String line;

			while ((line = in.readLine()) != null) {
				// Checks for end of header
				if (line.trim().equals("")) {
					header = false;
				}

				// Prints head to console and body to file
				if (header) {
					if (line.contains(":")) {
						headers.put(line.substring(0, line.indexOf(":")).toLowerCase().trim(), line.substring(line.indexOf(":") + 1, line.length()).trim());
					} else {
						headers.put(null, line.substring(line.indexOf(":") + 1, line.length()).trim());
					}
				} else {
					break;
				}
			}

			// Prints the header to console
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				System.out.println("" + entry.getKey() + " : " + entry.getValue());
			}
			System.out.println("\n");
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + url.domainName());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + url.domainName());
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Error : " + e);
			System.exit(1);
		}
	}

	@SuppressWarnings("deprecation")
	public String getText() throws IOException {
		StringBuffer result = new StringBuffer();
		String line;

		// Writes the body of the request to a single string
		while ((line = in.readLine()) != null) {
			if (line.equals("")) {
				result.append(System.getProperty("line.separator"));
			} else {
				result.append(line + System.getProperty("line.separator"));
			}
		}

		return result.toString();
	}

	public BufferedImage getImage() throws IOException {
		return ImageIO.read(in);
	}

	public String response() {
		return response;
	}

	public int responseCode() {
		return Integer.parseInt(headers.get(null).substring(9, 12));
	}

	public Map<String, String> responseHeaders() {
		return headers;
	}

	public String getHeader(String key) {
		return headers.get(key.toLowerCase());
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		in.close();
		out.close();
		socket.close();
	}

	public static void main(String[] args) {
		try {
			new WebTransactionClient(new MyURL("http://cdn.osxdaily.com/wp-content/uploads/2013/07/apple-logo.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
