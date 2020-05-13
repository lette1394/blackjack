package com.clean.todo.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Trump {
    private final Suit suit;
    private final Value value;

    public enum Suit {
        SPADE,
        HEART,
        DIAMOND,
        CLUB
    }

    public enum Value {
        ACE,
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        J, Q, K
    }
}
