import java.util.ArrayList;
import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.text.NumberFormat;
import java.lang.*;

/**
 * LotteryMachine provides information on LotteryTicket
 * 
 * @author Sean Crowley
 * @version March 16
 */

public class LotteryMachine{
    
    /** List holds LotteryTicket */
    private ArrayList<LotteryTicket> tickets;
    
    /** Ball numbers */
    private int b1, b2, b3, b4, b5, megaBall;
    
    /**********************************************
     * Setting a constructor for LotteryMachine
     **********************************************/
    public LotteryMachine(){
        tickets = new ArrayList<LotteryTicket>();
        b1 = 0;
        b2 = 0;
        b3 = 0;
        b4 = 0;
        b5 = 0;
        megaBall = 0;
    }
        
    /**********************************************
     * Adds ticket to ArrayList tickets
     * 
     * @param t Custom LotteryTicket
     **********************************************/
    public void addTicket(LotteryTicket t){
        tickets.add(t);
    }
        
    /**********************************************
     * Counts the amount of tickets in ArrayList
     * 
     * @return Size of the ArrayList
     **********************************************/
    public int countTickets(){
        return tickets.size();
    }
        
    /**********************************************
     * Randomly picks numbers
     **********************************************/
    private void pickNumbers(){
        Random gen = new Random();
        ArrayList<Integer> num = new ArrayList<Integer>();
        int nums[] = new int[5];
        
        //Adds 1 through 75 to ArrayList
        for(int x=1; x<=75; x++){
            num.add(x);
        }
        
        nums[0] = num.remove(gen.nextInt(num.size()));
        nums[1] = num.remove(gen.nextInt(num.size()));
        nums[2] = num.remove(gen.nextInt(num.size()));
        nums[3] = num.remove(gen.nextInt(num.size()));
        nums[4] = num.remove(gen.nextInt(num.size()));
        
        //Sorts the numbers from least to greatest
        Arrays.sort(nums);
        
        b1 = nums[0];
        b2 = nums[1];
        b3 = nums[2];
        b4 = nums[3];
        b5 = nums[4];
        
        megaBall = gen.nextInt(15) + 1;
    }
        
    /**********************************************
     * Counts the number of ticket numbers that 
     *  match the selected numbers
     *  
     *@param t LotteryTicket to be checked for
     *  matches
     *@return number of matches
     **********************************************/
    private int countMatches(LotteryTicket t){
        int count = 0;
        if(t.hasBall(b1)){
            count++;
        }
        if(t.hasBall(b2)){
            count++;
        }
        if(t.hasBall(b3)){
            count++;
        }
        if(t.hasBall(b4)){
            count++;
        }
        if(t.hasBall(b5)){
            count++;
        }
        return count;
    }
            
    /**********************************************
     * Sets the prize of LotteryTicket depending on
     *  number of matches
     **********************************************/
    private void makePayouts(){
        for(LotteryTicket t:tickets){
            if(countMatches(t) == 5 && t.hasMegaBall(megaBall)){
                t.setPrize(5000000.00);
            }else if(countMatches(t) == 5){
                t.setPrize(1000000.00);
            }else if(countMatches(t) == 4 && t.hasMegaBall(megaBall)){
                t.setPrize(5000.00);
            }else if(countMatches(t) == 4){
                t.setPrize(500.00);
            }else if(countMatches(t) == 3 && t.hasMegaBall(megaBall)){
                t.setPrize(50.00);
            }else if(countMatches(t) == 3){
                t.setPrize(5.00);
            }else if(countMatches(t) == 2 && t.hasMegaBall(megaBall)){
                t.setPrize(5.00);
            }else if(countMatches(t) == 1 && t.hasMegaBall(megaBall)){
                t.setPrize(2.00);
            }else if(countMatches(t) == 0 && t.hasMegaBall(megaBall)){
                t.setPrize(1.00);
            }else{
                t.setPrize(0.00);
            }
        }
        
    }
        
    /**********************************************
     * Formats the numbers into a String
     * 
     * @return Formated String of selected numbers
     **********************************************/
    private String formatNumbers(){
        return "Selected Numbers: " + b1 + " " + b2 + " " + b3 + " " + b4 + " " + b5 + "   " + megaBall;
    }
        
    /**********************************************
     * Draws a ticket by calling pickNumbers()
     *  then sets the payout per ticket
     **********************************************/
    public void drawTicket(){
        pickNumbers();
        makePayouts();
    }
        
    /**********************************************
     * Allows user to input specific values for 
     *   ball numbers
     *   
     * @param ball1 number for first selected number
     * @param ball2 number for second selected number
     * @param ball3 number for third selected number
     * @param ball4 number for fourth selected number
     * @param ball5 number for fifth selected number
     * @param m number for megaBall
     **********************************************/
    public void drawTicket(int ball1, int ball2, int ball3, int ball4, int ball5, int m){
        b1 = ball1;
        b2 = ball2;
        b3 = ball3;
        b4 = ball4;
        b5 = ball5;
        megaBall = m;
        makePayouts();
    }
            
    /**********************************************
     * Reads all the tickets in file and stores to
     *  ArrayList
     *  
     * @param filename File that will be read
     **********************************************/
    public void readTickets(String filename){
        
        //Attempts to read filename
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            String info;
            
            //Adds each line in file as a LotteryTicket
            while(sc.hasNext()){
                info = sc.nextLine();
                LotteryTicket t = new LotteryTicket(info);
                tickets.add(t);
            }
            sc.close();
        }
        
