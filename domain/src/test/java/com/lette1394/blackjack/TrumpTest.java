package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TrumpTest {

    @Test
    public void numericTrumpScoreIsNumber() {
        final Trump trump = new Trump(Trump.Suit.HEART, Trump.Value.TWO);

        assertThat(trump.getScore(), is(2));
    }
}