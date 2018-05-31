import java.text.NumberFormat;

/***********************************************************************************
 * Provides Bank Account Information
 * 
 * @author Sean Crowley
 * @version Feb 2
 ***********************************************************************************/

public class BankAccount {

	/** Name of Customer */
	private String name;

	/** Customers Account ID */
	private int id;

	/** Customers Account Balance */
	private double balance;

	/***********************************************************************************
	 Constructor for object of class BankAccount
	 ***********************************************************************************/
	public BankAccount(String _name, int _id, double _balance) {
		name = _name;
		balance = _balance;
		id = _id;
	}

	/***********************************************************************************
	 Adds amount to the balance of the account
	 ***********************************************************************************/
	public void makeDeposit(double amount) {
		balance += amount;
	}

	/***********************************************************************************
	 Subtracts amount from the balance of the account
	 ***********************************************************************************/
	public void makeWithdrawal(double amount) {
		balance -= amount;
	}

	/***********************************************************************************
	 Returns the balance of the account
	 ***********************************************************************************/
	public double getBalance() {
		return balance;
	}

	/***********************************************************************************
	 Returns the name of the account
	 ***********************************************************************************/
	public String getName() {
		return name;
	}

	/***********************************************************************************
	 Returns the ID of the account
	 ***********************************************************************************/
	public int getID() {
		return id;
	}

	/***********************************************************************************
	 Creates a String description of the Bank Accounts current state
	 ***********************************************************************************/
	public String toString() {
		NumberFormat fmt1 = NumberFormat.getCurrencyInstance();
		return name + "\t" + id + "\t" + fmt1.format(balance);
	}

	/***********************************************************************************
	 Main method used for testing
	 ***********************************************************************************/
	public static void main(String[] args) {
		BankAccount sean = new BankAccount("Sean Crowley", 367, 2145.56);
		System.out.println(sean);

		BankAccount joe = new BankAccount("Joe Smith", 5643, 1000.0);
		joe.makeDeposit(247.35);
		System.out.println(joe);

		BankAccount sam = new BankAccount("Sam Down", 1568, 55689.35);
		sam.makeWithdrawal(333.95);
		System.out.println(sam);

	}
}
