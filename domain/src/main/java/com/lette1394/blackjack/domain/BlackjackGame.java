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
    private final Trumps trumpsForDealer = new Trumps();
    private final Trumps trumpsForPlayer = new Trumps();

    private final TrumpProvider trumpProvider;
    private final EventAnnouncer<BlackjackEventListener> game = new EventAnnouncer<>(BlackjackEventListener.class);

    private boolean isJoined = false;

    @Override
    public void addListener(final BlackjackEventListener listener) {
        game.addListener(listener);
    }

    @Override
    public void onJoin(final Player player) {
        if (isJoined == true){
            game.announce().onIllegalCommand("wrong input: join. game already started. you can type 'hit' or 'stay'");
            return;
        }
        isJoined = true;
        start();

        drawToPlayer(2);
        drawToDealer(1);
    }

    @Override
    public void onHit(final Player player) {
        if (isJoined == false) {
            game.announce().onIllegalCommand("wrong input: hit. You can type 'join'");
            return;
        }
        drawToPlayer(1);

        if (trumpsForPlayer.computeScore() > 21) {
            playerTurnEnds();
            showWinner();
            end();
        }
    }

    @Override
    public void onStay(final Player player) {
        if (isJoined == false) {
            game.announce().onIllegalCommand("wrong input: stay. You can type 'join'");
            return;
        }
        playerTurnEnds();

        showDealerCards(2);
        drawToDealerAtLeast(dealerStopScoreInclusive);
        dealerTurnEnds();

        showWinner();
        end();
    }

    public void start() {
        game.announce().onStart();
    }

    public void drawToPlayer(int howMany) {
        for (int i = 0; i < howMany; i++) {
            trumpsForPlayer.add(trumpProvider.provide());
        }
        game.announce().onPlayerHandChanged(trumpsForPlayer);
    }

    public void playerTurnEnds() {
        game.announce().onPlayerTurnEnds(trumpsForPlayer);
    }

    public void drawToDealer(int showCards) {
        trumpsForDealer.add(trumpProvider.provide());
        trumpsForDealer.add(trumpProvider.provide());

        game.announce().onDealerHandChanged(showCards, trumpsForDealer);
    }

    public void drawToDealerAtLeast(int score) {
        while (trumpsForDealer.computeScore() < score) {
            trumpsForDealer.add(trumpProvider.provide());
            game.announce().onDealerHandChanged(trumpsForDealer.size(), trumpsForDealer);
        }
    }

    public void showDealerCards(int showCards) {
        game.announce().onDealerHandChanged(showCards, trumpsForDealer);
    }

    public void dealerTurnEnds() {
        game.announce().onDealerTurnEnds(trumpsForDealer);
    }

    public void showWinner() {
        game.announce().onShowWinner(trumpsForPlayer, trumpsForDealer);
    }

    public void end() {
        game.announce().onEnd();
    }
}
