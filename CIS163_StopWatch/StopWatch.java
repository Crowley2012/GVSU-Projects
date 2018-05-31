import java.io.*;
import java.util.Scanner;

/********************************************************************
 * StopWatch Project
 * 
 * @author Sean Crowley
 * @version 1.0
 ********************************************************************/

public class StopWatch {

	/** Current timer minutes */
	private int minutes;

	/** Current timer seconds */
	private int seconds;

	/** Current timer milliseconds */
	private int milliseconds;

	/** Allow timer to change */
	private static boolean mutable = true;

	/***************************************************************
	 * Getter method for Minutes
	 * @return minutes
	 ***************************************************************/
	public int getMinutes() {
		return minutes;
	}

	/***************************************************************
	 * Getter method for Seconds
	 * @return seconds
	 ***************************************************************/
	public int getSeconds() {
		return seconds;
	}

	/***************************************************************
	 * Getter method for Milliseconds
	 * @return milliseconds
	 ***************************************************************/
	public int getMilliseconds() {
		return milliseconds;
	}

	/***************************************************************
	 * Setter method for Minutes
	 * @param minutes number of minutes
	 ***************************************************************/
	public void setMinutes(int minutes) {
		if (mutable) {
			this.minutes = minutes;
		}
	}

	/***************************************************************
	 * Setter method for Seconds
	 * @param seconds number of seconds
	 ***************************************************************/
	public void setSeconds(int seconds) {
		if (mutable) {
			this.seconds = seconds;
		}
	}

	/***************************************************************
	 * Setter method for Milliseconds
	 * @param milliseconds number of milliseconds
	 ***************************************************************/
	public void setMilliseconds(int milliseconds) {
		if (mutable) {
			this.milliseconds = milliseconds;
		}
	}

	/***************************************************************
	 * Default constructor setting min, sec, milli to 0
	 ***************************************************************/
	public StopWatch() {
		minutes = 0;
		seconds = 0;
		milliseconds = 0;
	}

	/***************************************************************
	 * Constructor creates based on given min, sec, milli
	 * @param min number of minutes
	 * @param sec number of seconds
	 * @param milli number of milliseconds
	 ***************************************************************/
	public StopWatch(int min, int sec, int milli) {
		check(min, sec, milli);
		minutes = min;
		seconds = sec;
		milliseconds = milli;
		convert();
	}

	/***************************************************************
	 * Constructor creates based on given sec, milli
	 * @param sec number of seconds
	 * @param milli number of milliseconds
	 ***************************************************************/
	public StopWatch(int sec, int milli) {
		check(0, sec, milli);
		seconds = sec;
		milliseconds = milli;
		convert();
	}

	/***************************************************************
	 * Constructor creates based on given milli
	 * @param milli number of milliseconds
	 ***************************************************************/
	public StopWatch(int milli) {
		check(0, 0, milli);
		milliseconds = milli;
		convert();
	}

	/***************************************************************
	 * Constructor creates based on given min, sec, milli in string
	 * @param startTime given in this format "x:xx:xxx"
	 ***************************************************************/
	public StopWatch(String startTime) {
		String time[] = startTime.split(":");
		int timeInt[] = new int[time.length];

		if (!time[0].isEmpty()) {
			for (int i = 0; i < time.length; i++) {
				timeInt[i] = Integer.parseInt(time[i].trim());
			}
		}

		if (time[0].isEmpty()) {
			minutes = 0;
			seconds = 0;
			milliseconds = 0;
		} else if (time.length == 3) {
			check(timeInt[0], timeInt[1], timeInt[2]);
			minutes = timeInt[0];
			seconds = timeInt[1];
			milliseconds = timeInt[2];
		} else if (time.length == 2) {
			check(0, timeInt[0], timeInt[1]);
			minutes = 0;
			seconds = timeInt[0];
			milliseconds = timeInt[1];
		} else {
			check(0, 0, timeInt[0]);
			minutes = 0;
			seconds = 0;
			milliseconds = timeInt[0];
		}

		convert();
	}

	/***************************************************************
	 * Used to convert to milliseconds
	 ***************************************************************/
	private void convert() {

		while (milliseconds >= 1000) {
			milliseconds -= 1000;
			seconds++;
		}

		while (seconds >= 60) {
			seconds -= 60;
			minutes++;
		}

	}

