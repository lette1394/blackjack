package com.lette1394.blackjack;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Trump {
    public final String suit;
    public final String value;

    public Trump(final Suit heart, final Value two) {
        throw new UnsupportedOperationException();
    }

    public enum Suit {
        HEART,
        SPADE,
        DIAMOND,
        CLUB
    }

    public enum Value {
        ACE,
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        JACK, QUEEN, KING
    }

    public int getScore() {
        throw new UnsupportedOperationException();
    }
}
