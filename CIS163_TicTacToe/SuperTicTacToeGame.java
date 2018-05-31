import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;

/*****************************************************************
 * The main game functions for TicTacToe.
 * 
 * @author Sean Crowley
 * @version September 2014
 *****************************************************************/
public class SuperTicTacToeGame {

	/** 2-D array of enum class Cells */
	private Cell[][] board;

	/** current status of the game */
	private GameStatus status;

	/** player moves stored in array */
	private ArrayList<Point> moves;

	/** value to determine who's turn */
	private int turn;

	/** value set by user to determine turn */
	private int turnSave;

	/** number of columns */
	private int sizeCol;

	/** number of rows */
	private int sizeRow;

	/** consecutive X's or O's to win */
	private int toWin;

	/** number of times X has won */
	private int xWins;

	/** number of times O has won */
	private int oWins;

	/*****************************************************************
	 * Getter method for size of columns.
	 * 
	 * @param none
	 * @return sizeCol the number of the column
	 *****************************************************************/
	public int getSizeCol() {
		return sizeCol;
	}

	/*****************************************************************
	 * Getter method for size of rows.
	 * 
	 * @param none
	 * @return sizeRow the number of the rows
	 *****************************************************************/
	public int getSizeRow() {
		return sizeRow;
	}

	/*****************************************************************
	 * Getter method for number of times x has won.
	 * 
	 * @param none
	 * @return xWins the number of times x has won
	 *****************************************************************/
	public int getxWins() {
		return xWins;
	}

	/*****************************************************************
	 * Setter method to set the number of X wins.
	 * 
	 * @param s number of times x has won
	 * @return none
	 *****************************************************************/
	public void setxWins(int s) {
		xWins = s;
	}

	/*****************************************************************
	 * Getter method for number of times o has won.
	 * 
	 * @param none
	 * @return oWins number of times o has won
	 *****************************************************************/
	public int getoWins() {
		return oWins;
	}

	/*****************************************************************
	 * Setter method to set the number of O wins.
	 * 
	 * @param s number of times o has won
	 * @return none
	 *****************************************************************/
	public void setoWins(int s) {
		oWins = s;
	}

	/*****************************************************************
	 * Setter method to set who's turn it is.
	 * 
	 * @param s determines whos turn it is (0-X, 1-O)
	 * @return none
	 *****************************************************************/
	public void setTurn(int s) {
		turn = s;
	}

	/*****************************************************************
	 * Getter method for the board
	 * 
	 * @param none
	 * @return board 2-D array of Enum Class Cells
	 *****************************************************************/
	public Cell[][] getBoard() {
		return board;
	}

	/*****************************************************************
	 * Getter method for the status of the game
	 * 
	 * @param none
	 * @return status the current status of the game
	 *****************************************************************/
	public GameStatus getGameStatus() {
		checkWin();

		// Increments oWins if O has won
		if (status == GameStatus.O_WON) {
			oWins++;
		}
		// Increments xWins if x has won
		if (status == GameStatus.X_WON) {
			xWins++;
		}

		return status;
	}

	/*****************************************************************
	 * Constructor creates a board and sets up variables.
	 * 
	 * @param size determines the size of the board (size x size)
	 * @param start determines who starts
	 * @return none
	 *****************************************************************/
	public SuperTicTacToeGame(int size, int start) {
		status = GameStatus.IN_PROGRESS;
		sizeRow = size;
		sizeCol = size;
		toWin = 3;
		xWins = 0;
		oWins = 0;
		turnSave = start;
		turn = start;
		moves = new ArrayList<Point>();
		board = new Cell[sizeRow][sizeCol];

		// sets all cells to empty
		for (int row = 0; row < sizeRow; row++) {
			for (int col = 0; col < sizeCol; col++) {
				board[row][col] = Cell.EMPTY;
			}
		}
	}

	/*****************************************************************
	 * Sets the board to an X or O based on who's turn it is and what
	 * button was selected.
	 * 
	 * @param row the row that the button was selected on
	 * @param col the column that the button was selected on
	 * @return none
	 *****************************************************************/
	public void select(int row, int col) {
		if (board[row][col] == Cell.EMPTY) {
			if (turn == 0) {
				board[row][col] = Cell.O;
				turn = 1;
			} else {
				board[row][col] = Cell.X;
				turn = 0;
			}
			moves.add(new Point(row, col));
		}
	}

