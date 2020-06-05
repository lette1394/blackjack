package com.lette1394.blackjack;

public class BlackjackGame implements BlackjackPlayerCommandListener {

    // TODO: 딜러가 뽑은 카드만 특별하게 체크해야하나? 이건 player가 hit하는 테스트를 추가하고나서 더 생각해보자.
    private Trumps trumpsForDealer;
    private Trumps trumpsForPlayer = new BlackjackTrumps();

    private final TrumpProvider trumpProvider;
    private final EventAnnouncer<BlackjackGameEventListener> game = new EventAnnouncer<>(BlackjackGameEventListener.class);

    public BlackjackGame(final TrumpProvider trumpProvider) {
        this.trumpProvider = trumpProvider;
    }

    public void addListener(final BlackjackGameEventListener listener) {
        game.addListener(listener);
    }

    @Override
    public void join() {
        start();
        drawToPlayer(2);
        drawToDealer(1);
    }

    @Override
    public void hit() {
        drawToPlayer(1);
    }

    @Override
    public void stay() {
        playerTurnEnds();

        showDealerCards(2);
        dealerTurnEnds();

        showWinner();
        end();
    }

    @Override
    public void cannotHandle(final String rawInput) {
        throw new UnsupportedOperationException();
    }

    public void start() {
        game.announce().start();
    }

    public void drawToPlayer(int howMany) {
        for (int i = 0; i < howMany; i++) {
            trumpsForPlayer.add(trumpProvider.provide());
        }

        game.announce().playerHandChanged(2, trumpsForPlayer);
    }

    public void playerTurnEnds() {
        game.announce().playerTurnEnds(trumpsForPlayer);
    }

    public void drawToDealer(int showCards) {
        trumpsForDealer = new BlackjackTrumps(trumpProvider.provide(), trumpProvider.provide());

        game.announce().dealerHandChanged(showCards, trumpsForDealer);
    }

    public void showDealerCards(int showCards) {
        game.announce().dealerHandChanged(showCards, trumpsForDealer);
    }

    public void dealerTurnEnds() {
        game.announce().dealerTurnEnds(trumpsForDealer);
    }

    public void showWinner() {
        game.announce().showWinner(trumpsForPlayer, trumpsForDealer);
    }

    public void end() {
        game.announce().end();
    }
}
