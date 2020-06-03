package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackGameEventListener extends EventListener {
    void start();

    void playerHandsChanged(final int numberOfCards, final Trumps trumps);

    void dealerHandsChanged(final int numberOfCards, final Trumps trumps);

    void showPlayerScore(final int score);

    void showDealerScore(final int score);

    void showWinner(final int playerScore, final int dealerScore);

    void end();
}
