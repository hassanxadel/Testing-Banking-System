package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionWhiteBoxTesting {

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
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testTransactionAccepted() {
        Transaction transaction = new Transaction(sender, receiver, 50.0, true); // Deposit transaction
        assertTrue(transaction.is_accepted());
    }

    @Test
    public void testTransactionRejectedInsufficientFunds() {
        sender.deposite(100.0);
        Transaction transaction = new Transaction(sender, receiver, 200.0, false); // Attempt to send more than balance
        assertFalse(transaction.is_accepted());
    }

    @Test
    public void testTransactionAcceptedDeposit() {
        Transaction transaction = new Transaction(sender, receiver, 50.0, true); // Deposit transaction
        assertTrue(transaction.is_accepted());
    }

    @Test
    public void testTransactionAcceptedZeroAmount() {
        Transaction transaction = new Transaction(sender, receiver, 0.0, false); // Zero amount transaction
        assertTrue(transaction.is_accepted());
    }

    @Test
    public void testTransactionAcceptedDepositZeroAmount() {
        Transaction transaction = new Transaction(sender, receiver, 0.0, true); // Zero amount deposit transaction
        assertTrue(transaction.is_accepted());
    }
}
