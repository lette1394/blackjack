package com.lette1394.blackjack;

import java.io.OutputStream;
import java.io.PrintStream;

public class StandardInputOutputUI {
    private final PrintStream out;

    public StandardInputOutputUI(final OutputStream out) {
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

    private void send(String input) {
        out.println(input);
    }
}
