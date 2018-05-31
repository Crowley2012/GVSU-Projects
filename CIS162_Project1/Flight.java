/***********************************************************************************
 * Provides Flight Information
 * 
 * @author Sean Crowley
 * @version Feb 2
 ***********************************************************************************/

public class Flight {

	/** Name of Flight */
	private String name;

	/** Start location of Flight */
	private String start;

	/** End location of Flight */
	private String end;

	/** Flight number */
	private int number;

	/***********************************************************************************
	 Constructor for object of class Flight
	 ***********************************************************************************/
	public Flight(String airline, int num, String orig, String d) {
		name = airline;
		number = num;
		start = orig;
		end = d;
	}

	/***********************************************************************************
	 Set the objects Flight Number
	 ***********************************************************************************/
	public void setNumber(int num) {
		number = num;
	}

	/***********************************************************************************
	 Return the objects Flight Number
	 ***********************************************************************************/
	public int getNumber() {
		return number;
	}

	/***********************************************************************************
	 Set the objects Flight Name
	 ***********************************************************************************/
	public void setAirline(String airline) {
		name = airline;
	}

	/***********************************************************************************
	 Return the objects Flight Name
	 ***********************************************************************************/
	public String getAirline() {
		return name;
	}

	/***********************************************************************************
	 Set the objects Flight Destination
	 ***********************************************************************************/
	public void setDestination(String d) {
		end = d;
	}

	/***********************************************************************************
	 Return the objects Flight Destination
	 ***********************************************************************************/
	public String getDestination() {
		return end;
	}

	/***********************************************************************************
	 Set the objects Flight Origin
	 ***********************************************************************************/
	public void setOrigin(String orig) {
		start = orig;
	}

	/***********************************************************************************
	 Return the objects Flight Origin
	 ***********************************************************************************/
	public String getOrigin() {
		return start;
	}

	/***********************************************************************************
	 Creates a String description of the Flights current state
	 ***********************************************************************************/
	public String toString() {
		return name + "\t" + number + "\t" + start + "\t" + end;
	}

	/***********************************************************************************
	 Main method used for testing
	 ***********************************************************************************/
	public static void main(String[] args) {
		Flight f1 = new Flight("Southwest", 345, "LAX", "JFK");
		Flight f2 = new Flight("United  ", 191, "CHI", "DFW");
		Flight f3 = new Flight("American", 763, "DFW", "GRR");

		System.out.println(f1 + "\n" + f2 + "\n" + f3);

		f1.setAirline("American");
		f2.setNumber(555);
		f3.setDestination("JFK");

		System.out.println("\n" + f1.getAirline() + "\t" + f2.getNumber() + "\t" + f3.getDestination() + "\n");
		System.out.println(f1 + "\n" + f2 + "\n" + f3);

	}
}
