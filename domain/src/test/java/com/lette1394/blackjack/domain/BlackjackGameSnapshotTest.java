package com.lette1394.blackjack.domain;

import org.junit.jupiter.api.Test;

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
    void newGameToRunning() {
        BlackjackGameSnapshot snapshot = BlackjackGameSnapshot.waiting();
        snapshot = snapshot.running();

        assertThat(snapshot, is(new BlackjackGameSnapshot(BlackjackGameSnapshot.State.RUNNING)));
    }

    @Test
    void cannotChangeNewGameToFinished() {
        BlackjackGameSnapshot snapshot = BlackjackGameSnapshot.waiting();
        assertThrows(IllegalStateException.class, () -> snapshot.finished());
    }
}