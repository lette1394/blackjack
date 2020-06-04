package com.lette1394.blackjack;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayDeque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import com.lette1394.blackjack.ui.ConsolePlayerInputGameOutput;
import com.lette1394.blackjack.ui.PlayerInputGameOutput;

import static com.lette1394.blackjack.TrumpFactory.trump;

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
        readyForNewGame(nextTrumps(trump("♦️", "2"), trump("♣️", "8"),
                                   trump("♥️", "3"), trump("♠️", "9")));

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
        readyForNewGame(nextTrumps(trump("♦️", "5"), trump("♣️", "5"),
                                   trump("♥️", "3"), trump("♠️", "2")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️5) (♣️5)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️2)");
        assertion.hasShownDealerScore(5);

        assertion.hasShownPlayerWin();

        assertion.hasShownGameIsEnded();
    }

    @Test
    @Timeout(1)
    void APlayerLosesAfterHit() {
        readyForNewGame(nextTrumps(trump("♦️", "5"), trump("♣️", "5"),
                                   trump("♥️", "10"), trump("♠️", "10"),
                                   trump("♣️", "8")));

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

    @Test
    @Timeout(1)
    void APlayerWinUsingAce() {
        readyForNewGame(nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
                                   trump("♥️", "5"), trump("♠️", "6")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️5) (??)");

        player.stay();
        assertion.hasShownPlayerScore(19);

        assertion.hasShownDealerGotCards("(♥️5) (♠️6)");
        assertion.hasShownDealerScore(11);

        assertion.hasShownPlayerWin();

        assertion.hasShownGameIsEnded();
    }

    private void readyForNewGame(final CardProvider cardProvider) {
        final BlackjackGame blackjackGame = new BlackjackGame(cardProvider);
        blackjackGame.addListener(new ConsoleBlackjackGame(playerInputGameOutput));
        blackjackPlayerInputTranslator.addListener(blackjackGame);
    }

    private CardProvider nextTrumps(Trump... trumps) {
        return new ArrayDeque<>(Lists.newArrayList(trumps))::remove;
    }
}
