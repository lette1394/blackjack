package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlackjackGame implements BlackjackPlayerCommandListener {

    private final int dealerStopScore;
    private Trumps trumpsForDealer;
    private Trumps trumpsForPlayer = new Trumps();

    private final TrumpProvider trumpProvider;
    private final EventAnnouncer<BlackjackGameEventListener> game = new EventAnnouncer<>(BlackjackGameEventListener.class);

    public void addListener(final BlackjackGameEventListener listener) {
        game.addListener(listener);
    }

    @Override
    public void join(final Player player) {
        start();
        drawToPlayer(2);
        drawToDealer(1);
    }

    @Override
    public void hit(final Player player) {
        drawToPlayer(1);
    }

    @Override
    public void stay(final Player player) {
        playerTurnEnds();

        showDealerCards(2);
        drawToDealerAtLeast(dealerStopScore);
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

        game.announce().playerHandChanged(trumpsForPlayer);
    }

    public void playerTurnEnds() {
        game.announce().playerTurnEnds(trumpsForPlayer);
    }

    public void drawToDealer(int showCards) {
        trumpsForDealer = new Trumps(trumpProvider.provide(), trumpProvider.provide());

        game.announce().dealerHandChanged(showCards, trumpsForDealer);
    }

    public void drawToDealerAtLeast(int score) {
        while (trumpsForDealer.computeScore() < score) {
            trumpsForDealer.add(trumpProvider.provide());
            game.announce().dealerHandChanged(trumpsForDealer.size(), trumpsForDealer);
        }
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
