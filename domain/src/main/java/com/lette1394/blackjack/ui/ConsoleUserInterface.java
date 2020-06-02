package com.lette1394.blackjack.ui;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleUserInterface implements UserInterface {

    private final Scanner in;
    private final PrintStream out;

    public ConsoleUserInterface(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out, true);
    }

    @Override
    public String getNextInput() {
        return in.nextLine();
    }

    @Override
    public void send(final Object output) {
        out.println(output);
    }
}

