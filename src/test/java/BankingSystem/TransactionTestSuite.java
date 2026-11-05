package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTestSuite {
    private Transaction transaction;
    private Account sender;
    private Account receiver;

        @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
     @Before
    public void setUp() {
        sender = new Account();
        receiver = new Account();
        transaction = new Transaction(sender, receiver, 50.0,true);
    }
        @After
    public void tearDown() {
    
}
  
    @Test
    public void testTransactionCreation() {
        assertNotNull(transaction);
    }

    @Test
    public void testTransactionInformationRetrieval() {
        assertEquals(sender, transaction.get_sender());
        assertEquals(receiver, transaction.get_reciever());
        assertEquals(50.0, transaction.get_transaction_amount(), 0.001);
    }
}
