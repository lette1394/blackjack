package com.lette1394.blackjack.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleInputOutput implements InputOutput {
    private final Scanner in;
    private final PrintStream out;

    public ConsoleInputOutput(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out, true, StandardCharsets.UTF_8);
    }

    @Override
    public String get() {
        return in.nextLine();
    }

    @Override
    public void send(final Object output) {
        out.println(output);
    }

    @Override
    public void close() {
        in.close();
        out.close();
    }
}

