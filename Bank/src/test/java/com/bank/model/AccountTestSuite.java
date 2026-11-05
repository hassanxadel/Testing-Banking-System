package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTestSuite {
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
    public void testAccountCreation() {
        assertNotNull(account);
        assertNotNull(account.get_account_number());
    }

    @Test
    public void testDeposit() {
        account.deposite(100.0);
        assertEquals(100.0, account.get_balance(), 0.001);
    }

    @Test
    public void testWithdraw() {
        account.deposite(100.0);
        account.withdraw(50.0);
        assertEquals(50.0, account.get_balance(), 0.001);
    }

    @Test
    public void testTransactionSending() {
        account.deposite(100.0);
        account.send_transaction(receiver, 100.0, true); // External deposit: credits account itself
        account.send_transaction(receiver, 50.0, false); // Regular transfer to receiver
        assertEquals(150.0, account.get_balance(), 0.001);
        assertEquals(50.0, receiver.get_balance(), 0.001);
    }

    @Test
    public void testTransactionHistoryRetrieval() {
        account.deposite(100.0);
        account.send_transaction(receiver, 100.0, true); // Deposit
        account.send_transaction(receiver, 50.0, false); // Regular transaction
        assertEquals(2, account.get_transactions().size());
    }

    @Test
    public void testCardAdditionAndRetrieval() {
        Card card = new Card(account, CardType.DEBIT, Network.VISA);
        account.add_card(card);
        assertEquals(1, account.get_cards().size());
        assertEquals(card, account.get_cards().get(0));
    }
}
