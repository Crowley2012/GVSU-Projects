import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

/***********************************************************
 * Editor class implements a simple line-oriented editor.
 * 
 * @author Sean Crowley
 * @version November 2014
 ***********************************************************/
public class Editor implements IEditor {

	/** Contains file contents */
	private MyLinkedList list;

	/** Contains cut contents */
	private MyLinkedList clipboard;

	/** Current selected line */
	private int currentLine;

	/** True if changes have been saved */
	private boolean saved;

	/******************************************************
	 * Constructor initializes variables
	 * 
	 * @return none
	 ******************************************************/
	public Editor() {
		list = new MyLinkedList();
		clipboard = new MyLinkedList();
		currentLine = 1;
		saved = false;
	}

	/******************************************************
	 * Processes the input from the user
	 * 
	 * @param command
	 *            input from user
	 * @return none
	 ******************************************************/
	@Override
	public void processCommand(String command) throws EditorException {
		String lowCom = command.toLowerCase();
		if (command.length() > 2 && lowCom.equals("rev")) {
			reverse();
		} else if (command.length() > 2
				&& lowCom.substring(0, 3).equals("cut")) {
			processArg(command);
		} else if (command.length() > 2 && lowCom.equals("pas")) {
			paste();
		} else {
			if (command.length() != 0) {

				// If user forgets a space between input everything is
				// shifted over once
				if (command.length() > 1 && command.charAt(1) != ' ') {
					command = command.charAt(0) + " "
							+ command.substring(1, command.length());
				}

				switch (command.toLowerCase().charAt(0)) {
				case 'b':
					saved = false;
					if (command.length() > 2) {
						list.add(currentLine,
								command.substring(2, command.length()));
					} else {
						System.out
								.println("Please specify a sentence to add.");
					}
					break;
				case 'i':
					saved = false;
					if (command.length() > 2) {
						insert(command);
					} else {
						System.out
								.println("Please specify a sentence to add.");
					}
					break;
				case 'e':
					saved = false;
					if (command.length() > 2) {
						currentLine = list.size();
						insert(command);
					} else {
						System.out
								.println("Please specify a sentence to add.");
					}
					break;
				case 'm':
					move(command);
					break;
				case 'u':
					moveUp(command);
					break;
				case 'r':
					saved = false;
					remove(command);
					break;
				case 'd':
					if (command.length() > 2) {
						processArg(command);
					} else {
						display(0, 0);
					}
					break;
				case 'c':
					saved = false;
					currentLine = 1;
					list.clear();
					break;
				case 's':
					if (command.length() > 2) {
						save(command.substring(2, command.length()));
					} else {
						System.out
								.println("Please specify a filename.");
					}
					break;
				case 'l':
					if (command.length() > 2) {
						load(command.substring(2, command.length()));
					} else {
						System.out
								.println("Please specify a filename.");
					}
					break;
				case 'h':
					help();
					break;
				case 'x':
					check();
					break;
				default:
					System.out.println("Unknow Command");
				}
			}
		}
	}

	/******************************************************
	 * Returns the number of lines
	 * 
	 * @return size of list
	 ******************************************************/
	@Override
	public int nbrLines() {
		return list.size();
	}

	/******************************************************
	 * Returns the information on specified line
	 * 
	 * @param lineNbr
	 *            selected line
	 * @return data on specified line
	 ******************************************************/
	@Override
	public String getLine(int lineNbr) throws EditorException {
		return list.get(lineNbr);
	}

	/******************************************************
	 * Returns the information on current line
	 * 
	 * @return data on current line
	 ******************************************************/
	@Override
	public String getCurrentLine() {
		return list.get(currentLine);
	}

	/******************************************************
	 * Returns the current line number
	 * 
	 * @return current line
	 ******************************************************/
	@Override
	public int getCurrentLineNbr() {
		return currentLine;
	}

	/******************************************************
	 * Moves the current line down once or a specified amount
	 * 
	 * @param command
	 *            input from user
	 * @throws EditorException
	 *             when invalid command
	 * @return none
	 ******************************************************/
	private void move(String command) throws EditorException {
		if (command.length() < 2) {
			if (currentLine < list.size()) {
				currentLine++;
			}
		} else {
			try {
				// converts argument to int
				int i = Integer.parseInt(command.substring(2,
						command.length()));

				if (i + currentLine <= list.size()) {
					currentLine += i;
				} else {
					throw new EditorException("Move not possible.");
				}
			} catch (NumberFormatException e) {
				throw new EditorException(
						"Incorrect format for command 'm <n>'.");
			}
		}
	}

