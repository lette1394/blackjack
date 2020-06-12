package com.lette1394.blackjack.domain;

import com.lette1394.blackjack.domain.player.Player;

public class NoOpCommandListener implements CommandListener {
    @Override
    public void onHistory(final Player player) {
        // no-op
    }

    @Override
    public void onJoin(final Player player) {
        // no-op
    }

    @Override
    public void onBet(final Player player, int coin) {
        // no-op
    }

    @Override
    public void onHit(final Player player) {
        // no-op
    }

    @Override
    public void onStay(final Player player) {
        // no-op
    }

    @Override
    public void onLeave(final Player player) {
        // no-op
    }

    @Override
    public void onRejoin(final Player player) {
        // no-op
    }

    @Override
    public void onInvalidCommand(final String rawInput) {
        // no-op
    }
}
