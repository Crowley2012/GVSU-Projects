import static org.junit.Assert.*;
import org.junit.Test;

/***********************************************************
 * EditorTest class contains unit tests for Editor class.
 * 
 * @author Sean Crowley
 * @version November 2014
 ***********************************************************/
public class EditorTest {

	@Test
	public void testBeforeCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is an OO language.");
		ed.processCommand("i Ruby is another OO language.");
		ed.processCommand("b Programming.");
		ed.processCommand("m");
		ed.processCommand("b Test.");
		ed.processCommand("u 2");
		ed.processCommand("b Test2.");
		assertEquals("Test2.", ed.getCurrentLine());
		assertEquals("Test2.", ed.getLine(1));
		assertEquals("Java is an OO language.", ed.getLine(2));
		assertEquals("Programming.", ed.getLine(3));
		assertEquals("Test.", ed.getLine(4));
		assertEquals("Ruby is another OO language.", ed.getLine(5));
	}

	@Test
	public void testInsertCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is an OO language.");
		ed.processCommand("i Ruby is another OO language.");
		assertEquals("Ruby is another OO language.",
				ed.getCurrentLine());
		assertEquals("Java is an OO language.", ed.getLine(1));
	}

	@Test
	public void testMoveCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is an OO language.");
		ed.processCommand("i Ruby is another OO language.");
		ed.processCommand("i Test.");
		ed.processCommand("i Test2.");
		ed.processCommand("u 3");
		assertEquals("Java is an OO language.", ed.getCurrentLine());
		assertEquals("Java is an OO language.", ed.getLine(1));
		ed.processCommand("m");
		assertEquals("Ruby is another OO language.",
				ed.getCurrentLine());
		ed.processCommand("m 2");
		assertEquals("Test2.", ed.getCurrentLine());
	}

	@Test(expected = EditorException.class)
	public void testMoveException() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		assertEquals(3, ed.nbrLines());
		ed.processCommand("m 2");
	}

	@Test
	public void testMoveUpCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is an OO language.");
		ed.processCommand("i Ruby is another OO language.");
		ed.processCommand("i Test.");
		ed.processCommand("i Test2.");
		ed.processCommand("u");
		assertEquals("Test.", ed.getCurrentLine());
		assertEquals("Java is an OO language.", ed.getLine(1));
		ed.processCommand("u 2");
		assertEquals("Java is an OO language.", ed.getCurrentLine());
	}

	@Test(expected = EditorException.class)
	public void testMoveUpException() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		assertEquals(3, ed.nbrLines());
		ed.processCommand("u 5");
	}

	@Test
	public void testRemoveCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		ed.processCommand("i COBOL is old.");
		assertEquals(4, ed.nbrLines());
		ed.processCommand("u 2");
		ed.processCommand("r 2");
		assertEquals(2, ed.nbrLines());
		assertEquals("COBOL is old.", ed.getCurrentLine());
	}

	@Test(expected = EditorException.class)
	public void testRemoveException() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		assertEquals(3, ed.nbrLines());
		ed.processCommand("u 1");
		ed.processCommand("r 3");
	}

	@Test
	public void testDisplayCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i 1");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		ed.processCommand("i COBOL is old.");
		ed.processCommand("d");
	}

	@Test(expected = EditorException.class)
	public void testDisplayException() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		assertEquals(3, ed.nbrLines());
		ed.processCommand("d 1 5");
	}

	@Test(expected = EditorException.class)
	public void testDisplayException2() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		assertEquals(3, ed.nbrLines());
		ed.processCommand("d 2 1");
	}

	@Test
	public void testClearCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		ed.processCommand("i COBOL is old.");
		ed.processCommand("c");
		assertEquals(0, ed.nbrLines());
	}

	@Test
	public void testReverseCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		ed.processCommand("i COBOL is old.");
		assertEquals(4, ed.nbrLines());
		ed.processCommand("rev");
		assertEquals(4, ed.nbrLines());
		assertEquals("COBOL is old.", ed.getLine(1));
		assertEquals("Ruby is hot.", ed.getLine(2));
		assertEquals("Python is cooler.", ed.getLine(3));
		assertEquals("Java is cool.", ed.getLine(4));
	}

	@Test
	public void testLoadSaveCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		ed.processCommand("i COBOL is old.");
		ed.processCommand("s junit");
		ed.processCommand("c");
		ed.processCommand("l junit");
		assertEquals("COBOL is old.", ed.getLine(4));
		assertEquals("Ruby is hot.", ed.getLine(3));
		assertEquals("Python is cooler.", ed.getLine(2));
		assertEquals("Java is cool.", ed.getLine(1));
		assertEquals(4, ed.nbrLines());
	}

	@Test
	public void testHelpCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("h");
	}

	@Test
	public void testCutPasteCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Test1");
		ed.processCommand("i Test2");
		ed.processCommand("i Test3");
		ed.processCommand("i Test4");
		ed.processCommand("i Test5");
		ed.processCommand("cut 2 4");
		assertEquals("Test1", ed.getLine(1));
		assertEquals("Test5", ed.getLine(2));
		assertEquals(2, ed.nbrLines());
		ed.processCommand("u");
		ed.processCommand("pas");
		assertEquals("Test2", ed.getLine(1));
		assertEquals("Test3", ed.getLine(2));
		assertEquals("Test4", ed.getLine(3));
		assertEquals("Test1", ed.getLine(4));
		assertEquals("Test5", ed.getLine(5));
	}

	@Test(expected = EditorException.class)
	public void testCutException() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		assertEquals(3, ed.nbrLines());
		ed.processCommand("cut 1 5");
	}

	@Test(expected = EditorException.class)
	public void testCutException2() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is cool.");
		ed.processCommand("i Python is cooler.");
		ed.processCommand("i Ruby is hot.");
		assertEquals(3, ed.nbrLines());
		ed.processCommand("cut 2 1");
	}

}
