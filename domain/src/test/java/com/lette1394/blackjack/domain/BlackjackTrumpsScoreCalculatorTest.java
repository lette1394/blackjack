package com.lette1394.blackjack.domain;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static com.lette1394.blackjack.domain.TrumpFactory.trump;
import static com.lette1394.blackjack.domain.TrumpFactory.trumps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BlackjackTrumpsScoreCalculatorTest {
    private final TrumpsTranslator<Integer> calculator = new BlackjackTrumpsScoreCalculator();

    @Test
    void singleTrump() {
        assertThat(computeScore("3"), is(3));
    }

    @Test
    void twoTrump() {
        assertThat(computeScore("3", "5"), is(8));
    }

    @Test
    void ace() {
        assertThat(computeScore("ace",
                                "ace",
                                "ace",
                                "ace"), is(14));

        assertThat(computeScore("ace", "ace", "J"), is(12));
    }

    @Test
    void blackJack() {
        assertThat(computeScore("ace", "J"), is(21));
        assertThat(computeScore("ace", "10"), is(21));

        assertThat(computeScore("ace",
                                "5",
                                "2",
                                "3"), is(21));

        assertThat(computeScore("10",
                                "ace",
                                "K"), is(21));
    }

    @Test
    void busts() {
        assertThat(computeScore("J", "Q", "K"), is(30));

        assertThat(computeScore("ace",
                                "5",
                                "Q",
                                "K"), is(26));
    }

    private static final String ANY_TRUMP_SUIT = "♦️";
    private int computeScore(final String... trumpsValue) {
        return calculator.translate(trumps(Arrays.stream(trumpsValue)
                                                 .map(value -> trump(ANY_TRUMP_SUIT, value))
                                                 .collect(Collectors.toList())));
    }
}