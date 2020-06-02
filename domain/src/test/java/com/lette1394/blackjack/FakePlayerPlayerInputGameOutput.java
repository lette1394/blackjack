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
        send("join");
    }

    public void hit() {
        send("hit");
    }

    public void stay() {
        send("stay");
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(final Object output) {
        out.println(output);
    }
}
