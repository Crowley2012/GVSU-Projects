import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

/*****************************************************************
 * This class creates a panel to display the game.
 * 
 * @author Sean Crowley
 * @version September 2014
 *****************************************************************/
public class SuperTicTacToePanel extends JPanel {

	/** serial number */
	private static final long serialVersionUID = 1L;

	/** board buttons */
	private JButton board[][];

	/** option buttons */
	private JButton quitButton;
	private JButton undoButton;
	private JButton saveButton;
	private JButton loadButton;

	/** labels displaying game information */
	private JLabel winsX;
	private JLabel winsO;
	private JLabel teamX;
	private JLabel teamO;

	/** array of cells */
	private Cell[][] iBoard;

	/** icons */
	private ImageIcon xIcon;
	private ImageIcon oIcon;
	private ImageIcon emptyIcon;
	private ImageIcon quitIcon;
	private ImageIcon undoIcon;
	private ImageIcon loadIcon;
	private ImageIcon saveIcon;

	/** panels */
	private JPanel panel;
	private JPanel boardPanel;
	private JPanel buttonsPanel;
	private JPanel teamOPanel;
	private JPanel teamXPanel;
	private JPanel scorePanel;
	private JPanel sidePanel;

	/** layout used for buttons */
	private GridLayout boardLayout;

	/** SuperTicTacToeGame object */
	private SuperTicTacToeGame game;

