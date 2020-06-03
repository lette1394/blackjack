package com.lette1394.blackjack;

import java.util.Objects;

public class BlackjackGame implements BlackjackPlayerCommandListener {

    // TODO: 딜러가 뽑은 카드만 특별하게 체크해야하나? 이건 player가 hit하는 테스트를 추가하고나서 더 생각해보자.
    private Trumps trumpsForDealer;
    private Trumps trumpsForPlayer = new Trumps();

    // TODO: score도 마찬가지. 확실히 Trump 클래스에 들어가는 건 아닌 거 같고, 테스트를 조금 더 추가해서 생각해보자.
    //  아마도 1:1이 아니라 1:N으로 게임할때, 그러니까 딜러는 한명이고 여러 다른 사람들이 게임할때 테스트를 추가하면 더 잘 드러날듯.
    int playerScore = 0;
    int dealerScore = 0;

    private final CardProvider cardProvider;
    private final EventAnnouncer<BlackjackGameEventListener> announcer = new EventAnnouncer<>(BlackjackGameEventListener.class);

    public BlackjackGame(final CardProvider cardProvider) {
        this.cardProvider = cardProvider;
    }

    public void addListener(final BlackjackGameEventListener listener) {
        announcer.addListener(listener);
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
        throw new UnsupportedOperationException();
    }

    public void start() {
        announcer.announce().start();
    }

    public void drawToPlayer(int howMany) {
        for (int i = 0; i < howMany; i++) {
            trumpsForPlayer.add(cardProvider.provide());
        }
        playerScore = trumpsForPlayer.getScore();

        announcer.announce().drawToPlayer(2, trumpsForPlayer);
    }

    public void showPlayerScore() {
        announcer.announce().showPlayerScore(playerScore);
    }

    public void drawToDealer(int showCards) {
        if (Objects.isNull(trumpsForDealer)) {
            trumpsForDealer = new Trumps(cardProvider.provide(), cardProvider.provide());
        }
        dealerScore = trumpsForDealer.getScore();

        announcer.announce().drawToDealer(showCards, trumpsForDealer);
    }

    public void showDealerScore() {
        announcer.announce().showDealerScore(dealerScore);
    }

    public void showWinner() {
        announcer.announce().showWinner(playerScore, dealerScore);
    }

    public void end() {
        announcer.announce().end();
    }
}
