package com.lette1394.blackjack.domain;

import com.lette1394.blackjack.domain.player.Player;

public class NoOpCommandListener implements CommandListener {
    @Override
    public void onJoin(final Player player) {
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
    public void onInvalidCommand(final String rawInput) {
        // no-op
    }
}
