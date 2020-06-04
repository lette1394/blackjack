package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TrumpFactoryTest {
    @Test
    void trumpFactoryMakesNumericTrump() {
        final Trump trump = TrumpFactory.trump("♦️", "5");
        assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.FIVE)));
    }

    @Test
    void trumpFactoryMakesAceTrump() {
        final Trump trump = TrumpFactory.trump("♦️", "Ace");
        assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.ACE)));
    }

    @Test
    void trumpFactoryMakesJackTrump() {
        final Trump trump = TrumpFactory.trump("♦️", "Jack");
        assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.JACK)));
    }

    @Test
    void trumpFactoryMakesQueenTrump() {
        final Trump trump = TrumpFactory.trump("♦️", "Queen");
        assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.QUEEN)));
    }

    @Test
    void trumpFactoryMakesKingTrump() {
        final Trump trump = TrumpFactory.trump("♦️", "King");
        assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.KING)));
    }
}