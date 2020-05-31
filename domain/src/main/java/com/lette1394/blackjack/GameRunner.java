package com.lette1394.blackjack;

public interface GameRunner {
    void waitForPlayer();

    void start();

    void end();

    void drawTrumps(Trump... trumps);

    void showPlayerScore();
}
