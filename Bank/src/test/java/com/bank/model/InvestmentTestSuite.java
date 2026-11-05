package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvestmentTestSuite {
    private Investment investment;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        investment = new Investment(10000.0, 0.05, 12, 1);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testInvestmentCreation() {
        assertNotNull(investment);
    }

    @Test
    public void testInvestmentInformationRetrieval() {
        assertEquals(10000.0, investment.get_price(), 0.001);
        assertEquals(0.05, investment.get_return_rate(), 0.001);
        assertEquals(12, investment.get_duration_months());
        assertEquals(1, investment.get_payment_duration());
    }
}
