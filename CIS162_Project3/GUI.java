import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/************************************************
 * Displays the GUI for Craps
 * 
 * @author Sean Crowley
 * @version 2/15/2014
 ************************************************/
public class GUI extends JPanel{
    
    /** Instantiates the Craps object */
    private Craps craps;
    
    /** Instantiates two GVdie objects */
    private GVdie dice1, dice2;
    
    /** Instantiates two JButtons */
    private JButton comeOut, roll;
    
    /** Instantiates two JLabels */
    private JLabel message, point;
    
    /************************************************
     * Constructor for object of class GUI
     ************************************************/
    public GUI(){
        
        //Creates an object for Craps
        craps = new Craps();
        
        //Creates objects for dice
        dice1 = craps.getDie(1);
        dice2 = craps.getDie(2);
        
        //Instantiates two buttons
        comeOut = new JButton("Come Out");
        roll = new JButton("Roll");
        
        //Creates a listener for buttons
        ButtonListener listener = new ButtonListener();
        comeOut.addActionListener(listener);
        roll.addActionListener(listener);
        
        //Disables the roll button at start
        roll.setEnabled(false);
        
        //Instantiates two labels
        message = new JLabel(craps.getMessage());
        point = new JLabel("Points: " + Integer.toString(craps.credit));
        
        //Adds message to constructor
        add(message);
        
        //Creates new panel then adds to constructor
        JPanel p1 = new JPanel();
        p1.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        p1.add(dice1);
        p1.add(dice2);
        add(p1);
        
        //Creates new panel then adds to constructor
        JPanel p2 = new JPanel();
        p2.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        p2.add(comeOut);
        p2.add(roll);
        p2.add(point);
        add(p2);
        
        //Sets window size and background color
        setPreferredSize(new Dimension(300,200));
        setBackground(Color.gray);
    }
    
    /************************************************
     * Creates an action listener for buttons
     ************************************************/
    private class ButtonListener implements ActionListener{
        
        /***************************************************
         * Performs operations based on what button pressed
         ***************************************************/
        public void actionPerformed(ActionEvent event){
            
            //Performs comeOut() when comout button pressed
            if(event.getSource() == comeOut){
                craps.comeOut();
                if(craps.okToRoll() == true){
                    comeOut.setEnabled(false);
                    roll.setEnabled(true);
                }
            }
            
            //Performs roll() when roll buton pressed
            if(event.getSource() == roll){
                craps.roll();
                if(craps.okToRoll() == false){
                    comeOut.setEnabled(true);
                    roll.setEnabled(false);
                }
            }
            message.setText(craps.getMessage());
            point.setText("Credits: " + Integer.toString(craps.credit));
        }
    }
    
    /************************************************
     * Main method to start game and GUI
     ************************************************/
    public static void main(String[] args){
        JFrame f = new JFrame("Craps");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new GUI());
        f.pack();
        f.setVisible(true);
    }
}
