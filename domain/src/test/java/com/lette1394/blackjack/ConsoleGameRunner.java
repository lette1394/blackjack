package com.lette1394.blackjack;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsoleGameRunner {
    private final ByteArrayOutputStream out;

    public ConsoleGameRunner() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    public void hasShownWaitForPlayer() {
        final Scanner scanner = new Scanner(out.toString());
        assertThat(scanner.nextLine(), is("wait for player..."));
    }

    public void hasReceivedPlayersJoinInput() {
        final Scanner scanner = new Scanner(out.toString());
        scanner.nextLine();
        assertThat(scanner.nextLine(), is("player now join"));
    }

    public void hasShownGameIsStarted() {
        final Scanner scanner = new Scanner(out.toString());
        scanner.nextLine();
        scanner.nextLine();
        assertThat(scanner.nextLine(), is("new blackjack start"));
    }

    public void hasShownGameIsEnded() {
        final Scanner scanner = new Scanner(out.toString());
        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        assertThat(scanner.nextLine(), is("game finished"));
    }




    public void waitForPlayer() {
    }

}
