package com.lette1394.blackjack.domain;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.domain.player.Player;
import com.lette1394.blackjack.domain.trump.Trump;
import com.lette1394.blackjack.domain.trump.Trumps;

// TODO: 이거, snapshot은 모두 immutable로 하고
//  게임 상태를 변경하고 카드를 드로우하는 것은 별도의 클래스로 만들어야 하나?

@RequiredArgsConstructor
@EqualsAndHashCode
public class BlackjackGameState {
    private final State state;
    private final Map<Player, Trumps> playerTrumpsMap;

    public static BlackjackGameState waiting() {
        return new BlackjackGameState(State.WAITING, new HashMap<>());
    }

    public BlackjackGameState betting() {
        checkTransitionTo(State.BETTING);
        return new BlackjackGameState(State.BETTING, new HashMap<>(playerTrumpsMap));
    }

    public BlackjackGameState drawing() {
        checkTransitionTo(State.DRAWING);
        return new BlackjackGameState(State.DRAWING, new HashMap<>(playerTrumpsMap));
    }

    public BlackjackGameState scoring() {
        checkTransitionTo(State.SCORING);
        return new BlackjackGameState(State.SCORING, new HashMap<>(playerTrumpsMap));
    }

    public BlackjackGameState finishing() {
        checkTransitionTo(State.FINISHING);
        return new BlackjackGameState(State.FINISHING, new HashMap<>(playerTrumpsMap));
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
        if (playerTrumpsMap.containsKey(player) == false) {
            throw new IllegalArgumentException();
        }
        return playerTrumpsMap.get(player)
                              .computeScore();
    }

    public void addPlayer(final Player player) {
        playerTrumpsMap.put(player, new Trumps());
    }

    public void addDraw(final Player player, final Trump trump) {
        playerTrumpsMap.get(player)
                       .add(trump);
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
