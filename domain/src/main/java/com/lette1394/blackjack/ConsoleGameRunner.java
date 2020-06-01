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

import com.lette1394.blackjack.ui.ConsoleUserInterface;

@Slf4j
public class ConsoleGameRunner implements PlayerInputEventListener {
    public static final String WAIT_MESSAGE = "wait for player...";
    public static final String START_MESSAGE = "new blackjack game start";
    public static final String END_MESSAGE = "game ended";

    public static final String COMMAND_JOIN = "join";
    public static final String COMMAND_HIT = "hit";
    public static final String COMMAND_STAY = "stay";


    // TODO: 이거 좀 없앨 수 없나? 흠...
    //  생성자 의존성으로 빼야하는데 그렇게하면 테스트 만들기가 어렵네. 부분 빌더 패턴?
    @Setter
    private CardProvider cardProvider;

    private ExecutorService executorService;

    // TODO: 딜러가 뽑은 카드만 특별하게 체크해야하나? 이건 player가 hit하는 테스트를 추가하고나서 더 생각해보자.
    private Trumps trumpsForDealer;
    private Trumps trumpsForPlayer = new Trumps();

    // TODO: score도 마찬가지. 확실히 Trump 클래스에 들어가는 건 아닌 거 같고, 테스트를 조금 더 추가해서 생각해보자.
    //  아마도 1:1이 아니라 1:N으로 게임할때, 그러니까 딜러는 한명이고 여러 다른 사람들이 게임할때 테스트를 추가하면 더 잘 드러날듯.
    int playerScore = 0;
    int dealerScore = 0;


    private ConsoleUserInterface consoleUserInterface;

    public ConsoleGameRunner(InputStream in, OutputStream out) {
        consoleUserInterface = new ConsoleUserInterface(in, out, this);
    }

    public static void main(String[] args) {
        final ConsoleGameRunner runner = new ConsoleGameRunner(System.in, System.out);
        // TODO: 랜덤 생성기로
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
        consoleUserInterface.runCommand();
    }

    @Override
    public void join() {
        start();
        drawToPlayer(2);

        // TODO: 카드를 보여준다/안보여준다는 도메인 개념으로 빼야할 것 같다.
        drawToDealer(1);
    }

    @Override
    public void hit() {
        drawToPlayer(1);
    }

    @Override
    public void stay() {
        showPlayerScore();

        drawToDealer(2);
        showDealerScore();

        showWinner();
        end();
    }

    @Override
    public void cannotHandle(final String rawInput) {
        // TODO: 에러처리 필요. 이거 메인 클래스로 전파가 안된다. 다른 러너에서는 괜찮나?
        log.error("cannot process user command: " + rawInput);
        System.exit(1);
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

    public void drawToPlayer(int howMany) {
        for (int i = 0; i < howMany; i++) {
            trumpsForPlayer.add(cardProvider.provide());
        }
        playerScore = trumpsForPlayer.getScore();
        send("Your Cards: " + formatTrump(trumpsForPlayer));
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
        consoleUserInterface.send(output);
    }


    // TODO: formatter?
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
