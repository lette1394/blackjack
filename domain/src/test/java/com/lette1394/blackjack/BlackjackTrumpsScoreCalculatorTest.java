package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import static com.lette1394.blackjack.TrumpFactory.trump;
import static com.lette1394.blackjack.TrumpFactory.trumps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BlackjackTrumpsScoreCalculatorTest {
    private final TrumpsTranslator calculator = new BlackjackTrumpsScoreCalculator();

    @Test
    void singleTrump() {
        assertThat(computeScore(trump("♥️", "3")),
                   is(3));
    }

    @Test
    void twoTrump() {
        assertThat(computeScore(trump("♦️", "3"), trump("♣️", "5")),
                   is(8));
    }

    @Test
    void ace() {
        assertThat(computeScore(trump("♦️", "ace"),
                                trump("♥️", "ace"),
                                trump("♠️", "ace"),
                                trump("♣️", "ace")), is(14));

        assertThat(computeScore(trump("♦️", "ace"),
                                trump("♣️", "ace"),
                                trump("♣️", "J")), is(12));
    }

    @Test
    void blackJack() {
        assertThat(computeScore(trump("♦️", "ace"),
                                trump("♣️", "J")), is(21));

        assertThat(computeScore(trump("♦️", "ace"),
                                trump("♣️", "10")), is(21));

        assertThat(computeScore(trump("♦️", "ace"),
                                trump("♦️", "5"),
                                trump("♦️", "2"),
                                trump("♣️", "3")), is(21));

        assertThat(computeScore(trump("♦️", "10"),
                                trump("♣️", "ace"),
                                trump("♣️", "K")), is(21));
    }

    @Test
    void busts() {
        assertThat(computeScore(trump("♦️", "J"),
                                trump("♣️", "Q"),
                                trump("♣️", "K")), is(30));

        assertThat(computeScore(trump("♦️", "ace"),
                                trump("♣️", "5"),
                                trump("♣️", "Q"),
                                trump("♣️", "K")), is(26));
    }

    private int computeScore(final Trump... trumps) {
        return calculator.translate(trumps(trumps));
    }
}