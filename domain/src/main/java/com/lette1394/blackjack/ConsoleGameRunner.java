package com.lette1394.blackjack;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConsoleGameRunner implements GameRunner {
    public static final String WAIT_MESSAGE = "wait for player...";
    public static final String START_MESSAGE = "new blackjack game start";
    public static final String END_MESSAGE = "game ended";

    private int score = 0;

    @Override
    public void waitForPlayer() {
        send(WAIT_MESSAGE);
    }

    @Override
    public void start() {
        send(START_MESSAGE);
    }

    @Override
    public void end() {
        send(END_MESSAGE);
    }

    @Override
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

    @Override
    public void showPlayerScore() {
        send(score);
    }

    private void send(final Object output) {
        System.out.println(output);
    }
}
