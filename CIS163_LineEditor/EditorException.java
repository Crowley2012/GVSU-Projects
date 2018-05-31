/**
 * EditorException object is thrown when an error condition occurs in
 * the editor. Examples: sending invalid commands to the editor or
 * attempt to process a valid command that results in an error
 * situation.
 * 
 * @author Sean Crowley
 * @version November 2014 THIS CLASS IS ALL DONE FOR YOU. DO NOT MAKE
 *          ANY CHANGES.
 */
public class EditorException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an EditorException with the specified detail message.
	 * 
	 * @param message
	 *            the detail message
	 */
	public EditorException(String message) {
		super(message);
	}

}
