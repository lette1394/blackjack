package com.lette1394.blackjack;

import java.io.InputStream;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class ConsoleGameRunnerAssertion {
    private final Scanner userInput;

    public ConsoleGameRunnerAssertion(final InputStream userInput) {
        this.userInput = new Scanner(userInput);
    }

    public void hasShownWaitForPlayer() {
        assertThat(nextConsoleInput(), is("wait for player..."));
    }

    public void hasShownGameIsStarted() {
        assertThat(nextConsoleInput(), is("new blackjack game start"));
    }

    public void hasShownGameIsEnded() {
        assertThat(nextConsoleInput(), is("game ended"));
    }

    public void hasShownDrawCardToPlayer(final String cards) {
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

    public void hasShownPlayerWin() {
        assertThat(nextConsoleInput(), containsString("You WIN"));
    }

    public void hasShownInputIsInvalidAndHelpMessages() {
        assertThat(nextConsoleInput(), containsString("wrong input: "));
    }

    public void hasShownPlayerGotBust(final int playerBustScore) {
        assertThat(nextConsoleInput(), is("You got BUST. score: " + playerBustScore));
    }

    public void hasShownDealerGotBust(final int dealerBustScore) {
        assertThat(nextConsoleInput(), is("Dealer got BUST. score: " + dealerBustScore));
    }

    private String nextConsoleInput() {
        return userInput.nextLine();
    }
}
