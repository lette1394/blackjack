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

    void onShowWinner(Player player, GameWinner winner);

    // TODO: rename. illegal state transition?
    //  이거 에러 코드를 정해서 처리할 수 있나?
    //  아니면 exception type을 정해서 핸들링?
    void onIllegalCommand(String message);



    void onNewPlayerJoin(Player player);

    void onPlayerBetting(Player player);
}
