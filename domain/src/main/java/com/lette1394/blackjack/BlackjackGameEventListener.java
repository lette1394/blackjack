package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackGameEventListener extends EventListener {
    void start();

    void playerHandChanged(Trumps trumps);

    void dealerHandChanged(int showCards, Trumps trumps);

    void playerTurnEnds(Trumps trumps);

    void dealerTurnEnds(Trumps trumps);

    void showWinner(Trumps playerTrumps, Trumps dealerTrumps);

    void end();
}
