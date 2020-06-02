package com.lette1394.blackjack.ui;

public interface UserInputOutput {
    void sendNextOutput(final Object output);

    String getNextInput();
}
