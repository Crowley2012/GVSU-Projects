import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

/***********************************************************************
 * GUI front end for Lottery Simulation 
 * 
 * @author Scott Grissom
 * @version February 1, 2013
 **********************************************************************/
public class GUI extends JPanel{

    /** text fields */
    private JTextField field1;
    private JTextField prize;
    private JTextField state;

    /** results box */
    private JTextArea results;
    private JFrame theGUI;

    /** menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenu reportsMenu;
    private JMenuItem quitItem;
    private JMenuItem openItem; 
    private JMenuItem stateItem;
    private JMenuItem reportItem;
    
    /** buttons */
    private JButton random;
    private JButton pick;
    private JButton multiple;
    private JButton biggest;
    private JButton oldest;
    private JButton major;
    
    /** LotteryMachine object */
    private LotteryMachine tick;
    
    /** Boolean check if file is open */
    private boolean fileSelected;
    
    
             
    /**********************************************
     * Main method used for testing
     **********************************************/
    public static void main(String arg[]){
        // the tradition five lines of code
        // normally placed here are 
        // inserted throughout the construtor
        new GUI();

    }

    /*********************************************************************
    Constructor - instantiates and displays all of the GUI commponents
     *********************************************************************/
    public GUI(){
        
        tick = new LotteryMachine();
        
        fileSelected = false;
        
        //ActionListener
        ButtonListener listener = new ButtonListener();
        
        //Instantiates buttons
        random = new JButton("Random Numbers");
        pick = new JButton("Pick Numbers");
        multiple = new JButton("Multiple Games");
        biggest = new JButton("Biggest Winner");
        oldest = new JButton("Oldest Player");
        major = new JButton("All Major Winners");
        
        //Sets size of buttons
        random.setMaximumSize(new Dimension(200,30));
        pick.setMaximumSize(new Dimension(200,30));
        multiple.setMaximumSize(new Dimension(200,30));
        biggest.setMaximumSize(new Dimension(200,30));
        oldest.setMaximumSize(new Dimension(200,30));
        major.setMaximumSize(new Dimension(200,30));
        
        //Add button listener to buttons
        random.addActionListener(listener);
        pick.addActionListener(listener);
        multiple.addActionListener(listener);
        biggest.addActionListener(listener);
        oldest.addActionListener(listener);
        major.addActionListener(listener);
        
        //Instantiates text fields
        state = new JTextField(5);
        prize = new JTextField(5);
        
        //Setups frame
        theGUI = new JFrame("Mega Million Lottery");
        theGUI.setVisible(true);
        theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // create the Results Area for the Center area
        results = new JTextArea(20,30);
        JScrollPane scrollPane = new JScrollPane(results);
        theGUI.add(BorderLayout.CENTER, scrollPane);
        
        // create the South Panel
        JPanel southPanel = new JPanel();
        southPanel.add(new JLabel("Prize $ "));
        southPanel.add(prize);
        southPanel.add(Box.createHorizontalStrut(30));
        southPanel.add(new JLabel("ST "));
        southPanel.add(state);
        theGUI.add(BorderLayout.SOUTH, southPanel);

        // create the East Panel  
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(Box.createVerticalGlue());  
        eastPanel.add(new JLabel("Draw Ticket"));
        eastPanel.add(Box.createVerticalStrut(5));
        eastPanel.add(random);
        eastPanel.add(Box.createVerticalStrut(5));
        eastPanel.add(pick);
        eastPanel.add(Box.createVerticalStrut(5));
        eastPanel.add(multiple);
        eastPanel.add(Box.createVerticalStrut(30));
        eastPanel.add(new JLabel("Check Winners"));
        eastPanel.add(Box.createVerticalStrut(5));
        eastPanel.add(biggest);
        eastPanel.add(Box.createVerticalStrut(5));
        eastPanel.add(oldest);
        eastPanel.add(Box.createVerticalStrut(5));
        eastPanel.add(major);
        eastPanel.add(Box.createVerticalStrut(50));
        theGUI.add(BorderLayout.EAST, eastPanel);
        
        // set up File menus
        setupMenus();
        theGUI.pack();
    }

