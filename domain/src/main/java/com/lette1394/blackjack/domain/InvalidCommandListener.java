package com.lette1394.blackjack.domain;

public abstract class InvalidCommandListener extends NoOpCommandListener {
    @Override
    public abstract void onInvalidCommand(final String rawInput);
}
