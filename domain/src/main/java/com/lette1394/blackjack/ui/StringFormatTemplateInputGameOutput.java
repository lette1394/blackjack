package com.lette1394.blackjack.ui;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

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
    @SneakyThrows
    public void close() {
        playerInputGameOutput.close();
    }
}
