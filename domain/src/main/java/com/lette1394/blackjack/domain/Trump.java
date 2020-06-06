package com.lette1394.blackjack.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Trump {
    public final Suit suit;
    public final Value value;

    public enum Suit {
        HEART,
        SPADE,
        DIAMOND,
        CLUB
    }

    @RequiredArgsConstructor
    public enum Value {
        ACE,
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        JACK, QUEEN, KING;
    }
}
