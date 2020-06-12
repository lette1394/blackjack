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



    private int bet;

    @Override
    public void addListener(final BlackjackEventListener listener) {
        game.addListener(listener);
    }

    @Override
    public void onJoin(final Player player) {
        // TODO: 상태를 검사하기만 하는 decorator를 끼워넣어야 겠다.
        // TODO: 이렇게 검사하지말고, state 한테 물어보자.
        if (state.isWaiting() == false && state.isFinishing() == false) {
            game.announce().onIllegalCommand("wrong input: join. game already started. you can type 'hit' or 'stay'");
            return;
        }

        game.announce().onNewPlayerJoin(player);
    }

    @Override
    public void onBet(final Player player, int coin) {
        this.bet = coin;
        startThenSetup();
    }

    @Override
    public void onHit(final Player player) {
        if (state.isDrawing() == false) {
            game.announce().onIllegalCommand("wrong input: hit. You can type 'join'");
            return;
        }

        drawToPlayer(1);
        checkOrFirePlayerGotBust(player);
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


        scoring(player);
    }

    @Override
    public void onRejoin(final Player player) {
        // TODO: 이거 input validation 검사하는 코드 짜기전에, 테스트를 짜보자.
        game.announce().onPlayerBetting(player);
    }

    @Override
    public void onLeave(final Player player) {
        state = state.finishing();
        game.announce().onGameStateChanged(state);
    }

    private void startThenSetup() {
        start();

        drawToPlayer(2);
        drawToDealer(1);
    }

    private void start() {
        state = state.betting();
        state = state.drawing();

        game.announce().onGameStateChanged(state);
        trumpsForDealer = new Trumps();
        trumpsForPlayer = new Trumps();
    }

    private void scoring(final Player player) {
        state = state.scoring();
        if (trumpsForPlayer.computeScore() > 21) {
            player.setCoins(player.getCoins() - bet);
            game.announce().onShowWinner(player, false);
            return;
        }

        if (trumpsForDealer.computeScore() > 21) {
            player.setCoins(player.getCoins() + bet);
            game.announce().onShowWinner(player, true);
            return;
        }

        if (trumpsForPlayer.computeScore() > trumpsForDealer.computeScore()) {
            player.setCoins(player.getCoins() + bet);
            game.announce().onShowWinner(player, true);
        } else {
            player.setCoins(player.getCoins() - bet);
            game.announce().onShowWinner(player, false);
        }
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

    private void checkOrFirePlayerGotBust(final Player player) {
        if (trumpsForPlayer.computeScore() > 21) {
            game.announce().onPlayerTurnEnds(trumpsForPlayer);
            scoring(player);
        }
    }
}
