package com.lette1394.blackjack.testutil;

import java.io.OutputStream;
import java.io.PrintStream;

import com.lette1394.blackjack.io.InputOutput;

public class ConsoleFakeInputOutput implements InputOutput {
    private final PrintStream out;
    private final String playerId;

    public ConsoleFakeInputOutput(final OutputStream out, final String playerId) {
        this.out = new PrintStream(out, true);
        this.playerId = playerId;
    }

    public void join() {
        send(String.format("playerId=%s; command=join", playerId));
    }

    public void bet(final int coin) {
        send(String.format("playerId=%s; command=bet; coin=%s", playerId, coin));
    }

    public void hit() {
        send(String.format("playerId=%s; command=hit", playerId));
    }

    public void stay() {
        send(String.format("playerId=%s; command=stay", playerId));
    }

    public void rejoin() {
        send(String.format("playerId=%s; command=rejoin", playerId));
    }

    public void leave() {
        send(String.format("playerId=%s; command=leave", playerId));
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
        out.close();
    }
}
