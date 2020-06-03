package com.lette1394.blackjack.ui;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsolePlayerInputGameOutput implements PlayerInputGameOutput {
    private final Scanner in;
    private final PrintStream out;

    public ConsolePlayerInputGameOutput(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out, true);
    }

    @Override
    public String get() {
        return in.nextLine();
    }

    @Override
    public void send(final Object output) {
        out.println(output);
    }
}

