package com.lette1394.blackjack.io;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.domain.InvalidBlackjackPlayerCommandListener;

@RequiredArgsConstructor
public class ConsoleInvalidPlayerInputHandler extends InvalidBlackjackPlayerCommandListener {
    private final GameOutput gameOutput;

    @Override
    public void cannotHandle(final String invalidInput) {
        gameOutput.send(String.format("wrong input: %s", invalidInput));
    }
}
