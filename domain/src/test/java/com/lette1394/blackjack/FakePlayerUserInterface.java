package com.lette1394.blackjack;

import java.io.OutputStream;
import java.io.PrintStream;

import com.lette1394.blackjack.ui.UserInterface;

public class FakePlayerUserInterface implements UserInterface {
    private final PrintStream out;

    public FakePlayerUserInterface(final OutputStream out) {
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
    public String getNextInput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(final Object output) {
        out.println(output);
    }
}
