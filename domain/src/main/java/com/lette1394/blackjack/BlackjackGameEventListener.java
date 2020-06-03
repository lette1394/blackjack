package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackGameEventListener extends EventListener {
    void start();

    void playerHandsChanged(final int numberOfCards, final Trumps trumps);

    void dealerHandsChanged(final int numberOfCards, final Trumps trumps);

    void playerTurnEnds(final Trumps trumps);

    void dealerTurnEnds(final Trumps trumps);

    void showWinner(final Trumps playerTrumps, final Trumps dealerTrumps);

    void end();
}
