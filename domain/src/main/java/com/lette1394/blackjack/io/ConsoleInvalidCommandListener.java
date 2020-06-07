package com.lette1394.blackjack.io;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.domain.InvalidCommandListener;

@RequiredArgsConstructor
public class ConsoleInvalidCommandListener extends InvalidCommandListener {
    private final Output output;

    @Override
    public void cannotHandle(final String invalidInput) {
        output.send(String.format("wrong input: %s", invalidInput));
    }
}
