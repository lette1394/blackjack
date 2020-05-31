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


    public void hasShownDrawCardToPlayer() {
        assertThat(nextConsoleInput(), containsString("Your Cards: "));
    }

    public void hasShownPlayerScore() {
        assertThat(nextConsoleInput(), containsString("Your Score: "));
    }

    public void hasShownDealerGotCards() {
        assertThat(nextConsoleInput(), containsString("Dealer's Cards: "));
    }

    public void hasShownDealerScore() {
        assertThat(nextConsoleInput(), containsString("Dealer's Score: "));
    }

    public void hasShownWinner() {
        assertThat(nextConsoleInput(), containsString("Winner: "));
    }

    private String nextConsoleInput() {
        return userInput.nextLine();
    }
}
