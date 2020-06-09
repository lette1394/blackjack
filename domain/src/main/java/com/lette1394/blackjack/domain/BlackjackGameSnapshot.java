package com.lette1394.blackjack.domain;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;

import com.lette1394.blackjack.domain.player.Player;

// TODO: 이거, snapshot은 모두 immutable로 하고
//  게임 상태를 변경하고 카드를 드로우하는 것은 별도의 클래스로 만들어야 하나?
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

    public int getScore(Player player) {
        throw new UnsupportedOperationException();
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
