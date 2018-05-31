import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***********************************************************
 * EditorCLI class provides a text-based user interface to the editor
 * supporting a read-process-print loop.
 * 
 * @author Sean Crowley
 * @version November 2014
 ***********************************************************/
public class EditorCLI {

	/** true if the program is running */
	private static boolean running;

	/***********************************************************
	 * Main method used to launch the editor
	 * 
	 * @return none
	 ***********************************************************/
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));
		Editor editor = new Editor();
		running = true;

		// runs the program until running is false
		while (running) {
			try {
				editor.processCommand(br.readLine());
			} catch (EditorException | IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/***********************************************************
	 * Sets the running variable
	 * 
	 * @param b
	 *            sets running to true/false
	 * @return none
	 ***********************************************************/
	public static void setRunning(boolean b) {
		running = b;
	}

}
