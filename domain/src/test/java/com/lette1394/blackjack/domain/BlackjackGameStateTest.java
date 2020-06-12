package com.lette1394.blackjack.domain;

import org.junit.jupiter.api.Test;

import com.lette1394.blackjack.domain.player.Player;

import static com.lette1394.blackjack.domain.trump.TrumpFactory.trump;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void score() {
        Player player = new Player("jaeeun");
        BlackjackGameState state = BlackjackGameState.waiting();

        state.addPlayer(player);
        state.addDraw(player, trump("spade", "8"));


        assertThat(state.getScore(player), is(8));
    }
}