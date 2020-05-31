package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StandardInputOutputUI {
    public static final String COMMAND_JOIN = "join";
    public static final String COMMAND_STAY = "stay";
    public static final String COMMAND_HIT = "hit";

    private final GameRunner gameRunner;

    public void join() {
        send(COMMAND_JOIN);
        gameRunner.start();
    }

    private void send(String input) {
        System.out.println(input);
    }
}
