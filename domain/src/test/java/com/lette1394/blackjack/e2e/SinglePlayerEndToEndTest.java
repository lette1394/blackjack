package com.lette1394.blackjack.e2e;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayDeque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import com.lette1394.blackjack.ConsoleGameRunnerAssertion;
import com.lette1394.blackjack.domain.BlackjackGame;
import com.lette1394.blackjack.domain.player.InMemoryPlayerRepository;
import com.lette1394.blackjack.domain.trump.Trump;
import com.lette1394.blackjack.domain.trump.TrumpProvider;
import com.lette1394.blackjack.io.ConsoleHelloMessageInputOutputAdapter;
import com.lette1394.blackjack.io.ConsoleInputOutput;
import com.lette1394.blackjack.io.ConsoleInputProcessor;
import com.lette1394.blackjack.io.ConsoleInvalidCommandListener;
import com.lette1394.blackjack.io.ConsoleOutput;
import com.lette1394.blackjack.io.InputOutput;
import com.lette1394.blackjack.runner.BlackjackGameRunner;
import com.lette1394.blackjack.testutil.ConsoleFakeInputOutput;

import static com.lette1394.blackjack.domain.trump.TrumpFactory.trump;

@Timeout(1)
public class SinglePlayerEndToEndTest {
    private static final String playerId = "1234";

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
        player = new ConsoleFakeInputOutput(fakeOutput, playerId);
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
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️2) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️9)");
        assertion.hasShownDealerScore(12);

        assertion.hasShownPlayerLose();
        assertion.hasShownPlayerRemainingCoins(900);

        assertion.hasShownTryItAgain();
        player.leave();
        assertion.hasShownGameIsEnded();
    }

    @Test
    void aPlayerWinAfterStay() {
        readyForNewGame(5, nextTrumps(trump("♦️", "5"), trump("♣️", "5"),
                                      trump("♥️", "3"), trump("♠️", "2")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️5) (♣️5)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️2)");
        assertion.hasShownDealerScore(5);

        assertion.hasShownPlayerWin();
        assertion.hasShownPlayerRemainingCoins(1100);

        assertion.hasShownTryItAgain();
        player.leave();
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
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
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
        assertion.hasShownPlayerRemainingCoins(900);

        assertion.hasShownTryItAgain();
        player.leave();
        assertion.hasShownGameIsEnded();
    }

    @Test
    void aPlayerWinUsingAce() {
        readyForNewGame(11, nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
                                       trump("♥️", "5"), trump("♠️", "6")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️5) (??)");

        player.stay();
        assertion.hasShownPlayerScore(19);

        assertion.hasShownDealerGotCards("(♥️5) (♠️6)");
        assertion.hasShownDealerScore(11);

        assertion.hasShownPlayerWin();
        assertion.hasShownPlayerRemainingCoins(1100);

        assertion.hasShownTryItAgain();
        player.leave();
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
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
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
        assertion.hasShownPlayerRemainingCoins(1100);

        assertion.hasShownTryItAgain();
        player.leave();
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
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
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
        assertion.hasShownPlayerRemainingCoins(900);

        assertion.hasShownTryItAgain();
        player.leave();
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
        assertion.hasShownInputIsInvalidAndHelpMessages("wrong input: some invalid command");
    }

    @Test
    void aPlayerLosesAfterBusts() {
        readyForNewGame(20, nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
                                       trump("♥️", "5"), trump("♠️", "6"),
                                       trump("♣️", "9"), trump("♣️", "2"), trump("♦️", "2")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️5) (??)");

        player.hit();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8) (♣️9)");
        player.hit();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8) (♣️9) (♣️2)");
        player.hit();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8) (♣️9) (♣️2) (♦️2)");

        assertion.hasShownPlayerGotBust(22);

        assertion.hasShownPlayerLose();
        assertion.hasShownPlayerRemainingCoins(900);

        assertion.hasShownTryItAgain();
        player.leave();
        assertion.hasShownGameIsEnded();
    }

    @Test
    void aPlayerWinAfterDealerGotBusts() {
        readyForNewGame(16, nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
                                       trump("♥️", "5"), trump("♠️", "6"),
                                       trump("♣️", "9"),
                                       trump("♠️", "2"), trump("♠️", "9")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️5) (??)");

        player.hit();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8) (♣️9)");

        player.stay();
        assertion.hasShownPlayerScore(18);

        assertion.hasShownDealerGotCards("(♥️5) (♠️6)");
        assertion.hasShownDealerGotCards("(♥️5) (♠️6) (♠️2)");
        assertion.hasShownDealerGotCards("(♥️5) (♠️6) (♠️2) (♠️9)");
        assertion.hasShownDealerGotBust(22);

        assertion.hasShownPlayerWin();
        assertion.hasShownPlayerRemainingCoins(1100);

        assertion.hasShownTryItAgain();
        player.leave();
        assertion.hasShownGameIsEnded();
    }

    @Test
    void aPlayerSendCommandInWrongState() {
        readyForNewGame(-1, null);

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.hit();
        assertion.hasShownInputIsInvalidAndHelpMessages("wrong input: hit. You can type 'join'");
        player.stay();
        assertion.hasShownInputIsInvalidAndHelpMessages("wrong input: stay. You can type 'join'");

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
    }

    @Test
    void aPlayerAndDealerInDraw() {
        readyForNewGame(15, nextTrumps(trump("♦️", "7"), trump("♣️", "8"),
                                       trump("♥️", "8"), trump("♠️", "7")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️7) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️8) (??)");

        player.stay();
        assertion.hasShownPlayerScore(15);
        assertion.hasShownDealerGotCards("(♥️8) (♠️7)");
        assertion.hasShownDealerScore(15);

        assertion.hasShownPlayerDraw();
        assertion.hasShownPlayerRemainingCoins(1000);

        assertion.hasShownTryItAgain();
        player.leave();
        assertion.hasShownGameIsEnded();
    }

    @Test
    void aPlayerSendJoinTwice() {
        readyForNewGame(16, nextTrumps(trump("♦️", "Ace"), trump("♣️", "8"),
                                       trump("♥️", "5"), trump("♠️", "6"),
                                       trump("♣️", "9"),
                                       trump("♠️", "2"), trump("♠️", "9")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️A) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️5) (??)");

        player.join();
        assertion.hasShownInputIsInvalidAndHelpMessages(
                "wrong input: join. game already started. you can type 'hit' or 'stay'");
    }

    @Test
    void aPlayerCanPlayTwoGameContinuously() {
        readyForNewGame(12, nextTrumps(trump("♦️", "2"), trump("♣️", "8"),
                                       trump("♥️", "3"), trump("♠️", "9"),

                                       trump("♦️", "2"), trump("♣️", "8"),
                                       trump("♥️", "3"), trump("♠️", "9")));


        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(100);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️2) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️9)");
        assertion.hasShownDealerScore(12);

        assertion.hasShownPlayerLose();
        assertion.hasShownPlayerRemainingCoins(900);

        assertion.hasShownTryItAgain();

        player.rejoin();
        assertion.hasShownPlayerRemainingCoins(900);

        player.bet(200);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️2) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️9)");
        assertion.hasShownDealerScore(12);

        assertion.hasShownPlayerLose();
        assertion.hasShownPlayerRemainingCoins(700);

        assertion.hasShownTryItAgain();
        player.leave();
        assertion.hasShownGameIsEnded();
    }

    @Test
    void aPlayerGotBankrupt() {
        readyForNewGame(12, nextTrumps(trump("♦️", "2"), trump("♣️", "8"),
                                       trump("♥️", "3"), trump("♠️", "9"),

                                       trump("♦️", "2"), trump("♣️", "8"),
                                       trump("♥️", "3"), trump("♠️", "9")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(600);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️2) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️9)");
        assertion.hasShownDealerScore(12);

        assertion.hasShownPlayerLose();
        assertion.hasShownPlayerRemainingCoins(400);

        assertion.hasShownTryItAgain();

        player.rejoin();
        assertion.hasShownPlayerRemainingCoins(400);

        player.bet(400);
        assertion.hasShownGameIsStarted();
        assertion.hasShownDrawCardToPlayer("(♦️2) (♣️8)");
        assertion.hasShownDealerGotCards("(♥️3) (??)");

        player.stay();
        assertion.hasShownPlayerScore(10);

        assertion.hasShownDealerGotCards("(♥️3) (♠️9)");
        assertion.hasShownDealerScore(12);

        assertion.hasShownPlayerLose();
        assertion.hasShownPlayerRemainingCoins(0);

        assertion.hasShownPlayerGotBankrupt();
        assertion.hasShownGameIsEnded();
    }

    @Test
    void aPlayerCannotBetMoneyOver() {
        readyForNewGame(12, nextTrumps(trump("♦️", "2"), trump("♣️", "8"),
                                       trump("♥️", "3"), trump("♠️", "9")));

        runner.run();
        assertion.hasShownWaitForPlayer();

        player.join();
        assertion.hasShownPlayerJoin(playerId);
        assertion.hasShownPlayerRemainingCoins(1000);

        player.bet(1001);
        assertion.hasShownCannotBetMoneyOver(1000);

        player.bet(1000);
        assertion.hasShownGameIsStarted();
    }

    private BlackjackGame readyForNewGame(final int dealerStopScore, final TrumpProvider trumpProvider) {
        final BlackjackGame blackjackGame = new BlackjackGame(dealerStopScore, trumpProvider);
        blackjackGame.addListener(new ConsoleOutput(inputOutput));
        consoleInputTranslator.addListener(blackjackGame);
        consoleInputTranslator.addListener(new ConsoleInvalidCommandListener(inputOutput));

        return blackjackGame;
    }

    private TrumpProvider nextTrumps(Trump... trumps) {
        return new ArrayDeque<>(Lists.newArrayList(trumps))::remove;
    }
}
