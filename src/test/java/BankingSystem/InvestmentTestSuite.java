package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class InvestmentTestSuite {
    private Investment investment;
    
      @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        investment = new Investment(10000.0, 0.05, 12, 1);
    }
  
        @After
    public void tearDown() {
    
}

    @Test
    public void testInvestmentCreation() {
        assertNotNull(investment);
    }

    @Test
    public void testInvestmentInformationRetrieval() {
        assertEquals(10000.0, investment.get_price(), 0.001);
        assertEquals(0.05, investment.get_return_rate(), 0.001);
        assertEquals(12, investment.get_duration_months());
        assertEquals(1, investment.get_payment_duration());
    }
}
    
 
