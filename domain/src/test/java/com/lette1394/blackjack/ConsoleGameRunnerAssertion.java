package com.lette1394.blackjack;

import java.io.InputStream;
import java.util.Scanner;

import static com.lette1394.blackjack.ConsoleGameRunner.COMMAND_HIT;
import static com.lette1394.blackjack.ConsoleGameRunner.COMMAND_JOIN;
import static com.lette1394.blackjack.ConsoleGameRunner.COMMAND_STAY;
import static com.lette1394.blackjack.ConsoleGameRunner.END_MESSAGE;
import static com.lette1394.blackjack.ConsoleGameRunner.START_MESSAGE;
import static com.lette1394.blackjack.ConsoleGameRunner.WAIT_MESSAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class ConsoleGameRunnerAssertion {
    private final Scanner userInput;

    public ConsoleGameRunnerAssertion(final InputStream userInput) {
        this.userInput = new Scanner(userInput);
    }

    public void hasShownWaitForPlayer() {
        assertThat(nextConsoleInput(), is(WAIT_MESSAGE));
    }

    public void hasReceivedPlayerJoinInput() {
        assertThat(nextConsoleInput(), is(COMMAND_JOIN));
    }

    public void hasShownGameIsStarted() {
        assertThat(nextConsoleInput(), is(START_MESSAGE));
    }

    public void hasShownGameIsEnded() {
        assertThat(nextConsoleInput(), is(END_MESSAGE));
    }

    public void hasShownCards(String cards) {
        assertThat(nextConsoleInput(), is(cards)); // (♥️1) (♠️A) "(♦️2) (♣️8)"
    }

    public void hasReceivedPlayerStayInput() {
        assertThat(nextConsoleInput(), is(COMMAND_STAY));
    }

    public void hasReceivedPlayerHitInput() {
        assertThat(nextConsoleInput(), is(COMMAND_HIT));
    }


    public void hasShownDrawCardToPlayer(String cards) {
        assertThat(nextConsoleInput(), is("Your Cards: " + cards));
    }

    public void hasShownPlayerScore(final int score) {
        assertThat(nextConsoleInput(), is("Your Score: " + score));
    }

    public void hasShownDealerGotCards(final String cards) {
        assertThat(nextConsoleInput(), is("Dealer's Cards: " + cards));
    }

    public void hasShownDealerScore(final int score) {
        assertThat(nextConsoleInput(), is("Dealer's Score: " + score));
    }

    public void hasShownPlayerLose() {
        assertThat(nextConsoleInput(), containsString("You LOSE"));
    }

    private String nextConsoleInput() {
        return userInput.nextLine();
    }

    public void hasShownPlayerWin() {
        assertThat(nextConsoleInput(), containsString("You WIN"));
    }
}
