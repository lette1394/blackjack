package com.lette1394.blackjack;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.ui.GameOutput;

@RequiredArgsConstructor
public class ConsoleBlackjackGame implements BlackjackGameEventListener {
    private final GameOutput gameOutput;


    @Override
    public void start() {
        send("new blackjack game start");
    }

    @Override
    public void drawToPlayer(final int numberOfCards, final Trumps trumps) {
        send("Your Cards: " + formatTrump(trumps));
    }

    @Override
    public void drawToDealer(final int numberOfCards, final Trumps trumps) {
        send("Dealer's Cards: " + formatTrump(trumps, numberOfCards));
    }

    @Override
    public void showPlayerScore(final int score) {
        send("Your Score: " + score);
    }

    @Override
    public void showDealerScore(final int score) {
        send("Dealer's Score: " + score);
    }

    @Override
    public void showWinner(final int playerScore, final int dealerScore) {
        if (playerScore > dealerScore) {
            send("You WIN");
        } else {
            send("You LOSE");
        }
    }

    @Override
    public void end() {
        send("game ended");
    }

    private void send(final String output) {
        gameOutput.send(output);
    }

    // TODO: formatter?
    private String formatTrump(Trumps trumps) {
        return trumps.raw().stream()
                     .map(trump -> String.format("(%s%s)", trump.suit, trump.value))
                     .collect(Collectors.joining(" "));
    }

    private String formatTrump(Trumps trumps, int howManyShowingCards) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        for (Trump trump : trumps.raw()) {
            if (i < howManyShowingCards) {
                sb.append(String.format("(%s%s)", trump.suit, trump.value));
            } else {
                sb.append("(??)");
            }
            sb.append(" ");
            i++;
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
