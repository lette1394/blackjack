package com.lette1394.blackjack.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BlackjackGameSnapshot {
    private final State state;

    public BlackjackGameSnapshot(State state) {
        this.state = state;
    }

    public static BlackjackGameSnapshot waiting() {
        return new BlackjackGameSnapshot(State.WAITING);
    }

    public boolean isWaiting() {
        return State.WAITING.equals(state);
    }

    public BlackjackGameSnapshot running() {
        checkTransitionTo(State.RUNNING);
        return new BlackjackGameSnapshot(State.RUNNING);
    }

    public boolean isRunning() {
        return State.RUNNING.equals(state);
    }

    public BlackjackGameSnapshot finishing() {
        checkTransitionTo(State.FINISHING);
        return new BlackjackGameSnapshot(State.FINISHING);
    }

    public boolean isFinishing() {
        return State.FINISHING.equals(state);
    }

    public BlackjackGameSnapshot finished() {
        checkTransitionTo(State.FINISHED);
        return new BlackjackGameSnapshot(State.FINISHED);
    }

    public boolean isFinished() {
        return State.FINISHED.equals(state);
    }

    private void checkTransitionTo(final State to) {
        final State from = state;
        if (from.canTransitTo(to) == false) {
            throw new IllegalStateException(String.format("cannot transit %s -> %s", from, to));
        }
    }

    public enum State {
        WAITING {
            @Override
            public boolean canTransitTo(final State state) {
                return RUNNING.equals(state);
            }
        },
        RUNNING {
            @Override
            public boolean canTransitTo(final State state) {
                return FINISHING.equals(state);
            }
        },
        FINISHING {
            @Override
            public boolean canTransitTo(final State state) {
                return RUNNING.equals(state) || FINISHED.equals(state);
            }
        },
        FINISHED {
            @Override
            public boolean canTransitTo(final State state) {
                return false;
            }
        },
        ;

        public abstract boolean canTransitTo(State state);
    }
}
