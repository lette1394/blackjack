package com.lette1394.blackjack.domain;

import com.google.common.collect.Sets;
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

    public BlackjackGameSnapshot betting() {
        checkTransitionTo(State.BETTING);
        return new BlackjackGameSnapshot(State.BETTING);
    }

    public BlackjackGameSnapshot drawing() {
        checkTransitionTo(State.DRAWING);
        return new BlackjackGameSnapshot(State.DRAWING);
    }

    public BlackjackGameSnapshot scoring() {
        checkTransitionTo(State.SCORING);
        return new BlackjackGameSnapshot(State.SCORING);
    }

    public BlackjackGameSnapshot finishing() {
        checkTransitionTo(State.FINISHING);
        return new BlackjackGameSnapshot(State.FINISHING);
    }

    public boolean isWaiting() {
        return State.WAITING.equals(state);
    }

    public boolean isBetting() {
        return State.BETTING.equals(state);
    }

    public boolean isDrawing() {
        return State.DRAWING.equals(state);
    }

    public boolean isScoring() {
        return State.SCORING.equals(state);
    }

    public boolean isFinishing() {
        return State.FINISHING.equals(state);
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
                return BETTING.equals(state);
            }
        },

        BETTING {
            @Override
            public boolean canTransitTo(final State state) {
                return DRAWING.equals(state);
            }
        },

        DRAWING {
            @Override
            public boolean canTransitTo(final State state) {
                return Sets.newHashSet(DRAWING, SCORING).contains(state);
            }
        },

        SCORING {
            @Override
            public boolean canTransitTo(final State state) {
                return Sets.newHashSet(BETTING, FINISHING).contains(state);
            }
        },

        FINISHING {
            @Override
            public boolean canTransitTo(final State state) {
                return false;
            }
        },
        ;

        public abstract boolean canTransitTo(State state);
    }
}
