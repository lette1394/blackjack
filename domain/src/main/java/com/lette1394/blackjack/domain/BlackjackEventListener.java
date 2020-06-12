package com.lette1394.blackjack.domain;

import java.util.EventListener;

import com.lette1394.blackjack.domain.player.Player;
import com.lette1394.blackjack.domain.trump.Trumps;

// TODO: start, end 이런거 다 onGameStateChanged(); 같은걸로 변경
public interface BlackjackEventListener extends EventListener {

    void onGameStateChanged(BlackjackGameState snapshot);

    // TODO: player랑 dealer랑 구분없이 callback trigger
    void onPlayerHandChanged(Trumps trumps);

    void onDealerHandChanged(int showCards, Trumps trumps);

    void onPlayerTurnEnds(Trumps trumps);

    void onDealerTurnEnds(Trumps trumps);

    void onShowWinner(Player player, Trumps playerTrumps, Trumps dealerTrumps);

    // TODO: rename. illegal state transition?
    void onIllegalCommand(String message);



    void onNewPlayerJoin(Player player);
}