    /*********************************************************************
    List all entries given an ArrayList of tickets

    @param list Lists all tickets
     *********************************************************************/
    public void displayTickets(ArrayList <LotteryTicket> list){
        
        //Adds all the tickets in ArrayList
        for(LotteryTicket t: list){
            results.append(t + "\n");
        }
    }
             
    /**********************************************
     * Manually sets the selected numbers
     **********************************************/
    private void pickNumbers(){
        int b1, b2, b3, b4, b5, mega;
        
        b1 = Integer.parseInt(JOptionPane.showInputDialog("First Ball"));
        b2 = Integer.parseInt(JOptionPane.showInputDialog("Second Ball"));
        b3 = Integer.parseInt(JOptionPane.showInputDialog("Third Ball"));
        b4 = Integer.parseInt(JOptionPane.showInputDialog("Fourth Ball"));
        b5 = Integer.parseInt(JOptionPane.showInputDialog("Fifth Ball"));
        mega = Integer.parseInt(JOptionPane.showInputDialog("Mega Ball"));
        
        tick.drawTicket(b1, b2, b3, b4, b5, mega);
    }

  
    /*********************************************************************
    Respond to menu selections and button clicks

    @param e the button or menu item that was selected
     *********************************************************************/
    private class ButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e){

            LotteryTicket c = null;

            //Checks if a file has been opened
            if(fileSelected){
                
                //East Side Buttons
                if (e.getSource() == random){
                    tick.drawTicket();
                }
                if (e.getSource() == pick){
                    pickNumbers();
                }
                if (e.getSource() == multiple){
                    results.append(tick.multipleGames() + "\n\n");
                }
                if (e.getSource() == biggest){
                    results.append("Biggest Winner \n\n" + tick.getBiggestWinner().toString() + "\n\n");
                }
                if (e.getSource() == oldest){
                    results.append("Oldest Player \n\n" + tick.getOldestPlayer().toString() + "\n\n");
                }
                if (e.getSource() == major){
                    
                    //Checks if state has been entered
                    if(prize.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Prize empty");
                    }else{
                        results.append("Major Winners\n\n");
                        
                        //Appends all major winners
                        for(LotteryTicket t:tick.getMajorWinners(Double.parseDouble(prize.getText()))){
                            results.append(t + "\n\n");
                        }
                    }
                }
                
                //Items in reports menu
                if (e.getSource() == stateItem){
                    if(state.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "State empty");
                    }else{
                        results.append(tick.createReport(state.getText()) + "\n\n");
                    }
                }
                if (e.getSource() == reportItem){
                    results.append(tick.createReport() + "\n\n");
                }
            }else{
                
                //Error message to pick a file
                if(e.getSource() != quitItem && e.getSource() != openItem){
                    JOptionPane.showMessageDialog(null, "Please select a file!");
                }
            }
                
            // menu item - quit
            if (e.getSource() == quitItem){
                System.exit(1);
            }
            // menu item - open
            if (e.getSource() == openItem){
                openFile();
            }
            
        }
    }
    
    
        /*********************************************************************
        Set up the menu items
        *********************************************************************/
        private void setupMenus(){

            // create menu components
            fileMenu = new JMenu("File");
            quitItem = new JMenuItem("Quit");
            openItem = new JMenuItem("Open...");
            reportsMenu = new JMenu("Reports");
            stateItem = new JMenuItem("by State");
            reportItem = new JMenuItem("All Tickets");

            // assign action listeners
            ButtonListener ml = new ButtonListener();
            quitItem.addActionListener(ml);
            openItem.addActionListener(ml);
            stateItem.addActionListener(ml);
            reportItem.addActionListener(ml);

            // display menu components
            fileMenu.add(openItem);
            fileMenu.add(quitItem);
            reportsMenu.add(reportItem);
            reportsMenu.add(stateItem);    
            menus = new JMenuBar();

            menus.add(fileMenu);
            menus.add(reportsMenu);
            theGUI.setJMenuBar(menus);
        } 
        
        /*********************************************************************
        In response to the menu selection - open a data file
         *********************************************************************/
         private void openFile(){
             JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
             int returnVal = fc.showOpenDialog(theGUI);

             // If file selected it is read and fileSelected is set to true
             if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filename = fc.getSelectedFile().getName();
                tick.readTickets(filename);         
                fileSelected = true;
            }
        }       
    }
