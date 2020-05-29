package com.lette1394.blackjack;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import lombok.SneakyThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsoleGameRunner {
    public static final String WAIT_MESSAGE = "wait for player...";
    public static final String START_MESSAGE = "new blackjack game start";
    public static final String END_MESSAGE = "game ended";

    private final Scanner userInput;

    @SneakyThrows
    public ConsoleGameRunner() {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);

        System.setOut(new PrintStream(out));
        this.userInput = new Scanner(in);
    }

    public void hasShownWaitForPlayer() {
        assertThat(userInput.nextLine(), is(WAIT_MESSAGE));
    }

    public void hasReceivedPlayerJoinInput() {
        assertThat(userInput.nextLine(), is(StandardInputOutputUI.COMMAND_JOIN));
    }

    public void hasShownGameIsStarted() {
        assertThat(userInput.nextLine(), is(START_MESSAGE));
    }

    public void hasShownGameIsEnded() {
        assertThat(userInput.nextLine(), is(END_MESSAGE));
    }



    public void waitForPlayer() {
        System.out.println(WAIT_MESSAGE);
    }

    public void startGame() {
        System.out.println(START_MESSAGE);
    }

    public void endGame() {
        System.out.println(END_MESSAGE);
    }
}
