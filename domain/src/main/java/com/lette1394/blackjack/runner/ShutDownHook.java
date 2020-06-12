package com.lette1394.blackjack.runner;

import java.io.Closeable;

import lombok.SneakyThrows;

import com.lette1394.blackjack.domain.BlackjackEventListener;
import com.lette1394.blackjack.domain.BlackjackGameState;
import com.lette1394.blackjack.domain.player.Player;
import com.lette1394.blackjack.domain.trump.Trumps;

public class ShutDownHook implements BlackjackEventListener {
    private final Closeable[] closeables;

    public ShutDownHook(final Closeable... closeables) {
        this.closeables = closeables;
    }

    @Override
    @SneakyThrows
    public void onGameStateChanged(final BlackjackGameState snapshot) {
        if (snapshot.isFinishing()) {
            for (final Closeable closeable : closeables) {
                closeable.close();
            }
        }
    }

    @Override
    public void onNewPlayerJoin(final Player player) {

    }

    @Override
    public void onPlayerBetting(final Player player) {

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
    public void onShowWinner(final Player player, final boolean isPlayerWin) {

    }

    @Override
    public void onIllegalCommand(final String message) {

    }
}
