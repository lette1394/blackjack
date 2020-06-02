package com.lette1394.blackjack;

import java.io.OutputStream;
import java.io.PrintStream;

import com.lette1394.blackjack.ui.PlayerInputGameOutput;

public class FakePlayerPlayerInputGameOutput implements PlayerInputGameOutput {
    private final PrintStream out;

    public FakePlayerPlayerInputGameOutput(final OutputStream out) {
        this.out = new PrintStream(out, true);
    }

    public void join() {
        sendOutput("join");
    }

    public void hit() {
        sendOutput("hit");
    }

    public void stay() {
        sendOutput("stay");
    }

    @Override
    public String getNextInput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendOutput(final Object output) {
        out.println(output);
    }
}
