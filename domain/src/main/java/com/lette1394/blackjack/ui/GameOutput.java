package com.lette1394.blackjack.ui;

@FunctionalInterface
public interface GameOutput {
    void send(final Object output);
}
