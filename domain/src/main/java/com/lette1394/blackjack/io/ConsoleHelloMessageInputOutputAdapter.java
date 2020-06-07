package com.lette1394.blackjack.io;

public class ConsoleHelloMessageInputOutputAdapter implements InputOutput {
    private final InputOutput inputOutput;

    public ConsoleHelloMessageInputOutputAdapter(final String helloMessage,
                                                 final InputOutput inputOutput) {
        this.inputOutput = inputOutput;

        inputOutput.send(helloMessage);
    }

    @Override
    public void send(final Object output) {
        inputOutput.send(output);
    }

    @Override
    public String get() {
        return inputOutput.get();
    }

    @Override
    public void close() {
        inputOutput.close();
    }
}
