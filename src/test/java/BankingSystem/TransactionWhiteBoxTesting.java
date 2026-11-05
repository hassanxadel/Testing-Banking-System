package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionWhiteBoxTesting {
    
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
    }
        @After
    public void tearDown() {
    
}

    @Test
    public void testTransactionAccepted() {
        Transaction transaction = new Transaction(sender, receiver, 50.0, true); // Withdrawal transaction
        assertTrue(transaction.is_accepted());
    }

    @Test
    public void testTransactionRejectedInsufficientFunds() {
        sender.deposite(100.0);
        Transaction transaction = new Transaction(sender, receiver, 200.0, false); // Attempt to send more than balance
        assertFalse(transaction.is_accepted());
    }

    @Test
    public void testTransactionAcceptedDeposit() {
        Transaction transaction = new Transaction(sender, receiver, 50.0, true); // Deposit transaction
        assertTrue(transaction.is_accepted());
    }

    @Test
    public void testTransactionAcceptedZeroAmount() {
        Transaction transaction = new Transaction(sender, receiver, 0.0, false); // Zero amount transaction
        assertTrue(transaction.is_accepted());
    }

    @Test
    public void testTransactionAcceptedDepositZeroAmount() {
        Transaction transaction = new Transaction(sender, receiver, 0.0, true); // Zero amount deposit transaction
        assertTrue(transaction.is_accepted());
    }
}
