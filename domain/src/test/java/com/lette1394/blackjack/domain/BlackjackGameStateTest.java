package com.lette1394.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BlackjackGameStateTest {

    @Test
    void newGame() {
        BlackjackGameState state = BlackjackGameState.waiting();
        assertThat(state, is(BlackjackGameState.waiting()));
    }

    @Test
    void cannotChangeNewGameToFinished() {
        BlackjackGameState state = BlackjackGameState.waiting();
        assertThrows(IllegalStateException.class, () -> state.finishing());
    }
}