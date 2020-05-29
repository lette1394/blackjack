package com.lette1394.blackjack;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsoleGameRunner {
    public static final String WAIT_MESSAGE = "wait for player...";
    public static final String START_MESSAGE = "new blackjack game start";
    public static final String END_MESSAGE = "game ended";

    private final Scanner userInput;
    private int score = 0;

    @SneakyThrows
    public ConsoleGameRunner() {
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

    public void hasShownCards() {
        assertThat(nextUserInput(), is("(♦️2) (♣️8)")); // (♥️1) (♠️A)
    }

    public void hasReceivedPlayerStayInput() {
        assertThat(nextUserInput(), is(StandardInputOutputUI.COMMAND_STAY));
    }

    public void hasShownPlayerScore() {
        assertThat(nextUserInput(), is(String.valueOf(score)));
    }

    private String nextUserInput() {
        return userInput.nextLine();
    }


    public void waitForPlayer() {
        send(WAIT_MESSAGE);
    }

    public void start() {
        send(START_MESSAGE);
    }

    public void end() {
        send(END_MESSAGE);
    }

    public void drawTrumps(Trump... trumps) {
        String toView = Arrays.stream(trumps)
                              .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                              .collect(Collectors.joining(" "));

        score = Arrays.stream(trumps)
                      .map(trump -> trump.value)
                      .map(Integer::parseInt)
                      .reduce(0, Integer::sum);

        send(toView);
    }

    public void showPlayerScore() {
        send(score);
    }

    private void send(final Object output) {
        System.out.println(output);
    }
}
