package com.lette1394.blackjack;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayDeque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;

import com.lette1394.blackjack.ui.ConsolePlayerInputGameOutput;
import com.lette1394.blackjack.ui.PlayerInputGameOutput;

public class BlackjackEndToEndTest {
    private BlackjackGameRunner runner;
    private FakePlayerPlayerInputGameOutput player;
    private ConsoleGameRunnerAssertion assertion;
    private BlackjackPlayerInputTranslator blackjackPlayerInputTranslator;
    private PlayerInputGameOutput playerInputGameOutput;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        PipedInputStream fakeInput = new PipedInputStream();
        PipedOutputStream fakeOutput = new PipedOutputStream(fakeInput);
        PipedInputStream runnerInput = new PipedInputStream();
        PipedOutputStream runnerOutput = new PipedOutputStream(runnerInput);

        playerInputGameOutput = new ConsolePlayerInputGameOutput(fakeInput, runnerOutput);
        blackjackPlayerInputTranslator = new BlackjackPlayerInputTranslator();
        player = new FakePlayerPlayerInputGameOutput(fakeOutput);
        runner = new BlackjackGameRunner(playerInputGameOutput, blackjackPlayerInputTranslator);

        assertion = new ConsoleGameRunnerAssertion(runnerInput);
    }

    @Test
    @Timeout(1)
    void APlayerLoseAfterStay() {
        final CardProvider cardProvider = cardProvider(new Trump("♦️", "2"), new Trump("♣️", "8"),
                                                       new Trump("♥️", "3"), new Trump("♠️", "9"));
        blackjackPlayerInputTranslator.addListener(new BlackjackGame(cardProvider, playerInputGameOutput));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️2) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

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
        final CardProvider cardProvider = cardProvider(new Trump("♦️", "5"), new Trump("♣️", "5"),
                                                       new Trump("♥️", "3"), new Trump("♠️", "1"));
        blackjackPlayerInputTranslator.addListener(new BlackjackGame(cardProvider, playerInputGameOutput));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️5) (♣️5)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️1)");
        assertion.hasShownDealerScore(4);

        assertion.hasShownPlayerWin();

        assertion.hasShownGameIsEnded();
    }

    @Test
    @Timeout(1)
    void APlayerLosesAfterHit() {
        final CardProvider cardProvider = cardProvider(new Trump("♦️", "5"), new Trump("♣️", "5"),
                                                       new Trump("♥️", "10"), new Trump("♠️", "10"),
                                                       new Trump("♣️", "8"));
        blackjackPlayerInputTranslator.addListener(new BlackjackGame(cardProvider, playerInputGameOutput));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️5) (♣️5)");
        assertion.hasShownDealerGotCards("(♥️10) (??)");

        player.hit();
        assertion.hasShownDrawCardToPlayer("(♦️5) (♣️5) (♣️8)");

        player.stay();
        assertion.hasShownPlayerScore(18);

        assertion.hasShownDealerGotCards("(♥️10) (♠️10)");
        assertion.hasShownDealerScore(20);

        assertion.hasShownPlayerLose();

        assertion.hasShownGameIsEnded();
    }

    private CardProvider cardProvider(Trump... trumps) {
        return new ArrayDeque<>(Lists.list(trumps))::poll;
    }
}
