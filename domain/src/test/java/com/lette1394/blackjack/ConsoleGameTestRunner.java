package com.lette1394.blackjack;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import lombok.SneakyThrows;

import static com.lette1394.blackjack.ConsoleGameRunner.END_MESSAGE;
import static com.lette1394.blackjack.ConsoleGameRunner.START_MESSAGE;
import static com.lette1394.blackjack.ConsoleGameRunner.WAIT_MESSAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsoleGameTestRunner {
    private final Scanner userInput;

    @SneakyThrows
    public ConsoleGameTestRunner() {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        System.setOut(new PrintStream(out));
        this.userInput = new Scanner(in);
    }

    public void hasShownWaitForPlayer() {
        assertThat(nextConsoleInput(), is(WAIT_MESSAGE));
    }

    public void hasReceivedPlayerJoinInput() {
        assertThat(nextConsoleInput(), is(StandardInputOutputUI.COMMAND_JOIN));
    }

    public void hasShownGameIsStarted() {
        assertThat(nextConsoleInput(), is(START_MESSAGE));
    }

    public void hasShownGameIsEnded() {
        assertThat(nextConsoleInput(), is(END_MESSAGE));
    }

    public void hasShownCards(String cards) {
        assertThat(nextConsoleInput(), is(cards)); // (♥️1) (♠️A) "(♦️2) (♣️8)"
    }

    public void hasReceivedPlayerStayInput() {
        assertThat(nextConsoleInput(), is(StandardInputOutputUI.COMMAND_STAY));
    }

    public void hasShownPlayerScore(int score) {
        assertThat(nextConsoleInput(), is(String.valueOf(score)));
    }

    public void hasReceivedPlayerHitInput() {
        assertThat(nextConsoleInput(), is(StandardInputOutputUI.COMMAND_HIT));
    }


    private String nextConsoleInput() {
        return userInput.nextLine();
    }
}
