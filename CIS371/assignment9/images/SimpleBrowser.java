import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class SimpleBrowser {

	private JFrame frame;
	protected JTextField addressBar;
	private JScrollPane scrollPane;
	private StarterDisplay display;
	private String homeLoc;

	// protected ImageCache cache = new ImageCache();

	// The URL of the currently displayed document;
	protected MyURL currentURL = null;

	protected SimpleBrowser(String frameName, String initialLocation, JPanel displayPanel) {
		homeLoc = initialLocation;

		frame = new JFrame(frameName);
		frame.setSize(500, 500);
		addressBar = new JTextField(initialLocation);
		currentURL = new MyURL(initialLocation);

		JPanel barPanel = new JPanel();
		barPanel.setLayout(new BorderLayout());
		JButton home = new JButton("Home");
		barPanel.add(home, BorderLayout.WEST);
		barPanel.add(addressBar, BorderLayout.CENTER);

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.width /= 2;
		screenSize.height /= 2;

		displayPanel.setPreferredSize(screenSize);
		scrollPane = new JScrollPane(displayPanel);

		frame.getContentPane().add(barPanel, BorderLayout.NORTH);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		// Respond to the user pressing <enter> in the address bar.
		addressBar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String textInBar = addressBar.getText();

				// Replace this with the code that loads
				// text from a web server
				loadPage(textInBar);
			}
		});

		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadPage(homeLoc);
				addressBar.setText(homeLoc);
			}
		});

		displayPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				clicked(e.getPoint());
			}
		});
	}

	public SimpleBrowser(String frameName, String initialLocation, StarterDisplay display_in) {
		this(frameName, initialLocation, (JPanel) display_in);
		display = display_in;
		display.setAddress(currentURL.domainName() + currentURL.getPath());
		display.setMyURL(currentURL);
	}

	protected void clicked(Point point) {
		Map<Rectangle, String> test = display.getLinkAddresses();
		// Respond to a mouse click in the display
		// TODO: Override/replace this method when you add support for links.
		Color c = display.getColor(point);
		// System.out.println("CLICK");
		if (c != null) {
			// test.get(key)
			System.out.println("COLOR : " + display.getRect(point));
			display.setColor(Color.RED);
			display.repaint();

			// loadPage(display.getRect(point).substring(0, display.getRect(point).length() - 1));
			MyURL temp = new MyURL(addressBar.getText());


			if (display.getRect(point).contains("http")) {
				temp = new MyURL(display.getRect(point).replace("www.", ""));
				System.out.println("NEW : " + temp.domainName() + temp.path());
				loadPage(temp.domainName() + temp.path());
				addressBar.setText(temp.domainName() + temp.path());
				display.setAddress(temp.domainName() + temp.path());
				return;
			}

			loadPage(temp.domainName() + temp.getPath() + display.getRect(point));
			addressBar.setText(temp.domainName() + temp.getPath() + display.getRect(point));

			if (display.getRect(point).contains("/")) {
				String address = display.getRect(point).substring(0, display.getRect(point).lastIndexOf("/"));
				display.setAddress(temp.domainName() + temp.getPath() + address + "/");
			} else {
				display.setAddress(temp.domainName() + temp.getPath());
			}
		}
	}

	protected void loadPage(String textInBar) {

		int port = 80;
		String site = textInBar;
		display.setMyURL(new MyURL(textInBar));

		// Split the web address
		int split = site.indexOf('/');
		if (split < 0) {
			site = site + "/";
			split = site.indexOf('/');
		}

		String filepath = site.substring(split);
		String host = site.substring(0, split);

		try {
			// Establishes connection
			Socket socket = new Socket(host, port);

			// Data streams
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream, false);
			InputStream inputStream = socket.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			BufferedWriter fileout = new BufferedWriter(new FileWriter("output.txt", false));

			// Send get request
			// printWriter.print("GET " + file + " HTTP/1.0\r\n");
			// printWriter.print("Accept: text/plain, text/html, text/*\r\n");
			// printWriter.print("\r\n");

			// printWriter.println("HTTP/1.1 200 OK");
			printWriter.println("GET " + filepath + " HTTP/1.0");
			printWriter.println("Content-Type: text/plain");
			printWriter.println("Content-Length: 70");
			printWriter.println("Connection: close");
			printWriter.println("\r\n\r\n");
			printWriter.println("This is not the real content because this server is not yet complete.");

			printWriter.flush();

			// True if still in header
			boolean header = true;
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				// Checks for end of header
				if (line.trim().equals("")) {
					header = false;
				}

				// Prints header to console and body to file
				if (header) {
					System.out.println(line);
				} else {
					fileout.write(line + "\n");
				}
			}
			System.out.println("\n");

			fileout.close();
			// Closes socket
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

		File file = new File("output.txt");
		List<String> contents = null;
		try {

			// WARNING!! This code is missing a lot of important
			// checks ("does the file exist", "is it a text file", "is it
			// readable", etc.)
			contents = Files.readAllLines(file.toPath(), Charset.defaultCharset());
		} catch (IOException e) {
			System.out.println("Can't open file " + file);
			e.printStackTrace();
		}
		display.setText(contents);
		frame.repaint();
	}

	// Fetch an image from from the server, or return null if
	// the image isn't available.
	protected Image fetchImage(MyURL url) {
		try {
			WebTransactionClient client = new WebTransactionClient(url);
			client.getImage();

			// frame.add(display.paintComponent(g);
			// frame.repaint();
			// g.drawImage(client.getImage(), 0, 0, null);
			// frame.add(g);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return the image at the given url.
	 *
	 * @param urlString
	 *            the URL of the image to load.
	 * @return The desired image, or {@code null} if the image isn't available.
	 */
	/*
	 * public Image getCachedImage(String urlString) { MyURL url = new MyURL(urlString, currentURL);
	 * 
	 * // This unusual syntax (the "new ImageCache.ImageLoader" stuff) is an "anonymous inner class. It is Java's way // of allowing us to pass the fetchImage method as a parameter to the ImageCache.getImage. You may have seen this // syntax before with ActionListeners. If not, I will be happy to explain it to you. return cache.getImage(url, new ImageCache.ImageLoader() {
	 * 
	 * @Override public Image loadImage(MyURL url) { return fetchImage(url); } }); }
	 */

	public static void main(String[] args) {

		// Notice that the display object (the StarterDisplay) is created
		// *outside* of the
		// SimpleBrowser object. This is an example of "dependency injection"
		// (also called
		// "inversion of control"). In general, dependency injection simplifies
		// unit testing.
		// I this case, I used dependency injection so that I could more easily
		// write a subclass
		// of this browser that uses a completely different display class.
		// String initial = args.length > 0 ? args[0] : "cis.gvsu.edu/~kurmasz/Teaching/Courses/S16/CS371/Assignments/WebBrowser/sampleInput/buzz1.png";
		// String initial = args.length > 0 ? args[0] : "cis.gvsu.edu/~kurmasz/Teaching/Courses/S16/CS371/Assignments/WebBrowser/sampleInput/subdirImages.txt";
		String initial = args.length > 0 ? args[0] : "cis.gvsu.edu/~kurmasz/Teaching/Courses/S16/CS371/Assignments/WebBrowser/sampleInput/basic.txt";
		// String initial = args.length > 0 ? args[0] : "cis.gvsu.edu/~kurmasz/Teaching/Courses/S16/CS371/Assignments/WebBrowser/sampleInput/MyMarkupTest.txt";
		SimpleBrowser brow = new SimpleBrowser("CIS 371 Starter Browser", initial, new StarterDisplay());
		// System.out.println("Fetching Image");
		// brow.fetchImage(new MyURL("http://cdn.osxdaily.com/wp-content/uploads/2013/07/apple-logo.gif"));

	}

}