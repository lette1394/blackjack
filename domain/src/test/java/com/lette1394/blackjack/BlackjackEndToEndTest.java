package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class BlackjackEndToEndTest {
    private final ConsoleGameRunner runner = new ConsoleGameRunner();
    private final ConsoleGameTestRunner assertions = new ConsoleGameTestRunner();
    private final StandardInputOutputUI player = new StandardInputOutputUI();

    @Test
    void APlayerJoinTheGame() {
        runner.waitForPlayer();
        assertions.hasShownWaitForPlayer();

        player.join();
        assertions.hasReceivedPlayerJoinInput();

        runner.start();
        assertions.hasShownGameIsStarted();

        runner.end();
        assertions.hasShownGameIsEnded();
    }

    @Test
    @Timeout(1)
    void APlayerReceivedTwoTrumpsCardsThenStayAfterJoin() {
        runner.waitForPlayer();
        assertions.hasShownWaitForPlayer();

        player.join();
        assertions.hasReceivedPlayerJoinInput();

        runner.start();
        assertions.hasShownGameIsStarted();

        Trump[] trumps = new Trump[]{ new Trump("♦️", "2"), new Trump("♣️", "8") };
        runner.drawTrumps(trumps);
        assertions.hasShownCards("(♦️2) (♣️8)");

        player.stay();
        assertions.hasReceivedPlayerStayInput();

        runner.showPlayerScore();
        assertions.hasShownPlayerScore(2 + 8);

        runner.end();
        assertions.hasShownGameIsEnded();
    }
}
