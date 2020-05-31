package com.lette1394.blackjack;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class ConsoleGameRunner {
    public static final String WAIT_MESSAGE = "wait for player...";
    public static final String START_MESSAGE = "new blackjack game start";
    public static final String END_MESSAGE = "game ended";

    public static final String COMMAND_JOIN = "join";
    public static final String COMMAND_STAY = "stay";
    public static final String COMMAND_HIT = "hit";

    public final Scanner in;
    public final PrintStream out;

    public ConsoleGameRunner(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public ConsoleGameRunner() {
        this(System.in, System.out);
    }

    public void run() {
        waitForPlayer();
    }

    public void runCommand() {
        final String userInput = in.nextLine();
        if (userInput.equals(COMMAND_JOIN)) {
            start();
            drawToPlayer();
        } else if (userInput.equals(COMMAND_STAY)) {
            showPlayerScore();

            drawToDealer();
            showDealerScore();

            showWinner();
            end();
        } else {
            throw new RuntimeException();
        }
    }

    @SneakyThrows
    private boolean needWaitForPlayerInput() {
        return in.hasNextLine() == false;
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

    public void drawToPlayer() {
        Trump[] trumps = new Trump[]{ new Trump("♦️", "2"), new Trump("♣️", "8") };
        String toView = Arrays.stream(trumps)
                              .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                              .collect(Collectors.joining(" "));

        send("Your Cards: " + toView);
    }

    public void showPlayerScore() {
        send("Your Score: " + 10);
    }

    public void drawToDealer() {
        Trump[] trumps = new Trump[]{ new Trump("♦️", "3"), new Trump("♣️", "8") };
        String toView = Arrays.stream(trumps)
                              .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                              .collect(Collectors.joining(" "));

        send("Dealer's Cards: " + toView);
    }

    public void showDealerScore() {
        send("Dealer's Score: " + 10);
    }

    public void showWinner() {
        send("Winner: Dealer");
    }

    private void send(final Object output) {
        out.println(output);
    }
}
