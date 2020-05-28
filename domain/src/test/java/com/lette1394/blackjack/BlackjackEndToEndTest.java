package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

public class BlackjackEndToEndTest {
    private final ConsoleGameRunner runner = new ConsoleGameRunner();
    private final StandardInputOutputUI player = new StandardInputOutputUI();

    @Test
    void APlayerJoinTheGame() {
        runner.waitForPlayer();
        runner.hasShownWaitForPlayer();

        player.joinTheGame();
        runner.hasReceivedPlayersJoinInput();

        runner.hasShownGameIsStarted();

        runner.hasShownGameIsEnded();
    }
}