	/******************************************************
	 * Moves the current line up once or a specified amount
	 * 
	 * @param command
	 *            input from user
	 * @throws EditorException
	 *             when invalid command
	 * @return none
	 ******************************************************/
	private void moveUp(String command) throws EditorException {
		if (command.length() < 2) {
			if (currentLine != 1) {
				currentLine--;
			}
		} else {
			try {
				// Converts argument to int
				int i = Integer.parseInt(command.substring(2,
						command.length()));

				if (currentLine - i > 0) {
					currentLine -= i;
				} else {
					throw new EditorException("Move not possible.");
				}
			} catch (NumberFormatException e) {
				throw new EditorException(
						"Incorrect format for command 'u <n>'.");
			}
		}
	}

	/******************************************************
	 * Inserts line after currently selected line
	 * 
	 * @param command
	 *            input from user
	 * @return none
	 ******************************************************/
	private void insert(String command) {
		if (currentLine == 1 && list.isEmpty()) {
			list.add(command.substring(2, command.length()));
		} else {
			currentLine++;
			list.add(currentLine,
					command.substring(2, command.length()));
		}
	}

	/******************************************************
	 * Removes the current line or a specified amount of lines starting
	 * at the current line
	 * 
	 * @param command
	 *            input from user
	 * @throws EditorException
	 *             when invalid command
	 * @return none
	 ******************************************************/
	private void remove(String command) throws EditorException {
		if (!list.isEmpty()) {
			if (command.length() < 2) {
				list.remove(currentLine);

				// checks if currentLine is valid
				if (currentLine > list.size()) {
					currentLine = list.size();
				}
			} else {
				try {
					// converts argument to int
					int i = Integer.parseInt(command.substring(2,
							command.length()));

					if (i + currentLine - 1 <= list.size()) {
						// removes i lines
						for (int j = 0; j < i; j++) {
							list.remove(currentLine);
						}

					} else {
						throw new EditorException("Move not possible.");
					}
				} catch (NumberFormatException e) {
					throw new EditorException(
							"Incorrect format for command 'r <n>'.");
				}
			}
		} else {
			throw new EditorException("The buffer is empty.");
		}
	}

	/******************************************************
	 * Displays what is currently in the buffer starting at n1 to n2.
	 * (0,0) displays all lines.
	 * 
	 * @param n1
	 *            starting line to display
	 * @param n2
	 *            ending line to display
	 * @throws EditorException
	 *             when invalid command
	 * @return none
	 ******************************************************/
	private void display(int n1, int n2) throws EditorException {
		if (n1 == 0 && n2 == 0) {
			System.out.println("Start of file");

			// prints the lines and marks the current line
			for (int i = 1; i <= list.size(); i++) {
				if (currentLine == i) {
					System.out.println("* " + i + ": " + list.get(i));
				} else {
					System.out.println("  " + i + ": " + list.get(i));
				}
			}

			System.out.println("End of file");
		} else {

			// prints the specified lines
			while (n1 != n2 + 1) {
				if (n1 == currentLine) {
					System.out.println("* " + n1 + ": " + list.get(n1));
				} else {
					System.out.println("  " + n1 + ": " + list.get(n1));
				}
				n1++;
			}
		}
	}

