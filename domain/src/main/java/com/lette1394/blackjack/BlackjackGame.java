package com.lette1394.blackjack;

import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.ui.GameOutput;

@RequiredArgsConstructor
public class BlackjackGame implements BlackjackPlayerCommandListener, BlackjackGameEventListener {

    private static final String START_MESSAGE = "new blackjack game start";
    private static final String END_MESSAGE = "game ended";

    // TODO: 딜러가 뽑은 카드만 특별하게 체크해야하나? 이건 player가 hit하는 테스트를 추가하고나서 더 생각해보자.
    private Trumps trumpsForDealer;
    private Trumps trumpsForPlayer = new Trumps();

    // TODO: score도 마찬가지. 확실히 Trump 클래스에 들어가는 건 아닌 거 같고, 테스트를 조금 더 추가해서 생각해보자.
    //  아마도 1:1이 아니라 1:N으로 게임할때, 그러니까 딜러는 한명이고 여러 다른 사람들이 게임할때 테스트를 추가하면 더 잘 드러날듯.
    int playerScore = 0;
    int dealerScore = 0;

    private final CardProvider cardProvider;
    private final GameOutput gameOutput;

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
        throw new UnsupportedOperationException();
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
    public void drawToPlayer(int howMany) {
        for (int i = 0; i < howMany; i++) {
            trumpsForPlayer.add(cardProvider.provide());
        }
        playerScore = trumpsForPlayer.getScore();
        send("Your Cards: " + formatTrump(trumpsForPlayer));
    }

    @Override
    public void showPlayerScore() {
        send("Your Score: " + playerScore);
    }

    @Override
    public void drawToDealer(int showCards) {
        if (Objects.isNull(trumpsForDealer)) {
            trumpsForDealer = new Trumps(cardProvider.provide(), cardProvider.provide());
        }
        dealerScore = trumpsForDealer.getScore();
        send("Dealer's Cards: " + formatTrump(trumpsForDealer, showCards));
    }

    @Override
    public void showDealerScore() {
        send("Dealer's Score: " + dealerScore);
    }

    @Override
    public void showWinner() {
        if (playerScore > dealerScore) {
            send("You WIN");
        } else {
            send("You LOSE");
        }
    }

    private void send(final Object output) {
        gameOutput.send(output);
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
