package com.lette1394.blackjack;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayDeque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import com.lette1394.blackjack.domain.BlackjackGame;
import com.lette1394.blackjack.domain.player.InMemoryPlayerRepository;
import com.lette1394.blackjack.domain.trump.Trump;
import com.lette1394.blackjack.domain.trump.TrumpProvider;
import com.lette1394.blackjack.io.ConsoleInputProcessor;
import com.lette1394.blackjack.io.ConsoleOutput;
import com.lette1394.blackjack.io.ConsoleInvalidCommandListener;
import com.lette1394.blackjack.runner.BlackjackGameRunner;
import com.lette1394.blackjack.io.ConsoleInputOutput;
import com.lette1394.blackjack.io.ConsoleHelloMessageInputOutputAdapter;
import com.lette1394.blackjack.io.InputOutput;
import com.lette1394.blackjack.testutil.ConsoleFakeInputOutput;

import static com.lette1394.blackjack.domain.trump.TrumpFactory.trump;

@Timeout(1)
public class ConsoleBlackjackEndToEndTest {
    private BlackjackGameRunner runner;
    private ConsoleFakeInputOutput player;
    private ConsoleGameRunnerAssertion assertion;
    private ConsoleInputProcessor consoleInputTranslator;
    private InputOutput inputOutput;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        PipedInputStream fakeInput = new PipedInputStream();
        PipedOutputStream fakeOutput = new PipedOutputStream(fakeInput);
        PipedInputStream runnerInput = new PipedInputStream();
        PipedOutputStream runnerOutput = new PipedOutputStream(runnerInput);

        inputOutput = new ConsoleHelloMessageInputOutputAdapter("wait for player...",
                                                                new ConsoleInputOutput(fakeInput,
                                                                                runnerOutput));
        consoleInputTranslator = new ConsoleInputProcessor(new InMemoryPlayerRepository());
        player = new ConsoleFakeInputOutput(fakeOutput);
        runner = new BlackjackGameRunner(inputOutput, consoleInputTranslator, 0);

        assertion = new ConsoleGameRunnerAssertion(runnerInput);
    }

    @Test
    void aPlayerLoseAfterStay() {
        readyForNewGame(12, nextTrumps(trump("♦️", "2"), trump("♣️", "8"),
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
    void aPlayerWinAfterStay() {
        readyForNewGame(5, nextTrumps(trump("♦️", "5"), trump("♣️", "5"),
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
    void aPlayerLosesAfterHit() {
        readyForNewGame(20, nextTrumps(trump("♦️", "5"), trump("♣️", "5"),
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
    void aPlayerWinUsingAce() {
        readyForNewGame(11, nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
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

    @Test
    void aPlayerHitTwiceThenWin() {
        readyForNewGame(11, nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
                                       trump("♥️", "5"), trump("♠️", "6"),
                                       trump("♣️", "9"), trump("♣️", "2")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️5) (??)");

        player.hit();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8) (♣️9)");

        player.hit();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8) (♣️9) (♣️2)");

        player.stay();
        assertion.hasShownPlayerScore(20);

        assertion.hasShownDealerGotCards("(♥️5) (♠️6)");
        assertion.hasShownDealerScore(11);

        assertion.hasShownPlayerWin();

        assertion.hasShownGameIsEnded();
    }

    @Test
    void dealerHitTwiceThenWin() {
        readyForNewGame(20, nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
                                       trump("♥️", "5"), trump("♠️", "6"),
                                       trump("♠️", "8"), trump("♠️", "Ace")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️5) (??)");

        player.stay();
        assertion.hasShownPlayerScore(19);

        assertion.hasShownDealerGotCards("(♥️5) (♠️6)");
        assertion.hasShownDealerGotCards("(♥️5) (♠️6) (♠️8)");
        assertion.hasShownDealerGotCards("(♥️5) (♠️6) (♠️8) (♠️A)");
        assertion.hasShownDealerScore(20);

        assertion.hasShownPlayerLose();
        assertion.hasShownGameIsEnded();
    }

    @Test
    void aPlayerInputWrongCommand() {
        readyForNewGame(20, nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
                                       trump("♥️", "5"), trump("♠️", "6"),
                                       trump("♠️", "8"), trump("♠️", "Ace")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.send("some invalid command");
        assertion.hasShownInputIsInvalidAndHelpMessages();
    }

    private void readyForNewGame(final int dealerStopScore, final TrumpProvider trumpProvider) {
        final BlackjackGame blackjackGame = new BlackjackGame(dealerStopScore, trumpProvider);
        blackjackGame.addListener(new ConsoleOutput(inputOutput));
        consoleInputTranslator.addListener(blackjackGame);
        consoleInputTranslator.addListener(new ConsoleInvalidCommandListener(inputOutput));
    }

    private TrumpProvider nextTrumps(Trump... trumps) {
        return new ArrayDeque<>(Lists.newArrayList(trumps))::remove;
    }
}
