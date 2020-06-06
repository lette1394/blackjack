package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlackjackGame extends NoOpBlackjackPlayerCommandListener {

    private final int dealerStopScore;
    private final Trumps trumpsForDealer = new Trumps();
    private final Trumps trumpsForPlayer = new Trumps();

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
        trumpsForDealer.add(trumpProvider.provide());
        trumpsForDealer.add(trumpProvider.provide());

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
