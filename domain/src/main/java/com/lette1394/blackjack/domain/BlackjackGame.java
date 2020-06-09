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

    private BlackjackGameSnapshot snapshot = BlackjackGameSnapshot.waiting();

    @Override
    public void addListener(final BlackjackEventListener listener) {
        game.addListener(listener);
    }

    @Override
    public void onJoin(final Player player) {
        if (snapshot.isWaiting() == false && snapshot.isFinishing() == false) {
            game.announce().onIllegalCommand("wrong input: join. game already started. you can type 'hit' or 'stay'");
            return;
        }

        startThenSetup();
    }

    @Override
    public void onHit(final Player player) {
        if (snapshot.isDrawing() == false) {
            game.announce().onIllegalCommand("wrong input: hit. You can type 'join'");
            return;
        }

        drawToPlayer(1);
        game.announce().onPlayerHandChanged(trumpsForPlayer);

        if (trumpsForPlayer.computeScore() > 21) {
            game.announce().onPlayerTurnEnds(trumpsForPlayer);

            scoring();
        }
    }

    private void scoring() {
        snapshot = snapshot.scoring();
        game.announce().onShowWinner(snapshot, trumpsForPlayer, trumpsForDealer);
    }

    @Override
    public void onStay(final Player player) {
        if (snapshot.isDrawing() == false) {
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

    private void startThenSetup() {
        snapshot = snapshot.betting();
        snapshot = snapshot.drawing();

        game.announce().onStart();
        trumpsForDealer = new Trumps();
        trumpsForPlayer = new Trumps();

        drawToPlayer(2);
        game.announce().onPlayerHandChanged(trumpsForPlayer);

        drawToDealer(1);
        game.announce().onDealerHandChanged(1, trumpsForDealer);
    }

    @Override
    public void onLeave(final Player player) {
        snapshot = snapshot.finishing();
        game.announce().onEnd(snapshot);
    }

    public void drawToPlayer(int howMany) {
        for (int i = 0; i < howMany; i++) {
            trumpsForPlayer.add(trumpProvider.provide());
        }
    }

    public void drawToDealer(int showCards) {
        trumpsForDealer.add(trumpProvider.provide());
        trumpsForDealer.add(trumpProvider.provide());
    }
}
