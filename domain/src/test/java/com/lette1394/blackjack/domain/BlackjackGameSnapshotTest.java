package com.lette1394.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameSnapshotTest {

    @Test
    void newGame() {
        BlackjackGameSnapshot snapshot = BlackjackGameSnapshot.newGame();
        assertThat(snapshot, is(BlackjackGameSnapshot.waiting()));
    }
}