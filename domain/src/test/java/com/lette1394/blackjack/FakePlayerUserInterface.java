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
        send(ConsoleGameLauncher.COMMAND_JOIN);
    }

    public void hit() {
        send(ConsoleGameLauncher.COMMAND_HIT);
    }

    public void stay() {
        send(ConsoleGameLauncher.COMMAND_STAY);
    }

    @Override
    public void runLoop() {
        // no-op
    }

    @Override
    public void send(final Object output) {
        out.println(output);
    }
}
