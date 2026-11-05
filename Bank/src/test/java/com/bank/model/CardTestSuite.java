package com.bank.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTestSuite {
    private Account account;
    private Card card;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @AfterEach
    public void tearDown() {
    }

    @BeforeEach
    public void setUp() {
        account = new Account();
        card = new Card(account, CardType.DEBIT, Network.VISA);
    }

    @Test
    public void testCardCreation() {
        assertNotNull(card);
        assertNotNull(card.get_card_number());
    }

    @Test
    public void testCardInformationRetrieval() {
        assertEquals(account.get_name(), card.get_holder_name());
        assertEquals(CardType.DEBIT, card.get_type());
        assertEquals(Network.VISA, card.get_network());
    }

    @Test
    public void testPINSetting() {
        card.set_pin(1234);
        assertEquals(1234, card.get_pin());
    }
}
