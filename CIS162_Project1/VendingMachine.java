import java.text.NumberFormat;

/*******************************************************
 * Vending Machine
 * 
 * @author Sean Crowley
 * @version February 10
 *******************************************************/

public class VendingMachine {

    /** Price of Beverage */
    private int price;
    
    /** Beverages in the Machine */
    private int stock;
    
    /** Amount of Money put in Machine */
    private int credit;
    
    /** Number of Completed Sales */
    private int sales;

    /*******************************************************
     * Constructor for object of class VendingMachine
     *******************************************************/
    public VendingMachine() {
        credit = 0;
        sales = 0;
        stock = 10;
        price = 150;
    }

    /*******************************************************
     * Constructor for object of class Vending Machine with ability to change Stock
     *******************************************************/
    public VendingMachine(int units) {
        credit = 0;
        sales = 0;
        price = 150;
        stock = units;
    }

    /*******************************************************
     * Returns the price of the beverage
     *******************************************************/
    public int getPrice() {
        return price;
    }

    /*******************************************************
     * Returns the number of beverages in the machine
     *******************************************************/
    public int getInventory() {
        return stock;
    }

    /*******************************************************
     * Returns the amount of cash put in the machine
     *******************************************************/
    public int getCredit() {
        return credit;
    }

    /*******************************************************
     * Returns the total number of sales
     *******************************************************/
    public int getTotalSales() {
        return sales;
    }

    /*******************************************************
     * Displays the start menu of the machine
     *******************************************************/
    public void displayGreeting() {
        if (stock > 0) {
            System.out.println("\nVending Machine\nDrinks: " + formatDollars(price) + "\n");
        } else {
            System.out.println("Out of stock");
        }
    }

    /*******************************************************
     * Restocks machine with selected amount
     *******************************************************/
    public void restock(int units) {
        if (units >= 0) {
            stock += units;
        }
    }

    /*******************************************************
     * Cancels sale and returns money
     *******************************************************/
    public void cancelSale() {
        credit = 0;
        System.out.println("Sale Cancelled");
        displayGreeting();
    }

    /*******************************************************
     * Controls what the machine does with the money
     *******************************************************/
    public void insertMoney(int amount) {
        if (amount == 5 || amount == 10 || amount == 25 || amount == 100) {
            if (stock > 0) {
                credit += amount;
                if (credit >= 150) {
                    System.out.println("Price: " + formatDollars(price) + " Current Credit: " + formatDollars(credit));
                    System.out.println("Please make a selection.");
                } else {
                    System.out.println("Price: " + formatDollars(price) + " Current Credit: " + formatDollars(credit));
                }
            } else {
                displayGreeting();
            }
        } else {
            System.out.println("Can not accept payment");
            System.out.println("Price: " + formatDollars(price) + " Current Credit: " + formatDollars(credit));
        }
    }

    /*******************************************************
     * Dispenses Drink and updates the display
     *******************************************************/
    public void makeSelection() {
        System.out.println("Drink is being dispensed...");
        if (credit > 150) {
            credit = credit - 150;
            System.out.println("Your change is " + formatDollars(credit));
        }
        System.out.print("\n");

        stock -= 1;
        sales += 1;
        credit = 0;
    }

    /*******************************************************
     * Converts an integer into currency
     *******************************************************/
    public String formatDollars(int amount) {
        String x, x2;
        x = "$" + amount / 100.00;
        x2 = x.substring(x.length() - 2, x.length());

        if (x2.substring(0, 1).equals(".")) {
            x += "0";
        }

        return x;
    }

    /*******************************************************
     * Displays the current status of the machine
     *******************************************************/
    public void displayStatus() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        System.out.println("Remaining inventory: " + stock + "\nTotal Sales: " + fmt.format(sales * price / 100.0));
    }

    /*******************************************************
     * Simulates buying a drink by the entered number
     *******************************************************/
    public void simulateSales(int sales) {
        restock(sales);
        while (sales > 0) {
            insertMoney(100);
            insertMoney(25);
            insertMoney(25);
            makeSelection();
            sales--;
        }
        displayStatus();
    }

    /*******************************************************
     * Main Method that allows for testing of the objects
     *******************************************************/
    public static void main(String[] args) {
        VendingMachine vm1 = new VendingMachine();
        vm1.displayGreeting();
        System.out.println("Credit: " + vm1.getCredit() + " Stock: " + vm1.getInventory() + " Price: " + vm1.getPrice() + " Sales: " + vm1.getTotalSales() + "\n");
        vm1.insertMoney(100);
        vm1.cancelSale();
        vm1.insertMoney(100);
        vm1.insertMoney(100);
        vm1.makeSelection();
        vm1.displayStatus();
        System.out.println("Credit: " + vm1.getCredit() + " Stock: " + vm1.getInventory() + " Price: " + vm1.getPrice() + " Sales: " + vm1.getTotalSales() + "\n");

        VendingMachine vm2 = new VendingMachine();
        vm2.displayGreeting();
        System.out.println("Credit: " + vm2.getCredit() + " Stock: " + vm2.getInventory() + " Price: " + vm2.getPrice() + " Sales: " + vm2.getTotalSales() + "\n");
        vm2.insertMoney(25);
        vm2.insertMoney(5);
        vm2.insertMoney(10);
        vm2.insertMoney(10);
        vm2.insertMoney(25);
        vm2.insertMoney(25);
        vm2.insertMoney(25);
        vm2.insertMoney(25);
        vm2.makeSelection();
        vm2.simulateSales(2);
        System.out.println("Credit: " + vm2.getCredit() + " Stock: " + vm2.getInventory() + " Price: " + vm2.getPrice() + " Sales: " + vm2.getTotalSales() + "\n");
        vm2.formatDollars(1560);
    }
}