	/***************************************************************
	 * Used to check if input is legal
	 * @param min inputed minutes
	 * @param sec inputed seconds
	 * @param milli inputed milliseconds
	 ***************************************************************/
	private void check(int min, int sec, int milli) {

		if (milli >= 1000 || milli < 0) {
			throw new IllegalArgumentException(
					"Incorrect Input For Milliseconds!");
		}

		if (sec >= 60 || sec < 0) {
			throw new IllegalArgumentException(
					"Incorrect Input For Seconds!");
		}

		if (min < 0) {
			throw new IllegalArgumentException(
					"Incorrect Input For Min!");
		}

	}

	/***************************************************************
	 * Check if StopWatch is equal to another StopWatch
	 * @param other StopWatch object
	 * @return true if StopWatchs are equal
	 ***************************************************************/
	public boolean equals(StopWatch other) {
		if (minutes == other.getMinutes() && 
				seconds == other.getSeconds() && 
				milliseconds == other.getMilliseconds()) {
			return true;
		} else {
			return false;
		}
	}

	/***************************************************************
	 * Check if StopWatch is equal to another StopWatch
	 * @param other StopWatch object
	 * @return true if StopWatchs are equal
	 ***************************************************************/
	public boolean equals(Object other) {
		if (!(other instanceof StopWatch)) {
			return false;
		}

		StopWatch newWatch = (StopWatch) other;

		if (minutes == newWatch.getMinutes() && 
				seconds == newWatch.getSeconds() && 
				milliseconds == newWatch.getMilliseconds()) {
			return true;
		} else {
			return false;
		}
	}

	/***************************************************************
	 * Check if StopWatch is equal to another StopWatch. Static
	 *   method.
	 * @param s1 StopWatch object
	 * @param s2 StopWatch object
	 * @return true if StopWatchs are equal
	 ***************************************************************/
	public static boolean equals(StopWatch s1, StopWatch s2) {
		if (s1.getMinutes() == s2.getMinutes() && 
				s1.getSeconds() == s2.getSeconds() && 
				s1.getMilliseconds() == s2.getMilliseconds()) {
			return true;
		} else {
			return false;
		}
	}

	/***************************************************************
	 * Check if current StopWatch is greater than sent StopWatch
	 * @param other StopWatch object
	 * @return int 1 if current watch is greater
	 * 			   0 if current watch is equal
	 * 			   -1 if current watch is less
	 ***************************************************************/
	public int compareTo(StopWatch other) {
		if (minutes > other.getMinutes()) {
			return 1;
		} else if (other.getMinutes() > minutes) {
			return -1;
		} else if (seconds > other.getSeconds()) {
			return 1;
		} else if (other.getSeconds() > seconds) {
			return -1;
		} else if (milliseconds > other.getMilliseconds()) {
			return 1;
		} else if (other.getMilliseconds() > milliseconds) {
			return -1;
		} else {
			return 0;
		}
	}

	/***************************************************************
	 * Adds milliseconds to StopWatch
	 * @param milliseconds number of milliseconds to be added
	 ***************************************************************/
	public void add(int milliseconds) {
		if (mutable) {
			for (int i = 0; i < milliseconds; i++) {
				inc();
			}
		}
	}

	/***************************************************************
	 * Adds 1 milliseconds to StopWatch
	 ***************************************************************/
	public void inc() {
		if (mutable) {
			milliseconds++;

			if (milliseconds >= 1000) {
				milliseconds = 0;
				seconds++;
			}

			if (seconds >= 60) {
				seconds = 0;
				minutes++;
			}
		}
	}

	/***************************************************************
	 * Adds another StopWatch to current StopWatch
	 * @param other StopWatch object
	 ***************************************************************/
	public void add(StopWatch other) {
		if (mutable) {
			int milli = other.getMilliseconds();
			int sec = other.getSeconds();
			int min = other.getMinutes();

			int total = (min * 60 * 1000) + (sec * 1000) + milli;

			add(total);
		}
	}

	/***************************************************************
	 * Creates a string from StopWatch data
	 * @return format data of StopWatch
	 ***************************************************************/
	public String toString() {
		String format = minutes + ":";

		if (seconds < 10) {
			format += "0" + seconds + ":";
		} else {
			format += seconds + ":";
		}

		if (milliseconds < 10) {
			format += "00" + milliseconds;
		} else if (milliseconds < 100) {
			format += "0" + milliseconds;
		} else {
			format += milliseconds;
		}

		return format;
	}

