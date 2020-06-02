package com.lette1394.blackjack;

import java.io.OutputStream;
import java.io.PrintStream;

import com.lette1394.blackjack.ui.UserInputOutput;

public class FakePlayerUserInputOutput implements UserInputOutput {
    private final PrintStream out;

    public FakePlayerUserInputOutput(final OutputStream out) {
        this.out = new PrintStream(out, true);
    }

    public void join() {
        sendNextOutput("join");
    }

    public void hit() {
        sendNextOutput("hit");
    }

    public void stay() {
        sendNextOutput("stay");
    }

    @Override
    public String getNextInput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendNextOutput(final Object output) {
        out.println(output);
    }
}
