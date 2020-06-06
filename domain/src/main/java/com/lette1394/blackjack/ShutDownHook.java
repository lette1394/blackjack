package com.lette1394.blackjack;

import java.io.Closeable;

import lombok.SneakyThrows;

public class ShutDownHook implements BlackjackGameEventListener {
    private final Closeable[] closeables;

    public ShutDownHook(final Closeable... closeables) {
        this.closeables = closeables;
    }

    @Override
    public void start() {

    }

    @Override
    public void playerHandChanged(final Trumps trumps) {

    }

    @Override
    public void dealerHandChanged(final int showCards, final Trumps trumps) {

    }

    @Override
    public void playerTurnEnds(final Trumps trumps) {

    }

    @Override
    public void dealerTurnEnds(final Trumps trumps) {

    }

    @Override
    public void showWinner(final Trumps playerTrumps, final Trumps dealerTrumps) {

    }

    @Override
    @SneakyThrows
    public void end() {
        for (final Closeable closeable : closeables) {
            closeable.close();
        }
    }
}