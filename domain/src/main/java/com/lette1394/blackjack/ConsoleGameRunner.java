package com.lette1394.blackjack;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConsoleGameRunner {
    public static final String WAIT_MESSAGE = "wait for player...";
    public static final String START_MESSAGE = "new blackjack game start";
    public static final String END_MESSAGE = "game ended";

    private int score = 0;

    public void waitForPlayer() {
        send(WAIT_MESSAGE);
    }

    public void start() {
        send(START_MESSAGE);
    }

    public void end() {
        send(END_MESSAGE);
    }

    public void drawToPlayer() {
        Trump[] trumps = new Trump[]{ new Trump("♦️", "2"), new Trump("♣️", "8") };
        String toView = Arrays.stream(trumps)
                              .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                              .collect(Collectors.joining(" "));

        score = Arrays.stream(trumps)
                      .map(trump -> trump.value)
                      .map(Integer::parseInt)
                      .reduce(0, Integer::sum);

        send("Your Cards: "+toView);
    }

    public void showPlayerScore() {
        send("Your Score: " + 10);
    }

    public void drawToDealer() {
        Trump[] trumps = new Trump[]{ new Trump("♦️", "3"), new Trump("♣️", "8") };
        String toView = Arrays.stream(trumps)
                              .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                              .collect(Collectors.joining(" "));

        score = Arrays.stream(trumps)
                      .map(trump -> trump.value)
                      .map(Integer::parseInt)
                      .reduce(0, Integer::sum);

        send("Dealer's Cards: "+toView);
    }

    public void showDealerScore() {
        send("Dealer's Score: " + 10);
    }

    public void showWinner() {
        send("Winner: Dealer");
    }

    private void send(final Object output) {
        System.out.println(output);
    }
}
