package com.lette1394.blackjack.domain;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.domain.player.Player;
import com.lette1394.blackjack.domain.trump.TrumpProvider;
import com.lette1394.blackjack.domain.trump.Trumps;
import com.lette1394.blackjack.event.EventAnnouncer;
import com.lette1394.blackjack.event.ListenersAware;

@RequiredArgsConstructor
public class BlackjackGame extends NoOpCommandListener implements ListenersAware<BlackjackEventListener> {

    private final int dealerStopScoreInclusive;
    private Trumps trumpsForDealer = new Trumps();
    private Trumps trumpsForPlayer = new Trumps();

    private final TrumpProvider trumpProvider;
    private final EventAnnouncer<BlackjackEventListener> game = new EventAnnouncer<>(BlackjackEventListener.class);

    private BlackjackGameState state = BlackjackGameState.waiting();

    @Override
    public void addListener(final BlackjackEventListener listener) {
        game.addListener(listener);
    }

    @Override
    public void onJoin(final Player player) {
        if (state.isWaiting() == false && state.isFinishing() == false) {
            game.announce().onIllegalCommand("wrong input: join. game already started. you can type 'hit' or 'stay'");
            return;
        }

        startThenSetup();
    }

    @Override
    public void onHit(final Player player) {
        if (state.isDrawing() == false) {
            game.announce().onIllegalCommand("wrong input: hit. You can type 'join'");
            return;
        }

        drawToPlayer(1);
        checkOrFirePlayerGotBust();
    }

    @Override
    public void onStay(final Player player) {
        if (state.isDrawing() == false) {
            game.announce().onIllegalCommand("wrong input: stay. You can type 'join'");
            return;
        }
        game.announce().onPlayerTurnEnds(trumpsForPlayer);

        game.announce().onDealerHandChanged(2, trumpsForDealer);

        while (trumpsForDealer.computeScore() < dealerStopScoreInclusive) {
            trumpsForDealer.add(trumpProvider.provide());
            game.announce().onDealerHandChanged(trumpsForDealer.size(), trumpsForDealer);
        }
        game.announce().onDealerTurnEnds(trumpsForDealer);


        scoring();
    }

    @Override
    public void onRejoin(final Player player) {
        startThenSetup();
    }

    @Override
    public void onLeave(final Player player) {
        state = state.finishing();
        game.announce().onEnd(state);
    }

    private void startThenSetup() {
        start();

        drawToPlayer(2);
        drawToDealer(1);
    }

    private void start() {
        state = state.betting();
        state = state.drawing();

        game.announce().onStart();
        trumpsForDealer = new Trumps();
        trumpsForPlayer = new Trumps();
    }

    private void scoring() {
        state = state.scoring();
        game.announce().onShowWinner(state, trumpsForPlayer, trumpsForDealer);
    }

    public void drawToPlayer(int howMany) {
        for (int i = 0; i < howMany; i++) {
            trumpsForPlayer.add(trumpProvider.provide());
        }
        game.announce().onPlayerHandChanged(trumpsForPlayer);
    }

    public void drawToDealer(int showCards) {
        trumpsForDealer.add(trumpProvider.provide());
        trumpsForDealer.add(trumpProvider.provide());
        game.announce().onDealerHandChanged(showCards, trumpsForDealer);
    }

    private void checkOrFirePlayerGotBust() {
        if (trumpsForPlayer.computeScore() > 21) {
            game.announce().onPlayerTurnEnds(trumpsForPlayer);
            scoring();
        }
    }
}
