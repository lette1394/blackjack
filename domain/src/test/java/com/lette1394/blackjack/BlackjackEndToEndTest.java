package com.lette1394.blackjack;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayDeque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;

import com.lette1394.blackjack.ui.ConsoleUserInterface;
import com.lette1394.blackjack.ui.UserInterface;

public class BlackjackEndToEndTest {
    private ConsoleGameRunner runner;
    private FakePlayerUserInterface player;
    private ConsoleGameRunnerAssertion assertion;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        PipedInputStream fakeInput = new PipedInputStream();
        PipedOutputStream fakeOutput = new PipedOutputStream(fakeInput);

        PipedInputStream runnerInput = new PipedInputStream();
        PipedOutputStream runnerOutput = new PipedOutputStream(runnerInput);

        PlayerInputTranslator playerInputTranslator = new PlayerInputTranslator();
        UserInterface userInterface = new ConsoleUserInterface(fakeInput, runnerOutput);

        player = new FakePlayerUserInterface(fakeOutput);
        runner = new ConsoleGameRunner(userInterface, playerInputTranslator);

        assertion = new ConsoleGameRunnerAssertion(runnerInput);
    }

    @Test
    @Timeout(1)
    void APlayerLoseAfterStay() {
        runner.setCardProvider(cardProvider(new Trump("♦️", "2"), new Trump("♣️", "8"),
                                            new Trump("♥️", "3"), new Trump("♠️", "9")));
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
        runner.setCardProvider(cardProvider(new Trump("♦️", "5"), new Trump("♣️", "5"),
                                            new Trump("♥️", "3"), new Trump("♠️", "1")));
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
        runner.setCardProvider(cardProvider(new Trump("♦️", "5"), new Trump("♣️", "5"),
                                            new Trump("♥️", "10"), new Trump("♠️", "10"),
                                            new Trump("♣️", "8")));
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