	/*****************************************************************
	 * Resets the game.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	public void reset() {
		board = new Cell[sizeRow][sizeCol];
		turn = turnSave;
		status = GameStatus.IN_PROGRESS;
		moves.clear();

		// Sets all cells to empty
		for (int row = 0; row < sizeRow; row++) {
			for (int col = 0; col < sizeCol; col++) {
				board[row][col] = Cell.EMPTY;
			}
		}
	}

	/*****************************************************************
	 * Undos a players last move.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	public void undo() {

		// Changes whos turn it is
		if (turn == 0) {
			turn = 1;
		} else {
			turn = 0;
		}

		// Undos the last move
		if (moves.size() > 0) {

			int x = (int) moves.get(moves.size() - 1).getX();
			int y = (int) moves.get(moves.size() - 1).getY();

			board[x][y] = Cell.EMPTY;
			moves.remove(moves.size() - 1);
		}
	}

	/*****************************************************************
	 * Checks if there is a winner and updates the game status
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void checkWin() {
		int counter = 0;

		// Checks Horizontally for O
		for (int row = 0; row <= sizeRow * 2; row++) {

			// Resets counter after each row
			counter = 0;

			// Checks across 2 times the size to wrap the board
			for (int col = 0; col <= sizeCol * 2; col++) {
				if (board[row % sizeRow][col % sizeCol] == Cell.O) {
					counter++;
				} else {
					counter = 0;
				}
				if (counter == toWin) {
					status = GameStatus.O_WON;
					return;
				}
			}
		}

		// Checks Horizontally for X
		for (int row = 0; row <= sizeRow * 2; row++) {

			// Resets counter after each row
			counter = 0;

			// Checks across 2 times the size to wrap the board
			for (int col = 0; col <= sizeCol * 2; col++) {
				if (board[row % sizeRow][col % sizeCol] == Cell.X) {
					counter++;
				} else {
					counter = 0;
				}
				if (counter == toWin) {
					status = GameStatus.X_WON;
					return;
				}
			}
		}

		// Checks Vertically for O
		for (int col = 0; col <= sizeCol * 2; col++) {

			// Resets counter after each column
			counter = 0;

			// Checks across 2 times the size to wrap the board
			for (int row = 0; row <= sizeRow * 2; row++) {
				if (board[row % sizeRow][col % sizeCol] == Cell.O) {
					counter++;
				} else {
					counter = 0;
				}
				if (counter == toWin) {
					status = GameStatus.O_WON;
					return;
				}
			}
		}

		// Checks Vertically for X
		for (int col = 0; col <= sizeCol * 2; col++) {

			// Resets counter after each column
			counter = 0;

			// Checks across 2 times the size to wrap the board
			for (int row = 0; row <= sizeRow * 2; row++) {
				if (board[row % sizeRow][col % sizeCol] == Cell.X) {
					counter++;
				} else {
					counter = 0;
				}
				if (counter == toWin) {
					status = GameStatus.X_WON;
					return;
				}
			}
		}

		// Checks Diagonally bottom left to top right for X
		for (int col = 0; col <= sizeCol; col++) {
			for (int row = 0; row <= sizeRow; row++) {
				try {
					if (row - 2 >= 0 && col + 2 < sizeCol) {
						if (board[row][col] == Cell.X && board[(row - 1)][(col + 1)] == Cell.X && board[(row - 2)][(col + 2)] == Cell.X) {
							status = GameStatus.X_WON;
							return;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		// Checks Diagonally bottom left to top right for O
		for (int col = 0; col <= sizeCol; col++) {
			for (int row = 0; row <= sizeRow; row++) {
				try {
					if (row - 2 >= 0 && col + 2 < sizeCol) {
						if (board[row][col] == Cell.O && board[(row - 1)][(col + 1)] == Cell.O && board[(row - 2)][(col + 2)] == Cell.O) {
							status = GameStatus.O_WON;
							return;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		// Checks Diagonally top left to bottom right for X
		for (int col = 0; col <= sizeCol; col++) {
			for (int row = 0; row <= sizeRow; row++) {
				try {
					if (row - 2 >= 0 && col - 2 >= 0) {
						if (board[row][col] == Cell.X && board[(row - 1)][(col - 1)] == Cell.X && board[(row - 2)][(col - 2)] == Cell.X) {
							status = GameStatus.X_WON;
							return;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		// Checks Diagonally top left to bottom right for O
		for (int col = 0; col <= sizeCol; col++) {
			for (int row = 0; row <= sizeRow; row++) {
				try {
					if (row - 2 >= 0 && col - 2 >= 0) {
						if (board[row][col] == Cell.O && board[(row - 1)][(col - 1)] == Cell.O && board[(row - 2)][(col - 2)] == Cell.O) {
							status = GameStatus.O_WON;
							return;
						}
					}
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}

		// Check for cats game
		counter = 0;
		for (int col = 0; col < sizeCol; col++) {
			for (int row = 0; row < sizeRow; row++) {
				if (board[row][col] == Cell.O || board[row][col] == Cell.X) {
					counter++;
				}
			}
		}
		if (counter == sizeCol * sizeRow) {
			status = GameStatus.CATS;
		}
	}

	/*****************************************************************
	 * Saves the current game to file.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	public void save() {
		PrintWriter out = null;
		JFileChooser fileName = new JFileChooser();
		fileName.setCurrentDirectory(new File(System.getProperty("user.home")));
		int val = fileName.showOpenDialog(null);

		if (val == JFileChooser.APPROVE_OPTION) {
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter(fileName.getSelectedFile())));

				// Prints out settings
				out.println(xWins + "," + oWins + "," + turn + "," + sizeRow + "," + sizeCol);

				// Prints out button statues
				for (int row = 0; row < sizeRow; row++) {
					for (int col = 0; col < sizeCol; col++) {
						out.println(board[row][col]);
					}
				}

				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*****************************************************************
	 * Loads a game from file.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	public void load() {
		JFileChooser fileName = new JFileChooser();
		fileName.setCurrentDirectory(new File(System.getProperty("user.home")));
		int val = fileName.showOpenDialog(null);
		int rows = 0;
		int cols = 0;
		String loadedBoard;
		String[] values;
		Cell c;

		if (val == JFileChooser.APPROVE_OPTION) {
			try {
				// open the data file
				Scanner fileReader = new Scanner(fileName.getSelectedFile());

				// Splits the first line in the file
				// Containing the game settings
				loadedBoard = fileReader.next();
				values = loadedBoard.split(",");
				setxWins(Integer.parseInt(values[0]));
				setoWins(Integer.parseInt(values[1]));
				setTurn(Integer.parseInt(values[2]));
				rows = Integer.parseInt(values[3]);
				cols = Integer.parseInt(values[4]);

				// Checks if the size of the loaded board
				// Is different from current board. If so
				// A new board is created
				if (rows != sizeRow || cols != sizeCol) {
					new SuperTicTacToe(rows, turn, fileName.getSelectedFile());
				}

				// Sets the statuses of the buttons
				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < cols; col++) {
						loadedBoard = fileReader.next();

						if (loadedBoard.equals("X")) {
							c = Cell.X;
						} else if (loadedBoard.equals("O")) {
							c = Cell.O;
						} else {
							c = Cell.EMPTY;
						}

						board[row][col] = c;
					}
				}

				fileReader.close();
			}

			// could not find file
			catch (FileNotFoundException error) {
				System.out.println("File not found");

			}
		}
	}

	/*****************************************************************
	 * Loads a game from a specified path. Used when loading a different
	 * sized board.
	 * 
	 * @param path directory of the save file
	 * @return none
	 *****************************************************************/
	public void load(File path) {
		String loadedBoard;
		String[] values;
		Cell c;
		int rows = 0;
		int cols = 0;

		try {
			// open the data file
			Scanner fileReader = new Scanner(path);

			// Splits the first line in the file
			// Containing the game settings
			loadedBoard = fileReader.next();
			values = loadedBoard.split(",");
			setxWins(Integer.parseInt(values[0]));
			setoWins(Integer.parseInt(values[1]));
			setTurn(Integer.parseInt(values[2]));
			rows = Integer.parseInt(values[3]);
			cols = Integer.parseInt(values[4]);

			// Sets the statuses of the buttons
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					loadedBoard = fileReader.next();

					if (loadedBoard.equals("X")) {
						c = Cell.X;
					} else if (loadedBoard.equals("O")) {
						c = Cell.O;
					} else {
						c = Cell.EMPTY;
					}

					board[row][col] = c;
				}
			}

			fileReader.close();
		}

		// could not find file
		catch (FileNotFoundException error) {
			System.out.println("File not found");

		}
	}

}
