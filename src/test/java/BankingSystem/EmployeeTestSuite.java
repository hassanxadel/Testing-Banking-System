package BankingSystem;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


    public class EmployeeTestSuite {
    private Employee employee;

     @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        employee = new Employee();
    }
    
    @After
    public void tearDown() {
    
}
   

    @Test
    public void testEmployeeCreation() {
        assertNotNull(employee);
    }

   @Test
public void testEmployeeInformationRetrieval() {
    employee.set_fname("Ahmed");
    employee.set_lname("Zizo");
    employee.set_mname("Elsayed");
    employee.set_national_id("30008040101336");
    assertEquals("Ahmed Elsayed Zizo", employee.get_name());
    assertEquals("30008040101336", employee.get_national_id());
}


    @Test
    public void testRoleAndSalarySetting() {
        employee.set_role(Role.Teller);
        employee.set_salary(30000);
        assertEquals(Role.Teller, employee.get_role());
        assertEquals(30000, employee.get_salary());
    }
}
   
