package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import static com.lette1394.blackjack.TrumpFactory.trump;
import static com.lette1394.blackjack.TrumpFactory.trumps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BlackjackTrumpsScoreCalculatorTest {
    private final TrumpsTranslator calculator = new BlackjackTrumpsScoreCalculator();

    @Test
    void scoreSingleTrump() {
        final int score = calculator.translate(trumps(trump("♥️", "3")));
        assertThat(score, is(3));
    }

    @Test
    void scoreTwoTrump() {
        final int score = calculator.translate(trumps(trump("♦️", "3"), trump("♣️", "5")));
        assertThat(score, is(8));
    }
}