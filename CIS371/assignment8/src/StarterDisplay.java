import javax.swing.*;
import java.awt.*;
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

	private static final int MARGIN = 10; // the margin around the edge of the
											// window.
	private List<String> content; // the text that is to be displayed.
	private Color defaultColor; // the default text color

	// This Map is what makes links: Each Rectangle is a link --- an area on the
	// screen that can be clicked.
	// The rectangle is the Key. The Value, in this case, is the color that
	// should be used when the link is clicked.
	// When building a "real" browser, the links are also areas on the screen,
	// but the corresponding value is the URL
	// that should be loaded when the link is clicked.
	private Map<Rectangle, Color> links = new HashMap<Rectangle, Color>();

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

		// The FontMetrics object can compute the size of text in the window.
		// You must get a new FontMetrics object every time you change or modify
		// the font (e.g., use bold or italics).
		FontMetrics metrics = g.getFontMetrics();
		int line_height = metrics.getHeight();
		int panel_width = getWidth() - MARGIN * 2;

		int x = MARGIN;
		int y = line_height;

		// save the original font in case we change it.
		Font originalFont = g.getFont();
		boolean resetColor = false;
		boolean resetFont = false;
		// Iterate over each line.
		for (String line : content) {
			int bracketStart = 0;
			String url = null;

			Scanner words = new Scanner(line);
			int style = Font.PLAIN;

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

				if (nextWord.endsWith("]]")) {
					nextWord = nextWord.substring(0, nextWord.length() - 2);
					resetColor = true;
				}

				// Links
				if (nextWord.startsWith("[[")) {

					nextWord = nextWord.substring(2);

					url = nextWord;

					if (url.contains("]]"))
						url = url.substring(0, url.length() - 2);

					if (line.split(" ").length != 1 && words.hasNext())
						nextWord = words.next();

					g.setColor(Color.BLUE);
					System.out.println("URL : " + url);
					System.out.println("NEXTWORD : " + nextWord);
				}

				String wordAndSpace = nextWord + " ";
				int word_width = metrics.stringWidth(wordAndSpace);

				// If there isn't room for this word, go to the next line
				if (x + word_width > panel_width) {
					x = MARGIN;
					y += line_height;
				}

				System.out.println(":" + wordAndSpace + ":");

				// draw the word
				g.setFont(originalFont.deriveFont(style));
				g.drawString(wordAndSpace, x, y);

				x += word_width;

			} // end of the line

			// move to the next line
			x = MARGIN;
			y += line_height;
		} // end of all ines

		// make this JPanel bigger if necessary.
		// Calling re-validate causes the scroll bars to adjust, if necessary.
		if (y > getHeight()) {
			setPreferredSize(new Dimension(x, y + line_height + 2 * MARGIN));
			revalidate();
		}
	}

	/**
	 * Determine if the {@code word} represents a color.
	 *
	 * @param word
	 *            the next word to be displayed
	 * @return the {@code Color} represented by {@code word}, or {@code null} if
	 *         {@code word} does not represent a color
	 */

	private static Color getColor(String word) {
		if (word.length() == 9 && word.startsWith("(#") && word.endsWith(")")) {
			return new Color(Integer.parseInt(word.substring(2, 8), 16));
		} else {
			return null;
		}
	}

	/**
	 * Return the color value of the color link at {@code point}, or return
	 * {@code null} if {@code point} doesn't point to a color link.
	 *
	 * @param point
	 *            the {@code Point} that was clicked.
	 * @return the color value of the color link at {@code point}, or return
	 *         {@code null} if {@code point} doesn't point to a color link.
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
}