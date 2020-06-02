package com.lette1394.blackjack;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.UserInterface;

@Slf4j
@RequiredArgsConstructor
public class ConsoleGameRunner implements GameEventListener, GameRunner {
    private static final String WAIT_MESSAGE = "wait for player...";
    private static final String START_MESSAGE = "new blackjack game start";
    private static final String END_MESSAGE = "game ended";

    // TODO: 딜러가 뽑은 카드만 특별하게 체크해야하나? 이건 player가 hit하는 테스트를 추가하고나서 더 생각해보자.
    private Trumps trumpsForDealer;
    private Trumps trumpsForPlayer = new Trumps();

    // TODO: score도 마찬가지. 확실히 Trump 클래스에 들어가는 건 아닌 거 같고, 테스트를 조금 더 추가해서 생각해보자.
    //  아마도 1:1이 아니라 1:N으로 게임할때, 그러니까 딜러는 한명이고 여러 다른 사람들이 게임할때 테스트를 추가하면 더 잘 드러날듯.
    int playerScore = 0;
    int dealerScore = 0;

    // TODO: 이거 좀 없앨 수 없나? 흠...
    //  생성자 의존성으로 빼야하는데 그렇게하면 테스트 만들기가 어렵네. 부분 빌더 패턴?
    @Setter
    private CardProvider cardProvider;

    private final UserInterface userInterface;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void run() {
        waitForPlayer();
        executorService.submit(() -> userInterface.runLoop());
    }

    private void waitForPlayer() {
        send(WAIT_MESSAGE);
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

    public void start() {
        send(START_MESSAGE);
    }

    public void end() {
        send(END_MESSAGE);
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
        userInterface.send(output);
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
