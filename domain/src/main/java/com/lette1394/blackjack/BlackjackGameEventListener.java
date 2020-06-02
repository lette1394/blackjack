package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackGameEventListener extends EventListener {
    void start();

    void drawToPlayer(final int numberOfCards);

    void drawToDealer(final int numberOfCards);

    void showPlayerScore();

    void showDealerScore();

    void showWinner();

    void end();
}
