package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StandardInputOutputUI {
    public static final String COMMAND_JOIN = "join";
    public static final String COMMAND_STAY = "stay";
    public static final String COMMAND_HIT = "hit";

    private final ConsoleGameRunner gameRunner;

    public void join() {
        send(COMMAND_JOIN);
        gameRunner.start();
        gameRunner.drawToPlayer();
    }

    public void stay() {
        send(COMMAND_STAY);
        gameRunner.showPlayerScore();

        gameRunner.drawToDealer();
        gameRunner.showDealerScore();

        gameRunner.showWinner();
    }



    private void send(String input) {
        System.out.println(input);
    }
}
