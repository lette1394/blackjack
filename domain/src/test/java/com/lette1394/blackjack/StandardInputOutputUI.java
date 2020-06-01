package com.lette1394.blackjack;

import java.io.OutputStream;
import java.io.PrintStream;

public class StandardInputOutputUI {
    private final PrintStream out;

    public StandardInputOutputUI(final OutputStream out) {
        this.out = new PrintStream(out, true);
    }

    public void join() {
        send(ConsoleGameRunner.COMMAND_JOIN);
    }

    public void hit() {
        send(ConsoleGameRunner.COMMAND_HIT);
    }

    public void stay() {
        send(ConsoleGameRunner.COMMAND_STAY);
    }

    private void send(String input) {
        out.println(input);
    }
}
