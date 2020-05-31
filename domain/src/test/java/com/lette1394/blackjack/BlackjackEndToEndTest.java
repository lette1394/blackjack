package com.lette1394.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class BlackjackEndToEndTest {
    private ConsoleGameRunner runner;
    private StandardInputOutputUI player;
    private final ConsoleGameRunnerAssertion assertion = new ConsoleGameRunnerAssertion();

    @BeforeEach
    void setUp() {
        assertion.mockStandardInputOutput();
        runner = new ConsoleGameRunner();
        player = new StandardInputOutputUI();
    }

    @Test
    @Timeout(1)
    void APlayerLosesAfterStay() {
        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer();

        player.stay();
        assertion.hasShownPlayerScore();

        assertion.hasShownDealerGotCards();
        assertion.hasShownDealerScore();

        assertion.hasShownWinner();

        assertion.hasShownGameIsEnded();
    }
}
