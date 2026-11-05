package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


public class AccountTestSuite {
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
    public void testAccountCreation() {
        assertNotNull(account);
        assertNotNull(account.get_account_number());
    }

    @Test
    public void testDeposit() {
        account.deposite(100.0);
        assertEquals(100.0, account.get_balance(), 0.001);
    }

    @Test
    public void testWithdraw() {
        account.deposite(100.0);
        account.withdraw(50.0);
        assertEquals(50.0, account.get_balance(), 0.001);
    }

    @Test
    public void testTransactionSending() {
        account.deposite(100.0);
        account.send_transaction(receiver, 100.0, true); // Deposit
        account.send_transaction(receiver, 50.0, false); // Regular transaction
        assertEquals(50.0, account.get_balance(), 0.001);
        assertEquals(50.0, receiver.get_balance(), 0.001);
    }

  @Test
    public void testTransactionHistoryRetrieval() {
        account.deposite(100.0);
        account.send_transaction(receiver, 100.0, true); // Deposit
        account.send_transaction(receiver, 50.0, false); // Regular transaction
        assertEquals(2, account.get_transactions().size());
    }

    @Test
    public void testCardAdditionAndRetrieval() {
        Card card = new Card(account, CardType.Debit, Network.Visa);
        account.add_card(card);
        assertEquals(1, account.get_cards().size());
        assertEquals(card, account.get_cards().get(0));
    }
}
