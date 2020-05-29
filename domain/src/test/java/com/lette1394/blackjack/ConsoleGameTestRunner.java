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
        assertThat(nextUserInput(), is(WAIT_MESSAGE));
    }

    public void hasReceivedPlayerJoinInput() {
        assertThat(nextUserInput(), is(StandardInputOutputUI.COMMAND_JOIN));
    }

    public void hasShownGameIsStarted() {
        assertThat(nextUserInput(), is(START_MESSAGE));
    }

    public void hasShownGameIsEnded() {
        assertThat(nextUserInput(), is(END_MESSAGE));
    }

    public void hasShownCards(String cards) {
        assertThat(nextUserInput(), is(cards)); // (♥️1) (♠️A) "(♦️2) (♣️8)"
    }

    public void hasReceivedPlayerStayInput() {
        assertThat(nextUserInput(), is(StandardInputOutputUI.COMMAND_STAY));
    }

    public void hasShownPlayerScore(int score) {
        assertThat(nextUserInput(), is(String.valueOf(score)));
    }

    private String nextUserInput() {
        return userInput.nextLine();
    }
}
