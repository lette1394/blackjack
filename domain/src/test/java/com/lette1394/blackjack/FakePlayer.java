package com.lette1394.blackjack;

import java.io.OutputStream;
import java.io.PrintStream;

import com.lette1394.blackjack.ui.PlayerInputGameOutput;

public class FakePlayer implements PlayerInputGameOutput {
    private final PrintStream out;

    public FakePlayer(final OutputStream out) {
        this.out = new PrintStream(out, true);
    }

    public void join() {
        send("playerId=1234; command=join");
    }

    public void hit() {
        send("playerId=1234; command=hit");
    }

    public void stay() {
        send("playerId=1234; command=stay");
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(final Object output) {
        out.println(output);
    }

    @Override
    public void close() {

    }
}
