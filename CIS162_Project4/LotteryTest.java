import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
/*****************************************************
 * The test class LotteryTest.
 *
 * @author  Scott Grissom
 * @version (a version number or a date)
 ****************************************************/
public class LotteryTest{
    private LotteryMachine m;

    /************************************************
     * Sets up the test fixture.
     *
     * Called before every test case method.
     ***********************************************/
    @Before
    public void setUp()
    {
        m = new LotteryMachine(); 
        String info;
        
        info = "Winner, Smith, San Francisco, CA, 49401, 4/22/1963, 1,2,3,4,5,6";
        LotteryTicket tix = new LotteryTicket(info);
        m.addTicket(tix);
        
        info = "Oldest, Smith, St. Louis, MO, 49401, 4/20/1963, 1,2,3,4,6,6";
        tix = new LotteryTicket(info);
        m.addTicket(tix);

        info = "Joe, Smith, San Francisco, CA, 49401, 4/21/1963, 8,9,10,11,12,13";
        tix = new LotteryTicket(info);
        m.addTicket(tix);
    }
    
/*********************************************************
 * Ticket Constructor
 ********************************************************/
    @Test
    public void testTicketConstructor()
    {
        String info = "Joe, Smith, San Francisco, CA, 49401, 4/20/1963, 1,2,3,4,5,6";
        LotteryTicket tix = new LotteryTicket(info);        
        Assert.assertEquals("LotteryTicket() first name does not match", 
                "Joe", tix.getFirst());                
        Assert.assertEquals("LotteryTicket() last name does not match", 
                "Smith", tix.getLast());                  
        Assert.assertEquals("LotteryTicket() birth day does not match", 
                20, tix.getDay());  
        Assert.assertEquals("LotteryTicket() birth month does not match", 
                4, tix.getMonth());  
        Assert.assertEquals("LotteryTicket() birth year does not match", 
                1963, tix.getYear());
        tix.setPrize(1000);        
        Assert.assertEquals("Lottery Ticket: set or get Prize has problems", 
                1000.0, tix.getPrize(), 0.1);  
        } 
    
/*********************************************************
 * Read Datafile
 ********************************************************/
    @Test
    public void testReadFile()
    {
        LotteryMachine m = new LotteryMachine();
        m.readTickets("TicketInfo.txt");
        Assert.assertEquals("readTickets() not correct", 
                50000, m.countTickets());                
    }  
    
/*********************************************************
 * Big Winner
 ********************************************************/    
    @Test
    public void testBigWinner()
    {
        m.drawTicket(1,2,3,4,5,6);        
        LotteryTicket tix = m.getBiggestWinner();
        Assert.assertEquals("getBiggestWinner() or drawTicket(1,2,3,4,5,6) not correct", 
                "Winner", tix.getFirst());                
    }  

/*********************************************************
 * General Report
 ********************************************************/    
    @Test
    public void testReport()
    {
        m.drawTicket(1,2,3,4,5,6); 
        String str = m.createReport();
        Assert.assertTrue("createReport() not correct - did you format the average prize? '$335,000.00'", 
                str.contains("$1,668,333"));                
    }  

/*********************************************************
 * State Report
 ********************************************************/    
    @Test
    public void testStateReport()
    {
        m.drawTicket(1,2,3,4,5,6); 
        String str = m.createReport("TX");
        Assert.assertTrue("createStateReport(TX) not correct - did you format the average prize? '$0.00'", 
                str.contains("$0.00"));                
        str = m.createReport("MO");
        Assert.assertTrue("createReport(MO) not correct - did you format the average prize? '$5,000.00'", 
                str.contains("$5,000.00"));  
    }  

/*********************************************************
 * Major Winners
 ********************************************************/    
    @Test
    public void testMajorWinners()
    {
        m.drawTicket(1,2,3,4,5,6);        
        ArrayList <LotteryTicket> tix = m.getMajorWinners(50);
        Assert.assertEquals("getMajorWinners() not correct", 
                2, tix.size());                
    }  
 
/*********************************************************
 * Oldest Player
 ********************************************************/    
    @Test
    public void testOldest()
    {
        LotteryTicket tix = m.getOldestPlayer();
        Assert.assertEquals("getOldestPlayer() not correct", 
                "Oldest", tix.getFirst());                
    }   
        
    
}
