/************************************************
 * Game of Craps
 * 
 * @author Sean Crowley 
 * @version 2/15/2014
 ************************************************/

public class Craps{
    
    /** Declares a variable for points */
    public int point;
    
    /** Declares a variable for credits */
    public int credit;
    
    /** Declares a variable for messages */
    public String message;
    
    /** Declares a boolean for legal moves */
    public boolean legal;
    
    /** Instantiates a new object for GVdie */
    GVdie dice1 = new GVdie();
    
    /** Instantiates a new object for GVdie */
    GVdie dice2 = new GVdie();
    
    /************************************************
     * Constructor for object of class Craps
     ************************************************/
    public Craps(){
        point = -1;
        credit = 10;
        message = "Welcome to Craps!";
        legal = true;
    }
    
    /************************************************
     * Returns the amount of Credits
     ************************************************/
    public int getCredits(){
        return credit;
    }
    
    /************************************************
     * Returns the amount of Points
     ************************************************/
    public int getPoint(){
        return point;
    }
    
    /************************************************
     * Returns the current Message
     ************************************************/
    public String getMessage(){
        return message;
    }
    
    /************************************************
     * Returns if move is legal
     ************************************************/
    public boolean getLegal(){
        return legal;
    }
    
    /************************************************
     * Sets the amount of credits
     ************************************************/
    public void setCredits(int amount){
        if(amount >= 0 ){
            credit = amount;
        }
    }
    
    /************************************************
     * First phase of the game
     ************************************************/
    public void comeOut(){
        int x;
        
        //Checks if move is legal and the sum of the dice
        if(legal == true && credit > 1){
            dice1.roll();
            dice2.roll();
            x = dice1.getValue() + dice2.getValue();
            
            if(x == 7 || x == 11){
                credit += 1;
                message = "You rolled a " + x + " and gained a credit!";
            }else if(x == 2 || x == 3 || x == 12){
                credit -= 1;
                message = "You rolled a " + x + " and crapped out!";
            }else{
                point = x;
                legal = false;
                message = "You rolled a " + x + ". Move on to Phase 2.";
            }
        }else{
            message = "Cannot continue";
        }
    }
    
    /************************************************
     * Second phase of the game
     ************************************************/
    public void roll(){
        int x;
        
        //Checks if it is okay to roll and the sum of the roll
        if(okToRoll() == true){
            dice1.roll();
            dice2.roll();
            
            x = dice1.getValue() + dice2.getValue();
            
            if(x == 7){
                credit -= 1;
                point = -1;
                legal = true;
                message = "You rolled a " + x + " and lost a credit!";
            }else if(x == point){
                credit += 1;
                point = -1;
                legal = true;
                message = "You rolled a " + x + " and gained a credit!";
            }else{
                message = "You rolled a " + x + ". Please roll again.";
            }
        }
    }
    
    /************************************************
     * Returns if it is legal to move to phase 2
     ************************************************/
    public boolean okToRoll(){
        if(legal == false){
            return true;
        }else{
            return false;
        }
    }
    
    /************************************************
     * Returns the dice object
     ************************************************/
    public GVdie getDie(int num){
        if(num == 1){
            return dice1;
        }else if(num == 2){
            return dice2;
        }else{
            return null;
        }
    }
    
    /************************************************
     * Main class used to test the game
     ************************************************/
    public static void main(String[] args){
        Craps crap = new Craps();
        System.out.println(crap.message);
        while(crap.legal == true){
            crap.comeOut();
            System.out.println(crap.message);
        }
        while(crap.okToRoll() == true){
            crap.roll();
            System.out.println(crap.message);
        }
        System.out.println("Total Credits: " + crap.credit);
    }
}


    