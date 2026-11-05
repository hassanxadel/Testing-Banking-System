package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountWhiteBoxTesting {
    private Account account;
    private Account receiver;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        account = new Account();
        receiver = new Account();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testWithdrawalInsufficientFunds() {
        account.deposite(100.0);
        account.send_transaction(receiver, 200.0, false); // Attempt to withdraw more than balance
        assertEquals(100.0, account.get_balance(), 0.001);
    }

    @Test
    public void testTransactionSendingRejected() {
        account.deposite(100.0);
        account.send_transaction(receiver, 200.0, false); // Attempt to send more than balance
        assertEquals(0.0, receiver.get_balance(), 0.001); // Receiver should not receive any amount
    }

    @Test
    public void testTransactionSendingAccepted() {
        account.deposite(100.0);
        account.send_transaction(receiver, 50.0, false);
        assertEquals(50.0, account.get_balance(), 0.001);
        assertEquals(50.0, receiver.get_balance(), 0.001);
    }

    @Test
    public void testWithdrawalBoundary() {
        account.deposite(100.0);
        account.send_transaction(receiver, 100.0, false);
        assertEquals(0.0, account.get_balance(), 0.001);
    }
}
