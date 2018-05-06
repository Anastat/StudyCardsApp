package com.project.studycards;

import com.project.studycards.model.Card;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCardClass {
    @Test
    public void createNewDeck () {
        Card card = new Card ("test", "test");
        assertEquals("test", card.getAnswer());
    }

    @Test
    public void incrementCount() {
        Card card = new Card ("test", "test");
        card.incrementCount();
        assertEquals(1, card.getCount());
    }
}
