import java.text.NumberFormat;

/*************************************************
 * Formats input into useable tickets
 * 
 * @author Sean Crowley 
 * @version March 9
 *************************************************/

public class LotteryTicket
{
    /** Name of ticket owner */
    private String firstName, lastName;
    
    /** Birth date of ticket owner */
    private int day, month, year;
    
    /** Location of owner */
    private String city, state;
    
    /** Zip code of owner */
    private int zipCode;
    
    /** Ball numbers */
    private int b1, b2, b3, b4, b5, megaBall;
    
    /** Prize Amount */
    private double prize;
    
    /**********************************************
     * Setting a constructor for LotteryTicket
     * 
     * @param info Ticket information
     **********************************************/
    public LotteryTicket(String info){
        
        String ticketInfo[] = info.split(",|/");
        
        firstName = ticketInfo[0].trim();
        lastName = ticketInfo[1].trim();
        city = ticketInfo[2].trim();
        state = ticketInfo[3].trim();
        zipCode = Integer.parseInt(ticketInfo[4].trim());
        month = Integer.parseInt(ticketInfo[5].trim());
        day = Integer.parseInt(ticketInfo[6].trim());
        year = Integer.parseInt(ticketInfo[7].trim());
        b1 = Integer.parseInt(ticketInfo[8].trim());
        b2 = Integer.parseInt(ticketInfo[9].trim());
        b3 = Integer.parseInt(ticketInfo[10].trim());
        b4 = Integer.parseInt(ticketInfo[11].trim());
        b5 = Integer.parseInt(ticketInfo[12].trim());
        megaBall = Integer.parseInt(ticketInfo[13].trim());
    }
    
    /**********************************************
     * Returns the first name of owner
     * 
     * @return firstName Instance Variable
     **********************************************/
    public String getFirst(){
        return firstName;
    }
    
    /**********************************************
     * Returns the last name of owner
     * 
     * @return lastName Instance Variable
     **********************************************/
    public String getLast(){
        return lastName;
    }
    
    /**********************************************
     * Returns the city of owner
     * 
     * @return city Instance Variable
     **********************************************/
    public String getCity(){
        return city;
    }
    
    /**********************************************
     * Returns the state of owner
     * 
     * @return state Instance Variable
     **********************************************/
    public String getState(){
        return state;
    }
    
    /**********************************************
     * Returns the zip code of owner
     * 
     * @return zipCode Instance Variable
     **********************************************/
    public int getZipcode(){
        return zipCode;
    }
    
    /**********************************************
     * Returns the birth day of owner
     * 
     * @return day Instance Variable
     **********************************************/
    public int getDay(){
        return day;
    }
    
    /**********************************************
     * Returns the birth month of owner
     * 
     * @return month Instance Variable
     **********************************************/
    public int getMonth(){
        return month;
    }
    
    /**********************************************
     * Returns the birth year of owner
     * 
     * @return year Instance Variable
     **********************************************/
    public int getYear(){
        return year;
    }
    
    /**********************************************
     * Returns the prize
     * 
     * @return prize Instance Variable
     **********************************************/
    public double getPrize(){
        return prize;
    }
    
    /**********************************************
     * Sets the prize amount
     * 
     * @param amount Sets the prize of the ticket
     **********************************************/
    public void setPrize(double amount){
        prize = amount;
    }
    
    /**********************************************
     * Checks if any of the balls are equal to the 
     * val then returns true or false
     * 
     * @param val Value used to check with other 
     *  balls
     * @return boolean of val is equal to one of 
     *  the balls
     **********************************************/
    public boolean hasBall(int val){
        
        //Checks if the val is equal to any of the tickets balls
        if(val == b1 || val == b2 || val == b3 || val == b4 || val == b5){
            return true;
        }else{
            return false;
        }
    }
    
    /**********************************************
     * Checks if megaball is equal to val then 
     * returns true or false
     * 
     * @param val Value used to check if equal 
     *  to megaball
     * @return boolean of val is equal to megaBall
     **********************************************/
    public boolean hasMegaBall(int val){
        if(val == megaBall){
            return true;
        }else{
            return false;
        }
    }
    
    /**********************************************
     * Returns a String with formated information
     * on the owner
     * 
     * @return String of formated ticket
     **********************************************/
    public String toString(){
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        return firstName + " " + lastName + "\n" +
            city + ", " + state + " " + zipCode + "\n" +
            b1 + " " + b2 + " " + b3 + " " + b4 + " " + b5 + "\t" + megaBall + "\n" +
            "Prize: " + fmt.format(prize);
    }
       
    /**********************************************
     * Main method used to test other methods
     **********************************************/
    public static void main(String[] args){
        LotteryTicket ticket = new LotteryTicket("Sean,Crowley,Toledo,OH,43604,1/18/1956,13,16,21,23,59,1");
        System.out.println("Name: " + ticket.firstName + " " + ticket.lastName);
        System.out.println("Location: " + ticket.city + ", " + ticket.state + ", " + ticket.zipCode);
        System.out.println("Numbers: " + ticket.b1 + " " + ticket.b2 + " " + ticket.b3 + " " + ticket.b4 + " " + ticket.b5);
        System.out.println("Megaball: " + ticket.megaBall);
        System.out.println("Prize: " + ticket.prize + "\n\n");
        System.out.println(ticket.toString());
        
        
    }
}
