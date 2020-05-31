package com.lette1394.blackjack;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleGameRunner {
    public static final String WAIT_MESSAGE = "wait for player...";
    public static final String START_MESSAGE = "new blackjack game start";
    public static final String END_MESSAGE = "game ended";

    public static final String COMMAND_JOIN = "join";
    public static final String COMMAND_STAY = "stay";
    public static final String COMMAND_HIT = "hit";

    public final Scanner in;
    public final PrintStream out;

    @Setter
    private CardProvider cardProvider;

    private ExecutorService executorService;

    private Trumps trumpsForDealer;
    int playerScore = 0;
    int dealerScore = 0;

    public ConsoleGameRunner(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out, true);
    }

    public ConsoleGameRunner() {
        this(System.in, System.out);
    }

    public static void main(String[] args) {
        final ConsoleGameRunner runner = new ConsoleGameRunner();
        runner.setCardProvider(new ArrayDeque<>(Lists.newArrayList(new Trump("♦️", "5"), new Trump("♣️", "5"),
                                                                   new Trump("♥️", "3"), new Trump("♠️", "1")))::poll);
        runner.run();
    }

    public void run() {
        waitForPlayer();

        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> runCommand());
    }

    @SneakyThrows
    public void runCommand() {
        while (true) {
            try {

                final String userInput = in.nextLine();
                System.out.println("\f");

                if (userInput.equals(COMMAND_JOIN)) {
                    start();
                    drawToPlayer();
                    drawToDealer(1);
                } else if (userInput.equals(COMMAND_STAY)) {
                    showPlayerScore();

                    drawToDealer(2);
                    showDealerScore();

                    showWinner();
                    end();
                    break;
                } else {
                    log.error("cannot process user command: " + userInput);
                    System.exit(1);
                }


                Thread.sleep(50);
            } catch (Exception e) {
                log.error("unexpected error : " + e);
                System.exit(99);
            }
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
        executorService.shutdown();
    }

    public void drawToPlayer() {
        Trumps trumps = new Trumps(cardProvider.provide(), cardProvider.provide());
        playerScore = trumps.getScore();
        send("Your Cards: " + formatTrump(trumps));
    }

    public void showPlayerScore() {
        send("Your Score: " + playerScore);
    }

    public void drawToDealer(int showCards) {
        if (Objects.isNull(trumpsForDealer)) {
            trumpsForDealer = new Trumps(cardProvider.provide(), cardProvider.provide());
        }
        dealerScore = trumpsForDealer.getScore();
        send("Dealer's Cards: " + formatTrump(trumpsForDealer, showCards));
    }

    public void showDealerScore() {
        send("Dealer's Score: " + dealerScore);
    }

    public void showWinner() {
        if (playerScore > dealerScore) {
            send("You WIN");
        } else {
            send("You LOSE");
        }
    }

    private void send(final Object output) {
        out.println(output);
    }

    private String formatTrump(Trumps trumps) {
        return trumps.raw().stream()
                     .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                     .collect(Collectors.joining(" "));
    }

    private String formatTrump(Trumps trumps, int howManyShowingCards) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        for (Trump trump : trumps.raw()) {
            if (i < howManyShowingCards) {
                sb.append(String.format("(%s%s)", trump.suit, trump.value));
            } else {
                sb.append("(??)");
            }
            sb.append(" ");
            i++;
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
