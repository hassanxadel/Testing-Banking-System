package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


public class AccountWhiteBoxTesting {
    private Account account;
    private Account receiver;

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
       @Before
    public void setUp() {
        account = new Account();
        receiver = new Account();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testWithdrawalInsufficientFunds() {
        account.deposite(100.0);
        account.send_transaction(receiver, 200.0, false); // Attempt to withdraw more than balance
        assertEquals(100.0, account.get_balance(), 0.001);
    }

    @Test
    public void testTransactionSendingRejected() {
        account.deposite(100.0);
        account.send_transaction(receiver, 200.0, false); // Attempt to send more than balance
        assertEquals(0.0, receiver.get_balance(), 0.001); // Receiver should not receive any amount
    }

    @Test
    public void testTransactionSendingAccepted() {
        account.deposite(100.0);
        account.send_transaction(receiver, 50.0, false);
        assertEquals(50.0, account.get_balance(), 0.001);
        assertEquals(50.0, receiver.get_balance(), 0.001);
    }

    @Test
    public void testWithdrawalBoundary() {
        account.deposite(100.0);
        account.send_transaction(receiver, 100.0, false);
        assertEquals(0.0, account.get_balance(), 0.001);
    }
}
