import static org.junit.Assert.*;
import org.junit.*;
/***************************************************
 * The test class CrapsTest.
 *
 * @author Scott Grissom
 * @version January 20. 2014
 **************************************************/
public class CrapsTest{

    private Craps game;
    private GVdie d1, d2;
    private final int MAX_GAMES = 100;
    
    public CrapsTest()
    {

    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        game = new Craps(); 
        d1 = game.getDie(1);
        d2 = game.getDie(2);
                
    }
    
    private int getTotal(){
        return d1.getValue() + d2.getValue();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testConstructor()
    {
        Assert.assertEquals("Craps(): credits should start at 10", 
                10, game.getCredits()); 
        Assert.assertEquals("Craps(): point should start at -1", 
                -1, game.getPoint());                 
    }    
    
    @Test
    public void testComeOut()
    {
        int beforeTurn = game.getCredits();
        Assert.assertFalse("Comeout(): should not be OK to roll", 
                game.okToRoll()); 
            
        game.comeOut();
        if(getTotal() == 7 || getTotal() == 11)
                Assert.assertEquals("Comeout(): should have won with 7 or 11", 
                beforeTurn, game.getCredits()-1); 
        else if(getTotal() == 2 || getTotal() == 3 || getTotal() == 12){
                Assert.assertEquals("Comeout(): should have lost with 2,3 or 12", 
                beforeTurn, game.getCredits()+1); 
        }else
           Assert.assertTrue("Comeout(): should be OK to roll",game.okToRoll());
    }
    
    public void testRoll()
    {
        int beforeTurn = game.getCredits();
        int point = game.getPoint();
        Assert.assertTrue("Comeout(): should be OK to roll", 
                game.okToRoll()); 
        
        game.roll();
        if(getTotal() == point){
           Assert.assertEquals("roll(): should have won with 7", 
                beforeTurn+1, game.getCredits()); 
           Assert.assertEquals("roll(): point should be -1 at end of turn", 
                -1, game.getPoint());          
        }else if(getTotal() == 7){
           Assert.assertEquals("roll(): should have lost with 2,3 or 11", 
                beforeTurn-1, game.getCredits()); 
           Assert.assertEquals("roll(): point should be -1 at end of turn", 
                -1, game.getPoint());          
        }else{      
           Assert.assertTrue("roll(): should be OK to roll",game.okToRoll());
    }
}
    
    @Test
    public void testTurn()
    {
        int beforeTurn = game.getCredits();
        testComeOut();
        while(game.okToRoll()){
            game.roll();
        }
        Assert.assertTrue("End of turn: credits should be >= 0", 
                game.getCredits() >= 0);  
        Assert.assertFalse("End of turn: credits should have changed", 
                beforeTurn == game.getCredits());         
       
        
    }  
    @Test
    public void setCredits(){
        game.setCredits(99);
           Assert.assertEquals("setCredits(): point should have been set to 99", 
                99, game.getCredits());  
        game.setCredits(-99);
           Assert.assertEquals("setCredits(): should ignore a negative value", 
                99, game.getCredits());                 

    }    
    @Test
    public void testNoCredits(){
        game.setCredits(0);
        int beforeTotal = getTotal();
        int beforeCredits = game.getCredits();
        game.comeOut();
        Assert.assertEquals("comeOut(): should not roll with zero credits", 
                beforeTotal, getTotal());          
    }
    
    @Test
    public void testManyTurns(){
        game.setCredits(MAX_GAMES);
        for(int i = 1; i <= MAX_GAMES; i++)
            testTurn();
    }

     
}