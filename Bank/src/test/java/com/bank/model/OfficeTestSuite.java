package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OfficeTestSuite {
    private Office office;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        office = new Office();
    }

    @AfterEach
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
