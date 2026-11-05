package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTestSuite {
    private Transaction transaction;
    private Account sender;
    private Account receiver;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        sender = new Account();
        receiver = new Account();
        transaction = new Transaction(sender, receiver, 50.0, true);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testTransactionCreation() {
        assertNotNull(transaction);
    }

    @Test
    public void testTransactionInformationRetrieval() {
        assertEquals(sender, transaction.get_sender());
        assertEquals(receiver, transaction.get_reciever());
        assertEquals(50.0, transaction.get_transaction_amount(), 0.001);
    }
}
