package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTestSuite {
    private Employee employee;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        employee = new Employee();
    }

    @AfterEach
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
        employee.set_role(Role.TELLER);
        employee.set_salary(30000);
        assertEquals(Role.TELLER, employee.get_role());
        assertEquals(30000, employee.get_salary());
    }
}