        //If file not found provide an error message
        catch(IOException e){
            System.out.println("Failed to read: " + filename);
        }
        
    }
             
    /**********************************************
     * Creates a formated report of Lottery
     * 
     * @return formatted report as String
     **********************************************/
    public String createReport(){
        double avg = 0;
        int sold = 0;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        
        //Calculates the average prize of all tickets
        for(LotteryTicket t:tickets){
            avg += t.getPrize();
            sold ++;
        }
        
        avg = avg / sold;
        
        return "Report For All Sales\n\n" + formatNumbers() + "\nTickets Sold: " + sold
            + "\nAverage Prize: " + fmt.format(avg) + "\n\nBiggest Winner\n\n" + getBiggestWinner();
    }
             
    /**********************************************
     * Creates a report based on a state
     * 
     * @param st state to create a report on
     * @return formated report as String based on 
     *  inputed state
     **********************************************/
    public String createReport(String st){
        int sold = 0;
        double avg = 0.0;
        boolean change = false;
        LotteryTicket big = tickets.get(0);
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        
        //Checks through all tickets in LotteryTicket
        for(LotteryTicket t:tickets){
            
            //Checks if ticket is from state
            if(t.getState().equalsIgnoreCase(st)){
                sold ++;
                avg += t.getPrize();
                
                //Sets big to current lottery ticket to avoid errors
                if(change == false){
                    big = t;
                    change = true;
                }
                
                //Checks current ticket has higher prize than ticket big
                //and if so replaces big with current ticket
                if(t.getPrize() > big.getPrize()){
                    big = t;
                }
            }
        }
        
        avg = avg / sold;
        
        //If no tickets are from state then big is equal to null
        if(big == tickets.get(0)){
            big = null;
            avg = 0;
        }
        
        
        return "Report For All Sales In " + st.toUpperCase() + "\n\n" + formatNumbers() + "\nTickets Sold: " 
           + sold + "\nAverage Prize: " + fmt.format(avg) + "\n\nBiggest Winner In " + st.toUpperCase() 
           + "\n\n" + big;
    }
             
    /**********************************************
     * Finds the oldest player in tickets
     * 
     * @return The oldest player
     **********************************************/
    public LotteryTicket getOldestPlayer(){
        int month = 99;
        int day = 99;
        int year = 9999;
        LotteryTicket oldest = tickets.get(0);
        
        //Checks the day, month, year and compares to each ticket
        // in the ArrayList
        for(LotteryTicket t:tickets){
            if(t.getYear() <= year){
                if(t.getMonth() <= month){
                    if(t.getDay() < day){
                        year = t.getYear();
                        month = t.getMonth();
                        day = t.getDay();
                        oldest = t;
                    }
                }
            }
        }
        
        return oldest;
    }
             
    /**********************************************
     * Finds the Biggest Winner in tickets
     * 
     * @return the ticket of the biggest winner
     **********************************************/
    public LotteryTicket getBiggestWinner(){
        LotteryTicket prize = tickets.get(0);
        
        //Checks all tickets and compares their prizes
        for(LotteryTicket t:tickets){
            if(t.getPrize() > prize.getPrize()){
                prize = t;
            }
        }
        return prize;
    }
             
    /**********************************************
     * Finds tickets that are greater than or equal
     *  to the amount
     *  
     * @param amount amount to check for the prize 
     *  of tickets
     * @return all the tickets equal to or greater
     *  than amount
     **********************************************/
    public ArrayList<LotteryTicket> getMajorWinners(double amount){
        ArrayList<LotteryTicket> major = new ArrayList<LotteryTicket>();
        
        //creates an arraylist of all tickets greater than equal to amount
        for(LotteryTicket t:tickets){
            if(Double.compare(t.getPrize(), amount) >= 0){
                major.add(t);
            }
        }
        
        return major;
    }
             
    /**********************************************
     * Runs the lottery until somebody wins
     * 
     * @return String of the winner of lottery
     **********************************************/
    public String multipleGames(){
        int games = 0;
        double prize = 5_000_000.00;
        double origPrize = 5_000_000.00;
        boolean won = false;
        
        //Loop that runs until win = true
        while(!won){
            drawTicket();
            games ++;
            prize += 1_500_000.00;
            
            //compares the prize to the original prize if they are equal
            // the winners prize is set to the incremented prize
            if(Double.compare(getBiggestWinner().getPrize(), origPrize) == 0){
                won = true;
                getBiggestWinner().setPrize(prize);
            }
            
        }       
        return "Winner of Mega Millions\n\n" + getBiggestWinner() + "\n\nGames Played: " + games;
    }
             
    /**********************************************
     * Main method used for testing
     **********************************************/
    public static void main(String[] args){
        LotteryMachine m = new LotteryMachine();
        
        m.readTickets("TicketInfo.txt");
        m.drawTicket();
        
        System.out.println(m.createReport() + "\n");
        System.out.println("Oldest Player\n\n" + m.getOldestPlayer() + "\n");
        System.out.println(m.createReport("mi"));
        System.out.println("\n____________________________________________\n\nMajor Winners");
        
        //used to print major winners without brackets
        for(LotteryTicket t:m.getMajorWinners(50.00)){
            System.out.println("\n" + t);
        }
        
        System.out.println("\n____________________________________________\n");
        System.out.println(m.multipleGames());
        
    }
}
