package com.lette1394.blackjack;

public class StandardInputOutputUI {
    public static final String COMMAND_JOIN = "join";
    public static final String COMMAND_STAY = "stay";

    public void join() {
        send(COMMAND_JOIN);
    }

    public void stay() {
        send(COMMAND_STAY);
    }

    private void send(String input) {
        System.out.println(input);
    }
}