	/*****************************************************************
	 * Constructor that sets up the panel
	 * 
	 * @param size the size of the board
	 * @param start who starts the game X or O
	 * @return none
	 *****************************************************************/
	public SuperTicTacToePanel(int size, int start) {
		game = new SuperTicTacToeGame(size, start);

		boardPanel = new JPanel();
		buttonsPanel = new JPanel();
		panel = new JPanel();
		teamOPanel = new JPanel();
		sidePanel = new JPanel();
		teamXPanel = new JPanel();
		scorePanel = new JPanel();

		board = new JButton[game.getSizeRow()][game.getSizeCol()];
		quitButton = new JButton();
		undoButton = new JButton();
		loadButton = new JButton();
		saveButton = new JButton();

		winsX = new JLabel(Integer.toString(game.getxWins()));
		winsO = new JLabel(Integer.toString(game.getoWins()));
		teamX = new JLabel("X - ");
		teamO = new JLabel("O - ");

		winsX
			.setFont(new Font("Comic Sans MS Regular", Font.PLAIN, 60));
		winsO
			.setFont(new Font("Comic Sans MS Regular", Font.PLAIN, 60));
		teamX.setFont(new Font("Comic Sans MS Regular", Font.BOLD, 60));
		teamO.setFont(new Font("Comic Sans MS Regular", Font.BOLD, 60));

		xIcon = new ImageIcon("xIcon.png");
		oIcon = new ImageIcon("oIcon.png");
		emptyIcon = new ImageIcon("BlankIcon.png");

		quitIcon = new ImageIcon("quitButton.png");
		undoIcon = new ImageIcon("undoButton.png");
		loadIcon = new ImageIcon("loadButton.png");
		saveIcon = new ImageIcon("saveButton.png");

		boardLayout = new GridLayout(game.getSizeRow(),
			game.getSizeCol());
		boardPanel.setLayout(boardLayout);

		ButtonListener listener = new ButtonListener();
		quitButton.addActionListener(listener);
		undoButton.addActionListener(listener);
		loadButton.addActionListener(listener);
		saveButton.addActionListener(listener);

		// instantiates all the buttons
		for (int row = 0; row < game.getSizeRow(); row++) {
			for (int col = 0; col < game.getSizeCol(); col++) {
				board[row][col] = new JButton("", emptyIcon);
				board[row][col].setFocusable(false);
				board[row][col].setRolloverEnabled(false);
				board[row][col].setBorderPainted(false);
				board[row][col]
					.setPreferredSize(new Dimension(100, 100));
				board[row][col].addActionListener(listener);
				boardPanel.add(board[row][col]);
			}
		}

		panel.setBackground(Color.DARK_GRAY);
		teamOPanel.setBackground(Color.DARK_GRAY);
		sidePanel.setBackground(Color.DARK_GRAY);
		buttonsPanel.setBackground(Color.DARK_GRAY);
		scorePanel.setBackground(Color.DARK_GRAY);
		teamXPanel.setBackground(Color.DARK_GRAY);
		quitButton.setBackground(Color.DARK_GRAY);
		undoButton.setBackground(Color.DARK_GRAY);
		loadButton.setBackground(Color.DARK_GRAY);
		saveButton.setBackground(Color.DARK_GRAY);
		winsX.setForeground(Color.LIGHT_GRAY);
		winsO.setForeground(Color.LIGHT_GRAY);
		teamX.setForeground(Color.LIGHT_GRAY);
		teamO.setForeground(Color.LIGHT_GRAY);

		quitButton.setIcon(quitIcon);
		quitButton.setFocusable(false);
		quitButton.setFocusPainted(false);
		quitButton.setRolloverEnabled(false);
		quitButton.setBorderPainted(false);
		quitButton.setBorder(null);

		undoButton.setIcon(undoIcon);
		undoButton.setFocusable(false);
		undoButton.setFocusPainted(false);
		undoButton.setRolloverEnabled(false);
		undoButton.setBorderPainted(false);
		undoButton.setBorder(null);

		loadButton.setIcon(loadIcon);
		loadButton.setFocusable(false);
		loadButton.setFocusPainted(false);
		loadButton.setRolloverEnabled(false);
		loadButton.setBorderPainted(false);
		loadButton.setBorder(null);

		saveButton.setIcon(saveIcon);
		saveButton.setFocusable(false);
		saveButton.setFocusPainted(false);
		saveButton.setRolloverEnabled(false);
		saveButton.setBorderPainted(false);
		saveButton.setBorder(null);

		sidePanel.setPreferredSize(new Dimension(173, 0));

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel,
			BoxLayout.Y_AXIS));
		teamOPanel
			.setLayout(new BoxLayout(teamOPanel, BoxLayout.X_AXIS));
		teamXPanel
			.setLayout(new BoxLayout(teamXPanel, BoxLayout.X_AXIS));
		scorePanel
			.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		sidePanel.setLayout(new GridLayout(2, 0));

		teamXPanel.add(teamX);
		teamXPanel.add(winsX);

		teamOPanel.add(teamO);
		teamOPanel.add(winsO);

		scorePanel.add(Box.createVerticalGlue());
		scorePanel.add(Box.createHorizontalGlue());
		scorePanel.add(teamXPanel);
		scorePanel.add(teamOPanel);
		scorePanel.add(Box.createHorizontalGlue());
		scorePanel.add(Box.createVerticalGlue());

		buttonsPanel.add(Box.createVerticalGlue());
		buttonsPanel.add(Box.createHorizontalStrut(10));
		buttonsPanel.add(undoButton);
		buttonsPanel.add(Box.createVerticalStrut(5));
		buttonsPanel.add(saveButton);
		buttonsPanel.add(Box.createVerticalStrut(5));
		buttonsPanel.add(loadButton);
		buttonsPanel.add(Box.createVerticalStrut(5));
		buttonsPanel.add(quitButton);
		buttonsPanel.add(Box.createVerticalGlue());

		sidePanel.add(scorePanel);
		sidePanel.add(buttonsPanel);

		panel.add(boardPanel);
		panel.add(sidePanel);

		add(panel);
	}

	/*****************************************************************
	 * Updates the board.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void displayBoard() {
		iBoard = game.getBoard();

		for (int row = 0; row < game.getSizeRow(); row++) {
			for (int col = 0; col < game.getSizeCol(); col++) {
				if (iBoard[row][col] == Cell.O) {
					board[row][col].setIcon(oIcon);
				} else if (iBoard[row][col] == Cell.X) {
					board[row][col].setIcon(xIcon);
				} else {
					board[row][col].setIcon(emptyIcon);
				}
			}
		}
	}

	/*****************************************************************
	 * Button listener class
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		/*****************************************************************
		 * Handles the actions of the buttons
		 * 
		 * @param e ActionEvent to determine the action
		 * @return none
		 *****************************************************************/
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == undoButton) {
				game.undo();
				displayBoard();
			}

			if (e.getSource() == quitButton) {
				System.exit(0);
			}

			if (e.getSource() == saveButton) {
				game.save();
			}

			if (e.getSource() == loadButton) {
				game.load();
				winsX.setText(Integer.toString(game.getxWins()));
				winsO.setText(Integer.toString(game.getoWins()));
			}

			for (int row = 0; row < game.getSizeRow(); row++) {
				for (int col = 0; col < game.getSizeCol(); col++) {
					if (board[row][col] == e.getSource()) {
						game.select(row, col);
					}
				}
			}

			displayBoard();

			GameStatus status = game.getGameStatus();

			if (status == GameStatus.O_WON) {
				JOptionPane.showMessageDialog(null, "O won!");
				winsO.setText(Integer.toString(game.getoWins()));
				game.reset();
				displayBoard();
			}
			if (status == GameStatus.X_WON) {
				JOptionPane.showMessageDialog(null, "X won!");
				winsX.setText(Integer.toString(game.getxWins()));
				game.reset();
				displayBoard();
			}
			if (status == GameStatus.CATS) {
				JOptionPane.showMessageDialog(null, "It's a tie!" + "");
				game.reset();
				displayBoard();
			}
		}
	}

	/*****************************************************************
	 * Loads a save file from specified path.
	 * 
	 * @param path directory of the save file
	 * @return none
	 *****************************************************************/
	public void load(File path) {
		game.load(path);
		winsX.setText(Integer.toString(game.getxWins()));
		winsO.setText(Integer.toString(game.getoWins()));
		displayBoard();
	}
}
