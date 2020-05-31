package com.lette1394.blackjack;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
        new ConsoleGameRunner().run();
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
                if (userInput.equals(COMMAND_JOIN)) {
                    start();
                    drawToPlayer();
                } else if (userInput.equals(COMMAND_STAY)) {
                    showPlayerScore();

                    drawToDealer();
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
        Trump[] trumps = new Trump[]{ cardProvider.provide(), cardProvider.provide() };
        String toView = Arrays.stream(trumps)
                              .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                              .collect(Collectors.joining(" "));

        playerScore = Arrays.stream(trumps)
                .map(trump -> trump.value)
                .map(Integer::valueOf)
                .reduce(0, Integer::sum);

        send("Your Cards: " + toView);
    }

    public void showPlayerScore() {
        send("Your Score: " + playerScore);
    }

    public void drawToDealer() {
        Trump[] trumps = new Trump[]{ cardProvider.provide(), cardProvider.provide() };
        String toView = Arrays.stream(trumps)
                              .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                              .collect(Collectors.joining(" "));

        dealerScore = Arrays.stream(trumps)
                            .map(trump -> trump.value)
                            .map(Integer::valueOf)
                            .reduce(0, Integer::sum);

        send("Dealer's Cards: " + toView);
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
}
