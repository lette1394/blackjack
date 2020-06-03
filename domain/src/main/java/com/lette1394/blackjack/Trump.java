package com.lette1394.blackjack;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Trump {
    public final String suit;
    public final String value;

    private Suit suitt;
    private Value valuee;

    public Trump(final Suit heart, final Value two) {
        suitt = heart;
        valuee = two;
        suit = "";
        value = "";
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
}
