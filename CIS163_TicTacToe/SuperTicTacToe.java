import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*****************************************************************
 * The main class that initializes the frame for the game.
 * 
 * @author Sean Crowley
 * @version September 2014
 *****************************************************************/
public class SuperTicTacToe {

	/** the frame for the game */
	private static JFrame frame;

	/*****************************************************************
	 * Constructor that sets up the game with predefined settings.
	 * 
	 * @param size the size of the board
	 * @param start who starts the game
	 * @param path directory to the save file
	 * @return none
	 *****************************************************************/
	public SuperTicTacToe(int size, int start, File path) {

		// Destroys the old frame
		frame.dispose();
		frame = new JFrame("Super TicTacToe");

		SuperTicTacToePanel panel = new SuperTicTacToePanel(size, start);
		((FlowLayout) panel.getLayout()).setVgap(0);
		frame.add(panel, BorderLayout.NORTH);

		frame.pack();
		frame.setSize(panel.getWidth() - 5, panel.getHeight() + 28);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		panel.load(path);

	}

	/*****************************************************************
	 * Main method that is run when the game starts up. This creates the
	 * game.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	public static void main(String[] args) {
		frame = new JFrame("Super TicTacToe");

		// Asks the user for the size of the board
		String s = JOptionPane.showInputDialog(null,
			"Enter the size of the board:");

		boolean b = true;
		int size = 0;
		int start = 0;

		// This loop ensures that the user enters a proper size.
		while (b) {
			try {
				if (s == null) {
					System.exit(0);
				}

				size = Integer.parseInt(s);

				if (size > 2 && size < 15) {
					b = false;
				} else {
					s = JOptionPane
						.showInputDialog(
							null,
							"Size needs to be greater than 2 and less than 15. \nEnter the size of the board:");
				}

			} catch (NumberFormatException e) {
				s = JOptionPane
					.showInputDialog(null,
						"Size needs to be a number. \nEnter the size of the board:");
			}
		}

		// Determines who will start the game, X or O
		s = JOptionPane.showInputDialog(null,
			"Who will start the game? (X or O)");
		if (s.toLowerCase().equals("o")) {
			start = 0;
		} else if (s.toLowerCase().equals("x")) {
			start = 1;
		} else {
			// Defaults to X
			start = 1;
			JOptionPane.showMessageDialog(null,
				"Incorrect input. Defaulted to X.");
		}

		SuperTicTacToePanel panel = new SuperTicTacToePanel(size, start);
		((FlowLayout) panel.getLayout()).setVgap(0);
		frame.add(panel, BorderLayout.NORTH);

		frame.pack();
		frame.setSize(panel.getWidth() - 5, panel.getHeight() + 28);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