	/***************************************************************
	 * Saves current time to file
	 * @param fileName name of file
	 ***************************************************************/
	public void save(String fileName) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(
					new BufferedWriter(new FileWriter(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(toString());
		out.close();

	}

	/***************************************************************
	 * Loads time from file
	 * @param fileName name of file
	 ***************************************************************/
	public void load(String fileName) {
		if (mutable) {
			String loadedTime;

			try {
				// open the data file
				Scanner fileReader = new Scanner(new File(fileName));

				loadedTime = fileReader.next();

				String time[] = loadedTime.split(":");
				int timeInt[] = new int[time.length];

				if (!time[0].isEmpty()) {
					for (int i = 0; i < time.length; i++) {
						timeInt[i] = Integer.parseInt(time[i].trim());
					}
				}

				if (time[0].isEmpty()) {
					minutes = 0;
					seconds = 0;
					milliseconds = 0;
				} else if (time.length == 3) {
					check(timeInt[0], timeInt[1], timeInt[2]);
					minutes = timeInt[0];
					seconds = timeInt[1];
					milliseconds = timeInt[2];
				} else if (time.length == 2) {
					check(0, timeInt[0], timeInt[1]);
					minutes = 0;
					seconds = timeInt[0];
					milliseconds = timeInt[1];
				} else {
					check(0, 0, timeInt[0]);
					minutes = 0;
					seconds = 0;
					milliseconds = timeInt[0];
				}

				convert();

				fileReader.close();
			}

			// could not find file
			catch (FileNotFoundException error) {
				System.out.println("File not found ");
			}
		}
	}

	/***************************************************************
	 * Determines if a StopWatch can change
	 * @param mutate boolean
	 ***************************************************************/
	public static void setMutate(boolean mutate) {
		mutable = mutate;
	}

	/***************************************************************
	 * Main method used for testing
	 ***************************************************************/
	public static void main(String[] args) {

		System.out.println("Watches using different constructors\n");

		StopWatch s1 = new StopWatch("23:34:3");
		System.out.println("Time: " + s1);

		StopWatch s2 = new StopWatch("20:8");
		System.out.println("Time: " + s2);

		StopWatch s3 = new StopWatch("8");
		System.out.println("Time: " + s3);

		StopWatch s4 = new StopWatch(20, 2, 200);
		System.out.println("Time: " + s4);

		StopWatch s5 = new StopWatch(55, 999);
		System.out.println("Time: " + s5);

		StopWatch s6 = new StopWatch(356);
		System.out.println("Time: " + s6);

		StopWatch s7 = new StopWatch();
		System.out.println("Time: " + s7);

		System.out.println("\nIs S1 equal to S2? " +
				StopWatch.equals(s1, s2) + "\n");

		System.out.println("S1 Time: " + s1 + " + 12000");
		s1.add(12000);
		System.out.println("S1 Time: " + s1);

		System.out.println("\nIncrease S2 by 40000 using inc()");
		System.out.println("S2 Time: " + s2);
		for (int i = 0; i < 4000; i++) {
			s2.inc();
		}
		System.out.println("S2 Time: " + s2);

		System.out.println("\nCompare s3 and s4");
		System.out.println(s3.compareTo(s4));

		System.out.println("\nS5 toString()");
		System.out.println(s5.toString());

		System.out.println("\nTesting get methods.");
		System.out.println(s3.getMinutes() + " , " + s3.getSeconds() +
				" , " + s3.getMilliseconds());

		System.out.println("\nAdd 5000");
		s3.add(5000);
		System.out.println(s3.getMinutes() + " , " + s3.getSeconds() +
				" , " + s3.getMilliseconds());

		System.out.println("\nAdd S4 to S3");
		s3.add(s4);
		System.out.println("S3: " + s3.getMinutes() + " , " +
				s3.getSeconds() + " , " + s3.getMilliseconds());

		System.out.println("\nS4: " + s4.toString());

		System.out.println("\nSave S1: " + s1);
		s1.save("saved.txt");

		System.out.println("\nLoad S7 from file\n" + s7);
		s7.load("saved.txt");
		System.out.println(s7 + "\n\nTest set methods");

		s7.setMilliseconds(111);
		s7.setSeconds(22);
		s7.setMinutes(3);

		System.out.println(s7.toString());

	}
}
