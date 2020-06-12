package com.lette1394.blackjack.domain;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

// TODO: 이거, snapshot은 모두 immutable로 하고
//  게임 상태를 변경하고 카드를 드로우하는 것은 별도의 클래스로 만들어야 하나?

@EqualsAndHashCode
@RequiredArgsConstructor
public class BlackjackGameState {
    private final State state;

    public static BlackjackGameState waiting() {
        return to(State.WAITING);
    }

    public BlackjackGameState betting() {
        checkTransitionTo(State.BETTING);
        return to(State.BETTING);
    }

    public BlackjackGameState drawing() {
        checkTransitionTo(State.DRAWING);
        return to(State.DRAWING);
    }

    public BlackjackGameState scoring() {
        checkTransitionTo(State.SCORING);
        return to(State.SCORING);
    }

    public BlackjackGameState finishing() {
        checkTransitionTo(State.FINISHING);
        return to(State.FINISHING);
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

    private static BlackjackGameState to(final State state) {
        return new BlackjackGameState(state);
    }

    private void checkTransitionTo(final State to) {
        final State from = state;
        if (from.canTransitTo(to) == false) {
            throw new IllegalStateException(String.format("cannot transit %s -> %s", from, to));
        }
    }

    private enum State {
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

