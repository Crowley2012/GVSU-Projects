import javax.swing.JOptionPane;

public class Loops{

    private double num;
    private double total;
    private int x;
    private String numStr;

    public static void main(String[] args){    

    }
    
    public void average(){
        total = 0;
        x = 0;
        
        while(num >= 0){
            numStr = JOptionPane.showInputDialog("Enter an integer: ");
            num = Integer.parseInt(numStr);
            if(num >= 0){
                total += num;
                x ++;
            }
        }
        
        JOptionPane.showMessageDialog(null, "Sum: " + total + " Average: " + total/x);
    }
    
    public void sum(int low, int high){
        total = 0;
        
        while(low <= high){
            total += low;
            low ++;
        }
        JOptionPane.showMessageDialog(null, "Sum: " + total);
    }  
    
    public void infinite(){
        while(true){
            System.out.println("Running");
        }
    }
    
    public void countMultiples(int low, int high, int digit){
        x = 0;
        
        while(low <= high){
            if(low % digit == 0){
                x ++;
            }
            low ++;
        }
        System.out.println(x);
    } 
}
