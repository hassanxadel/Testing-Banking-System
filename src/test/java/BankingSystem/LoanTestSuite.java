package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoanTestSuite {
    private Loan loan;

        @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        loan = new Loan(10000.0, 0.1);
    }

        @After
    public void tearDown() {
    
}
    
    @Test
    public void testLoanCreation() {
        assertNotNull(loan);
    }

    @Test
    public void testLoanApproval() {
        loan.set_approved(true);
        assertTrue(loan.is_approved());
    }
}

