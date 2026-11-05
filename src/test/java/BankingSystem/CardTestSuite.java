package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardTestSuite {
    private Account account;
    private Card card;

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
      
    @After
    public void tearDown() {
    }
    
    @Before
    public void setUp() {
        account = new Account();
        card = new Card(account, CardType.Debit, Network.Visa);
    }

    @Test
    public void testCardCreation() {
        assertNotNull(card);
        assertNotNull(card.get_card_number());
    }

    @Test
    public void testCardInformationRetrieval() {
        assertEquals(account.get_name(), card.get_holder_name());
        assertEquals(CardType.Debit, card.get_type());
        assertEquals(Network.Visa, card.get_network());
    }

    @Test
    public void testPINSetting() {
        card.set_pin(1234);
        assertEquals(1234, card.get_pin());
    }
}
