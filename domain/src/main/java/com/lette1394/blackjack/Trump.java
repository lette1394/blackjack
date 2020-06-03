package com.lette1394.blackjack;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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
        ACE(1),
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10);

        @Getter
        private final int score;
    }

    public int getScore() {
        return value.getScore();
    }
}
