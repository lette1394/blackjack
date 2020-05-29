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

        player.joinTheGame();
        runner.hasReceivedPlayerJoinInput();

        runner.startGame();
        runner.hasShownGameIsStarted();

        runner.endGame();
        runner.hasShownGameIsEnded();
    }

    @Test
    @Timeout(1)
    void APlayerReceivedTwoTrumpsCardsAfterJoin() {
        runner.waitForPlayer();
        runner.hasShownWaitForPlayer();

        player.joinTheGame();
        runner.hasReceivedPlayerJoinInput();

        runner.startGame();
        runner.hasShownGameIsStarted();

        runner.drawCardsToPlayer();
        runner.hasShownCards();

        runner.endGame();
        runner.hasShownGameIsEnded();
    }
}
