package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class BlackjackEndToEndTest {
    private final ConsoleGameRunner runner = new ConsoleGameRunner();
    private final StandardInputOutputUI player = new StandardInputOutputUI();

    @Test
    void APlayerJoinTheGame() {
        runner.waitForPlayer();
        runner.hasShownWaitForPlayer();

        player.join();
        runner.hasReceivedPlayerJoinInput();

        runner.start();
        runner.hasShownGameIsStarted();

        runner.end();
        runner.hasShownGameIsEnded();
    }

    @Test
    @Timeout(1)
    void APlayerReceivedTwoTrumpsCardsThenStayAfterJoin() {
        runner.waitForPlayer();
        runner.hasShownWaitForPlayer();

        player.join();
        runner.hasReceivedPlayerJoinInput();

        runner.start();
        runner.hasShownGameIsStarted();

        runner.drawTrumps("(♦️2) (♣️8)");
        runner.hasShownCards();

        player.stay();
        runner.hasReceivedPlayerStayInput();

        runner.showPlayerScore();
        runner.hasShownPlayerScore();

        runner.end();
        runner.hasShownGameIsEnded();
    }
}
