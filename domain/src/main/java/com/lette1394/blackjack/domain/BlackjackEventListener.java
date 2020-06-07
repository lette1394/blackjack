package com.lette1394.blackjack.domain;

import java.util.EventListener;

import com.lette1394.blackjack.domain.trump.Trumps;

public interface BlackjackEventListener extends EventListener {
    void onStart();

    void onPlayerHandChanged(Trumps trumps);

    void onDealerHandChanged(int showCards, Trumps trumps);

    void onPlayerTurnEnds(Trumps trumps);

    void onDealerTurnEnds(Trumps trumps);

    void onShowWinner(Trumps playerTrumps, Trumps dealerTrumps);

    void onEnd();
}
