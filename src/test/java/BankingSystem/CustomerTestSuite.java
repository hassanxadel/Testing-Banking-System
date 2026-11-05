package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


    public class CustomerTestSuite {
    private Customer customer;
    private Account account;

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        customer = new Customer();
        account = new Account();
    }
    @After
    public void tearDown() {
    }

    

    @Test
    public void testCustomerCreation() {
        assertNotNull(customer);
    }

    @Test
    public void testAddingAndClosingAccounts() {
        customer.add_account(account);
        assertEquals(1, customer.get_accounts().size());

        customer.close_account(account);
        assertEquals(0, customer.get_accounts().size());
    }
@Test
public void testCustomerInformationRetrieval() {
    customer.set_fname("Mohamed");
    customer.set_mname("Mohamed");
    customer.set_lname("Abutrika");
    customer.set_id("123456789");
    assertEquals("Mohamed Mohamed Abutrika", customer.get_name()); 
    assertEquals("123456789", customer.get_id());
}

}

    


