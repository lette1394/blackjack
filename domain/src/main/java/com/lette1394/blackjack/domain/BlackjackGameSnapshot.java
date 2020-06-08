package com.lette1394.blackjack.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BlackjackGameSnapshot {
    private final State state;

    private BlackjackGameSnapshot(State state) {
        this.state = state;
    }

    public static BlackjackGameSnapshot newGame() {
        return waiting();
    }

    public static BlackjackGameSnapshot waiting() {
        return new BlackjackGameSnapshot(State.WAITING);
    }



    private enum State {
        WAITING,
        RUNNING,
        FINISHED
    }
}
