package com.lette1394.blackjack;

public class NoOpBlackjackPlayerCommandListener implements BlackjackPlayerCommandListener {
    @Override
    public void join(final Player player) {
        // no-op
    }

    @Override
    public void hit(final Player player) {
        // no-op
    }

    @Override
    public void stay(final Player player) {
        // no-op
    }

    @Override
    public void cannotHandle(final String invalidInput) {
        // no-op
    }
}
