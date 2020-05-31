package com.lette1394.blackjack;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import lombok.SneakyThrows;

public class BlackjackEndToEndTest {
    private ConsoleGameRunner runner;
    private StandardInputOutputUI player;
    private ConsoleGameRunnerAssertion assertion;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        PipedInputStream playerInput = new PipedInputStream();
        PipedOutputStream playerOutput = new PipedOutputStream(playerInput);

        PipedInputStream runnerInput = new PipedInputStream();
        PipedOutputStream runnerOutput = new PipedOutputStream(runnerInput);

        player = new StandardInputOutputUI(playerOutput);
        runner = new ConsoleGameRunner(playerInput, runnerOutput);

        assertion = new ConsoleGameRunnerAssertion(runnerInput);
    }

    @Test
    @Timeout(1)
    void APlayerLoseAfterStay() {
        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️2) (♣️8)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️9)");
        assertion.hasShownDealerScore(12);

        assertion.hasShownPlayerLose();

        assertion.hasShownGameIsEnded();
    }

    @Test
    @Timeout(1)
    void APlayerWinAfterStay() {
        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️5) (♣️5)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️1)");
        assertion.hasShownDealerScore(4);

        assertion.hasShownPlayerWin();

        assertion.hasShownGameIsEnded();
    }
}
