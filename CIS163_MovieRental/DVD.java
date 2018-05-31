import java.io.Serializable;
import java.util.GregorianCalendar;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The date the DVD was rented */
	protected GregorianCalendar bought;

	/** The date the DVD is due back */
	protected GregorianCalendar dueBack;

	/** The title of the DVD */
	protected String title;

	/** The name of the person who is renting the DVD */
	protected String nameOfRenter;

	public DVD() {
	}

	public GregorianCalendar getBought() {
		return bought;
	}

	public GregorianCalendar getDueBack() {
		return dueBack;
	}

	public String getTitle() {
		return title;
	}

	public String getNameOfRenter() {
		return nameOfRenter;
	}

	public void setBought(GregorianCalendar g) {
		bought = g;
	}

	public void setDueBack(GregorianCalendar g) {
		dueBack = g;
	}

	public void setTitle(String s) {
		title = s;
	}

	public void setNameOfRenter(String s) {
		nameOfRenter = s;
	}
	
	public String toString(){
		return "Name:  " + nameOfRenter + ", Title:  " + title + ", Rented On:  "
			+ bought.get(GregorianCalendar.MONTH) + "/"
			+ bought.get(GregorianCalendar.DAY_OF_MONTH) + "/" + bought.get(GregorianCalendar.YEAR)
			+ ", Due Back On:  " + dueBack.get(GregorianCalendar.MONTH) + "/"
			+ dueBack.get(GregorianCalendar.DAY_OF_MONTH) + "/"
			+ dueBack.get(GregorianCalendar.YEAR);
	}
}
