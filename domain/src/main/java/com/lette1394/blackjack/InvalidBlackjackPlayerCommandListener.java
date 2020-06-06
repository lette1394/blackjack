package com.lette1394.blackjack;

public abstract class InvalidBlackjackPlayerCommandListener extends NoOpBlackjackPlayerCommandListener {
    @Override
    public abstract void cannotHandle(final String invalidInput);
}
