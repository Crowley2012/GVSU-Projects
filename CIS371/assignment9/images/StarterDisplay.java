import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class demonstrates a simple technique of laying out text "by hand"
 * <p/>
 * Also demonstrates how to change fonts and colors.
 * <p/>
 * Created by kurmasz on 12/17/14.
 */
public class StarterDisplay extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int MARGIN = 10; // the margin around the edge of the
											// window.
	private List<String> content; // the text that is to be displayed.
	private Color defaultColor; // the default text color

	private ImageCache cache = new ImageCache();

	// This Map is what makes links: Each Rectangle is a link --- an area on the
	// screen that can be clicked.
	// The rectangle is the Key. The Value, in this case, is the color that
	// should be used when the link is clicked.
	// When building a "real" browser, the links are also areas on the screen,
	// but the corresponding value is the URL
	// that should be loaded when the link is clicked.
	private Map<Rectangle, Color> links = new HashMap<Rectangle, Color>();
	private Map<Rectangle, String> linkAddresses = new HashMap<Rectangle, String>();

	private String address;
	private MyURL myURL;

	/**
	 * Set the text that is to be displayed.
	 *
	 * @param text_in
	 *            the text that is to be displayed
	 */
	public void setText(List<String> text_in) {
		content = text_in;
		defaultColor = Color.black;
	}

	public void setAddress(String s) {
		address = s;
	}

	public void setMyURL(MyURL u) {
		myURL = u;
	}

	public Map<Rectangle, String> getLinkAddresses() {
		return linkAddresses;
	}

	/**
	 * Set the text color
	 *
	 * @param c
	 *            the desired text color
	 */
	public void setColor(Color c) {
		defaultColor = c;
	}

	/**
	 * Actually "draws" the text on the window.
	 *
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// If no file has been loaded yet, then do nothing.
		if (content == null) {
			return;
		}
		links.clear();
		linkAddresses.clear();

		// The FontMetrics object can compute the size of text in the window.
		// You must get a new FontMetrics object every time you change or modify
		// the font (e.g., use bold or italics).
		FontMetrics metrics = g.getFontMetrics();
		int line_height = metrics.getHeight();
		int panel_width = getWidth() - MARGIN * 2;

		int x = MARGIN;
		int y = line_height;

		if (myURL.toString().contains(".png") || myURL.toString().contains(".jpg")) {

			if (cache.getImage(new MyURL(myURL.toString().substring(0, myURL.toString().length() - 1))) != null) {
				BufferedImage image = cache.getImage(new MyURL(myURL.toString().substring(0, myURL.toString().length() - 1)));
				g.drawImage(image, x, y, null);
				if (image != null) {
					x += image.getWidth();
					y += image.getHeight();
				}
			} else {
				WebTransactionClient client;
				try {
					client = new WebTransactionClient(new MyURL(myURL.toString().substring(0, myURL.toString().length() - 1)));
					BufferedImage image = client.getImage();
					g.drawImage(image, x, y, null);
					if (image != null) {
						x += image.getWidth();
						y += image.getHeight();
					}
					cache.addImage(new MyURL(myURL.toString().substring(0, myURL.toString().length() - 1)), image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return;
		}

		// save the original font in case we change it.
		Font originalFont = g.getFont();
		boolean resetColor = false;
		boolean resetFont = false;
		// Iterate over each line.
		for (String line : content) {
			String url = null;

			Scanner words = new Scanner(line);
			int style = Font.PLAIN;
			int linkLength = 0;
			int linkX = 0;
			int linkXEnd = 0;
			boolean link = false;

			// iterate over each word.
			while (words.hasNext()) {

				String nextWord = words.next();

				if (resetColor) {
					g.setColor(Color.BLACK);
					resetColor = false;
				}

				if (resetFont) {
					style = Font.PLAIN;
					resetFont = false;
				}

				if (nextWord.startsWith("*")) {
					style = Font.BOLD;
					if (nextWord.endsWith("*")) {
						nextWord = nextWord.substring(1, nextWord.length() - 1) + " ";
						resetFont = true;
					} else {
						nextWord = nextWord.substring(1, nextWord.length()) + " ";
					}
				}

				if (nextWord.endsWith("*")) {
					nextWord = nextWord.substring(0, nextWord.length() - 1) + " ";
					resetFont = true;
				}

				if (nextWord.startsWith("_")) {
					style = Font.ITALIC;
					nextWord = nextWord.substring(1, nextWord.length() - 1);
				}

				if (nextWord.endsWith("_")) {
					nextWord = nextWord.substring(0, nextWord.length() - 1);
					resetFont = true;
				}

				// Links
				if (nextWord.startsWith("[[")) {
					link = true;

					nextWord = nextWord.substring(2);

					url = nextWord;

					if (url.contains("]]"))
						url = url.substring(0, url.length() - 2);

					if (line.split(" ").length != 1 && words.hasNext())
						nextWord = words.next();

					// System.out.println(url);

					linkLength = metrics.stringWidth(nextWord);
					linkX = x;
					g.setColor(Color.BLUE);
				}

				if (link) {
					linkXEnd += metrics.stringWidth(nextWord);
				}

				if (nextWord.endsWith("]]")) {
					nextWord = nextWord.substring(0, nextWord.length() - 2);
					resetColor = true;
					// linkXEnd = metrics.stringWidth(nextWord);
					link = false;
				}

				if (nextWord.startsWith("<<")) {
					url = nextWord.substring(2);
					nextWord = "";

					if (url.contains(">>"))
						url = url.substring(0, url.length() - 2);

					if (cache.getImage(new MyURL(address + url)) != null) {
						BufferedImage image = cache.getImage(new MyURL(address + url));
						g.drawImage(image, x, y, null);

						if (image != null) {
							x += image.getWidth();
							y += image.getHeight();
						}
					} else {
						WebTransactionClient client;

						try {
							if(url.contains("http")){
								client = new WebTransactionClient(new MyURL(url));
								
							}else{
								client = new WebTransactionClient(new MyURL(address + url));
							}
							System.out.println("IMAGE : " + address + url);
							BufferedImage image = client.getImage();
							g.drawImage(image, x, y, null);
							if (image != null) {
								x += image.getWidth();
								y += image.getHeight();
							}
							cache.addImage(new MyURL(address + url), image);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				String wordAndSpace = nextWord + " ";
				int word_width = metrics.stringWidth(wordAndSpace);

				// If there isn't room for this word, go to the next line
				if (x + word_width > panel_width) {
					x = MARGIN;
					y += line_height;
				}

				// draw the word
				g.setFont(originalFont.deriveFont(style));
				g.drawString(wordAndSpace, x, y);

				x += word_width;

			} // end of the line
			Rectangle rect = new Rectangle(linkX, y - line_height, linkXEnd, line_height);
			links.put(rect, Color.BLUE);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);

			if (url != null) {
				linkAddresses.put(rect, url);
			}

			// move to the next line
			x = MARGIN;
			y += line_height;
			words.close();
		} // end of all ines

		// make this JPanel bigger if necessary.
		// Calling re-validate causes the scroll bars to adjust, if necessary.
		if (y > getHeight()) {
			setPreferredSize(new Dimension(x, y + line_height + 2 * MARGIN));
			revalidate();
		}
	}

	public void drawImage(Graphics g) {

	}

	/**
	 * Determine if the {@code word} represents a color.
	 *
	 * @param word
	 *            the next word to be displayed
	 * @return the {@code Color} represented by {@code word}, or {@code null} if {@code word} does not represent a color
	 */

	private static Color getColor(String word) {
		if (word.length() == 9 && word.startsWith("(#") && word.endsWith(")")) {
			return new Color(Integer.parseInt(word.substring(2, 8), 16));
		} else {
			return null;
		}
	}

	/**
	 * Return the color value of the color link at {@code point}, or return {@code null} if {@code point} doesn't point to a color link.
	 *
	 * @param point
	 *            the {@code Point} that was clicked.
	 * @return the color value of the color link at {@code point}, or return {@code null} if {@code point} doesn't point to a color link.
	 */
	//
	public Color getColor(Point point) {
		for (Map.Entry<Rectangle, Color> entry : links.entrySet()) {
			if (entry.getKey().contains(point)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public String getRect(Point point) {
		for (Map.Entry<Rectangle, String> entry : linkAddresses.entrySet()) {
			if (entry.getKey().contains(point)) {
				return entry.getValue();
			}
		}
		return null;
	}
}