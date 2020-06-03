package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TrumpFactoryTest {
    final TrumpFactory trumpFactory = new TrumpFactory();

    @Test
    void trumpFactoryMakesNumericTrump() {
        final Trump trump = trumpFactory.trump("♦️", "5");

        assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.FIVE)));
    }
}