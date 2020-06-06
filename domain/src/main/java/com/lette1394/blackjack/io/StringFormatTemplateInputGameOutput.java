package com.lette1394.blackjack.io;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringFormatTemplateInputGameOutput implements PlayerInputGameOutput {
    private final String stringFormat;
    private final PlayerInputGameOutput playerInputGameOutput;

    @Override
    public void send(final Object output) {
        playerInputGameOutput.send(output);
    }

    @Override
    public String get() {
        return String.format(stringFormat, playerInputGameOutput.get());
    }

    @Override
    public void close() {
        playerInputGameOutput.close();
    }
}
