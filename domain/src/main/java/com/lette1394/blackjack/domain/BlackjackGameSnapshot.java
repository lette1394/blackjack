package com.lette1394.blackjack.domain;

public class BlackjackGameSnapshot {
    public static BlackjackGameSnapshot newGame() {
        throw new UnsupportedOperationException();
    }

    public static Object waiting() {
        throw new UnsupportedOperationException();
    }

    private enum BlackjackGameState {
        READY,
        RUNNING,
        FINISHED,
        ;

        public static BlackjackGameState newState() {

            return null;
        }
    }
}
