package com.lette1394.blackjack.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BlackjackGameSnapshot {
    private final State state;

    public BlackjackGameSnapshot(State state) {
        this.state = state;
    }

    public static BlackjackGameSnapshot newGame() {
        return waiting();
    }

    public static BlackjackGameSnapshot waiting() {
        return new BlackjackGameSnapshot(State.WAITING);
    }

    public BlackjackGameSnapshot running() {
        return null;
    }


    public enum State {
        WAITING,
        RUNNING,
        FINISHED
    }
}
