package com.project.studycards;

import com.project.studycards.model.Card;
import com.project.studycards.model.Deck;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestDeckClass {

    @Test
    public void createNewDeck () {
        Deck deck = new Deck ("test");
        assertEquals("test", deck.getName());
    }

    @Test
    public void addCardToDeck () {
        Deck deck = new Deck ("test");
        deck.addCard(new Card("test1", "test1"));
        assertEquals("test1", deck.getCards().get(0).getAnswer());
    }

    @Test
    public void getCardPriority() {
        Deck deck = new Deck("test");
        deck.addCard(new Card("test1", "test1"));
        assertEquals(1, deck.getCards().get(0).getPriority());
    }

    @Test
    public void isDeckEmpty() {
        Deck deck = new Deck("test");
        assertEquals(0, deck.getCards().size());
    }
}
