package com.lette1394.blackjack.runner;

import java.io.Closeable;

import lombok.SneakyThrows;

import com.lette1394.blackjack.domain.BlackjackEventListener;
import com.lette1394.blackjack.domain.BlackjackGameSnapshot;
import com.lette1394.blackjack.domain.trump.Trumps;

public class ShutDownHook implements BlackjackEventListener {
    private final Closeable[] closeables;

    public ShutDownHook(final Closeable... closeables) {
        this.closeables = closeables;
    }

    @Override
    public void onGameStateChanged(final BlackjackGameSnapshot snapshot) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPlayerHandChanged(final Trumps trumps) {

    }

    @Override
    public void onDealerHandChanged(final int showCards, final Trumps trumps) {

    }

    @Override
    public void onPlayerTurnEnds(final Trumps trumps) {

    }

    @Override
    public void onDealerTurnEnds(final Trumps trumps) {

    }

    @Override
    public void onShowWinner(final BlackjackGameSnapshot snapshot,
                             final Trumps playerTrumps,
                             final Trumps dealerTrumps) {

    }

    @Override
    public void onIllegalCommand(final String message) {

    }

    @Override
    @SneakyThrows
    public void onEnd(final BlackjackGameSnapshot snapshot) {
        if (snapshot.isFinishing() == false) {
            return;
        }

        for (final Closeable closeable : closeables) {
            closeable.close();
        }
    }
}
