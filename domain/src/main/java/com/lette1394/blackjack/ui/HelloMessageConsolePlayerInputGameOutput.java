package com.lette1394.blackjack.ui;

public class HelloMessageConsolePlayerInputGameOutput implements PlayerInputGameOutput {
    private final PlayerInputGameOutput playerInputGameOutput;

    public HelloMessageConsolePlayerInputGameOutput(final String helloMessage,
                                                    final PlayerInputGameOutput playerInputGameOutput) {
        this.playerInputGameOutput = playerInputGameOutput;

        playerInputGameOutput.send(helloMessage);
    }

    @Override
    public void send(final Object output) {
        playerInputGameOutput.send(output);
    }

    @Override
    public String get() {
        return playerInputGameOutput.get();
    }

    @Override
    public void close() {
        playerInputGameOutput.close();
    }
}
