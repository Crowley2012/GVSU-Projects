/**
 * IEditor interface specifies the methods that a line editor class
 * implements.
 * 
 * @author Sean Crowley
 * @version November 2014
 */
public interface IEditor {

	/**
	 * Processes the specified editor command.
	 * 
	 * @param command
	 *            the command to process
	 * 
	 * @throws EditorException
	 *             if the command is invalid or could result in an error
	 *             situation
	 */
	void processCommand(String command) throws EditorException;

	/**
	 * Returns the number of lines in the editor buffer/file.
	 * 
	 * @return the number of lines
	 */
	int nbrLines();

	/**
	 * Returns the line in the editor buffer at the given line number.
	 * 
	 * @param lineNbr
	 *            number of line to return
	 * @return line at the given line number
	 * @throws EditorException
	 *             if the line number is not valid (lineNbr <= 0 or
	 *             lineNbr > number of line in the buffer
	 */
	String getLine(int lineNbr) throws EditorException;

	/**
	 * Returns the line at the current line position.
	 * 
	 * @return the current line or null if there are no lines (i.e.,
	 *         buffer is empty)
	 */
	String getCurrentLine();

	/**
	 * Returns the current line number.
	 * 
	 * @return the current line number
	 */
	int getCurrentLineNbr();
}
