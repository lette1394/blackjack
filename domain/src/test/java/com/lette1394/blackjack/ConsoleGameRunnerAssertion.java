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

    public void hasShownPlayerJoin(final String playerId) {
        assertThat(nextConsoleInput(), is(String.format("playerId:[%s] joined", playerId)));
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

    public void hasShownPlayerDraw() {
        assertThat(nextConsoleInput(), containsString("Game DRAW"));
    }

    public void hasShownPlayerWin() {
        assertThat(nextConsoleInput(), containsString("You WIN"));
    }

    public void hasShownInputIsInvalidAndHelpMessages(final String message) {
        assertThat(nextConsoleInput(), is(message));
    }

    public void hasShownPlayerGotBust(final int playerBustScore) {
        assertThat(nextConsoleInput(), is("You got BUST. score: " + playerBustScore));
    }

    public void hasShownPlayerRemainingCoins(final int coin) {
        assertThat(nextConsoleInput(), is("Your coins: " + coin));
    }

    public void hasShownDealerGotBust(final int dealerBustScore) {
        assertThat(nextConsoleInput(), is("Dealer got BUST. score: " + dealerBustScore));
    }

    public void hasShownTryItAgain() {
        assertThat(nextConsoleInput(), is("Another game? [join|leave]"));
    }

    public void hasShownPlayerGotBankrupt() {
        assertThat(nextConsoleInput(), is("GAME OVER. You got bankrupt."));
    }

    public void hasShownCannotBetMoneyOver(final int atMost) {
        assertThat(nextConsoleInput(), is("Cannot bet money over " + atMost));
    }

    private String nextConsoleInput() {
        return userInput.nextLine();
    }
}
