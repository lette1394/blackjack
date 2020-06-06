package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import com.lette1394.blackjack.domain.Trump;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TrumpTest {

    @Test
    public void trumpEquality() {
        final Trump trump1 = new Trump(Trump.Suit.HEART, Trump.Value.TWO);
        final Trump trump2 = new Trump(Trump.Suit.HEART, Trump.Value.TWO);

        assertThat(trump1, is(trump2));
    }
}