package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.ui.GameOutput;

@RequiredArgsConstructor
public class ConsoleInvalidPlayerInputHandler extends NoOpBlackjackPlayerCommandListener {
    private final GameOutput gameOutput;

    @Override
    public void cannotHandle(final String invalidInput) {
        gameOutput.send(String.format("wrong input: %s", invalidInput));
    }
}
