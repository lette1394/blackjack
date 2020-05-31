package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class BlackjackEndToEndTest {
    private final ConsoleGameRunner runner = new ConsoleGameRunner();
    private final ConsoleGameTestRunner assertions = new ConsoleGameTestRunner();
    private final StandardInputOutputUI player = new StandardInputOutputUI(runner);

    @Test
    @Timeout(1)
    void APlayerLosesAfterStay() {
        runner.waitForPlayer();
        assertions.hasShownWaitForPlayer();

        player.join();
        assertions.hasReceivedPlayerJoinInput();
        assertions.hasShownGameIsStarted();

        assertions.hasShownDrawCardToPlayer();

        player.stay();
        assertions.hasReceivedPlayerStayInput();
        assertions.hasShownPlayerScore();

        assertions.hasShownDealerGotCards();
        assertions.hasShownDealerScore();

        assertions.hasShownWinner();

        runner.end();
        assertions.hasShownGameIsEnded();
    }
}
