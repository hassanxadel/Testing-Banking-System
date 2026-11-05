package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanTestSuite {
    private Loan loan;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        loan = new Loan(10000.0, 0.1);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testLoanCreation() {
        assertNotNull(loan);
    }

    @Test
    public void testLoanApproval() {
        loan.set_approved(true);
        assertTrue(loan.is_approved());
    }
}
