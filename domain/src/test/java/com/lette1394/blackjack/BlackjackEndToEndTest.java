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
