package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StandardInputOutputUI {
    private final ConsoleGameRunner runner = new ConsoleGameRunner();

    public void join() {
        send(ConsoleGameRunner.COMMAND_JOIN);
        runner.runCommand();
    }

    public void stay() {
        send(ConsoleGameRunner.COMMAND_STAY);
        runner.runCommand();
    }

    private void send(String input) {
        System.out.println(input);
    }
}
