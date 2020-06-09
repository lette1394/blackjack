package com.lette1394.blackjack.domain;

import org.junit.jupiter.api.Test;

import com.lette1394.blackjack.domain.player.Player;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameSnapshotTest {

    @Test
    void newGame() {
        BlackjackGameSnapshot snapshot = BlackjackGameSnapshot.waiting();
        assertThat(snapshot, is(BlackjackGameSnapshot.waiting()));
    }

    @Test
    void cannotChangeNewGameToFinished() {
        BlackjackGameSnapshot snapshot = BlackjackGameSnapshot.waiting();
        assertThrows(IllegalStateException.class, () -> snapshot.finishing());
    }

    @Test
    void score() {
        Player player = new Player();
        BlackjackGameSnapshot snapshot = BlackjackGameSnapshot.waiting();
        assertThat(snapshot.getScore(player), is(10));
    }
}