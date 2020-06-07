package com.lette1394.blackjack.io;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringFormatTemplateInputOutputAdapter implements InputOutput {
    private final String stringFormat;
    private final InputOutput inputOutput;

    @Override
    public void send(final Object output) {
        inputOutput.send(output);
    }

    @Override
    public String get() {
        return String.format(stringFormat, inputOutput.get());
    }

    @Override
    public void close() {
        inputOutput.close();
    }
}
