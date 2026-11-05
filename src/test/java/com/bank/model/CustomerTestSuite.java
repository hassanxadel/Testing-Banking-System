package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTestSuite {
    private Customer customer;
    private Account account;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        account = new Account();
    }

    @AfterEach
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
