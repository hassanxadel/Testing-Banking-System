package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class OfficeTestSuite {
    private Office office;

        @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
      @Before
    public void setUp() {
        office = new Office();
    }

        @After
    public void tearDown() {
    
}
  
    @Test
    public void testOfficeCreation() {
        assertNotNull(office);
    }

    @Test
    public void testOfficeInformationRetrieval() {
        office.set_office_name("Main Office");
        office.set_region("North");
        office.set_state("State");
        office.set_province("Province");
        office.set_address("123 Main St");
        assertEquals("Main Office", office.get_office_name());
        assertEquals("North", office.get_region());
        assertEquals("State", office.get_state());
        assertEquals("Province", office.get_province());
        assertEquals("123 Main St", office.get_address());
    }
}