	/******************************************************
	 * Saves buffer to specified file name
	 * 
	 * @param fileName
	 *            name of the file to save
	 * @throws EditorException
	 *             when error saving to file
	 * @return none
	 ******************************************************/
	private void save(String fileName) throws EditorException {
		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					fileName)));
		} catch (IOException e) {
			throw new EditorException("Error saving to file");
		}

		// writes lines to file
		for (int i = 1; i <= list.size(); i++) {
			out.println(list.get(i));
		}

		out.close();
		System.out.println(fileName + ", was saved successfully.");
		saved = true;
	}

	/******************************************************
	 * Loads specified file into buffer
	 * 
	 * @param fileName
	 *            name of file to load
	 * @throws EditorException
	 *             when file not found
	 * @return none
	 ******************************************************/
	private void load(String fileName) throws EditorException {
		list.clear();

		try {
			// open the data file
			Scanner fileReader = new Scanner(new File(fileName));

			// reads lines and adds to buffer
			while (fileReader.hasNext()) {
				list.add(fileReader.nextLine());
			}

			fileReader.close();
			currentLine = 1;
		}

		// could not find file
		catch (FileNotFoundException error) {
			throw new EditorException("File not found.");
		}
	}

	/******************************************************
	 * Displays a help menu
	 * 
	 * @return none
	 ******************************************************/
	private void help() {
		System.out.println(" -Command-\t\t\t -Meaning-");
		System.out
				.println("b <sentence>\t\t"
						+ "Insert sentence before the current line.");
		System.out
				.println("i <sentence>\t\t"
						+ "Insert sentence after the current line.");
		System.out
				.println("e <sentence>\t\t"
						+ "Insert sentence after the last line.");
		System.out
				.println("m \t\t\t"
						+ "Move current line indicator down 1 position.");
		System.out
				.println("m <n>\t\t\t"
						+ "Move current line indicator down 'n' positions.");
		System.out
				.println("u \t\t\t"
						+ "Move current line indicator up 1 position.");
		System.out
				.println("u <n>\t\t\t"
						+ "Move current line indicator up 'n' positions.");
		System.out.println("r \t\t\t"
				+ "Remove the current line.");
		System.out
				.println("r <n>\t\t\t"
						+ "Removes 'n' lines starting at the current line.");
		System.out.println("d \t\t\tDisplay lines in buffer.");
		System.out
				.println("d <n1> <n2>\t\t"
						+ "Display lines from lines 'n1' to 'n2'.");
		System.out.println("c \t\t\tClear lines in buffer.");
		System.out
				.println("s <filename>\t\tSave contents to filename.");
		System.out
				.println("l <filename>\t\tLoad contents from filename.");
		System.out.println("h \t\t\tDisplay help menu.");
		System.out.println("x \t\t\tExit the editor.");
		System.out
				.println("rev \t\t\tReverses the lines in the buffer.");
		System.out
				.println("cut <n1> <n2>\t\t"
						+ "Cut lines from the file from line 'n1' to line 'n2'.");
		System.out
				.println("pas \t\t\t"
						+ "Paste the clipboard contents before the current line.");
	}

	/******************************************************
	 * Reverses the order in the buffer
	 * 
	 * @return none
	 ******************************************************/
	private void reverse() {
		Stack<String> s = new Stack<String>();

		// writes buffer to stack in reverse order
		while (!list.isEmpty()) {
			s.push(list.remove(1));
		}

		// writes stack to buffer
		while (!s.isEmpty()) {
			list.add(s.pop());
		}
	}

	/******************************************************
	 * Cuts the lines specified by the user and saves to clip board
	 * 
	 * @param n1
	 *            starting line
	 * @param n2
	 *            ending line
	 * @throws EditorException
	 *             when invalid command
	 * @return none
	 ******************************************************/
	private void cut(int n1, int n2) throws EditorException {
		clipboard.clear();

		// remove lines from buffer to clip board
		for (int i = 0; i < n2 - n1 + 1; i++) {
			clipboard.add(list.remove(n1));
		}

		// ensures current line is valid
		if (list.size() == 0) {
			currentLine = 1;
		} else if (currentLine > list.size()) {
			currentLine = list.size();
		}
	}

	/******************************************************
	 * Pastes the lines in the clip board before the current line
	 * 
	 * @return none
	 ******************************************************/
	private void paste() {
		// adds lines in clip board to buffer before current
		for (int i = clipboard.size(); i > 0; i--) {
			list.add(currentLine, clipboard.get(i));
		}
	}

	/******************************************************
	 * Processes commands with two arguments
	 * 
	 * @param command
	 *            user input
	 * @throws EditorException
	 *             when invalid command
	 * @return none
	 ******************************************************/
	private void processArg(String command) throws EditorException {
		String input[] = command.split(" ");
		int inputInt[] = new int[input.length];

		if (input.length == 3) {
			try {

				// converts string to int
				for (int i = 1; i < input.length; i++) {
					inputInt[i] = Integer.parseInt(input[i].trim());
				}

				// ensures numbers are valid inputs
				if (inputInt[1] > 0 && inputInt[1] <= inputInt[2]
						&& inputInt[2] <= list.size()) {

					// checks the command
					if (command.toLowerCase().subSequence(0, 3)
							.equals("cut")) {
						cut(inputInt[1], inputInt[2]);
					} else {
						display(inputInt[1], inputInt[2]);
					}
				} else {
					throw new EditorException("Command not possible.");
				}
			} catch (NumberFormatException e) {
				throw new EditorException(
						"Incorrect format for command.");
			}
		} else {
			throw new EditorException("Incorrect format for command.");
		}
	}

	/******************************************************
	 * Checks if user really wants to quit
	 * 
	 * @throws EditorException
	 *             when invalid command
	 * @return none
	 ******************************************************/
	private void check() throws EditorException {
		if (saved) {
			// quits program
			EditorCLI.setRunning(false);
		} else {
			System.out
					.println("There are unsaved changes.\n"
							+ "Are you sure you want to quit? (y/n) ");
			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in));

			try {
				if (br.readLine().charAt(0) == 'y') {
					EditorCLI.setRunning(false);
					System.out.println("Bye!");
				} else {
					System.out.println("Close canceled.");
				}
			} catch (Exception e) {
				throw new EditorException("Close canceled.");
			}
		}
	}
}
