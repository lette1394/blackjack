package com.lette1394.blackjack.io;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.domain.BlackjackEventListener;
import com.lette1394.blackjack.domain.trump.Trump;
import com.lette1394.blackjack.domain.trump.Trumps;

@RequiredArgsConstructor
public class ConsoleOutput implements BlackjackEventListener {
    private final Output output;

    @Override
    public void start() {
        send("new blackjack game start");
    }

    @Override
    public void playerHandChanged(final Trumps trumps) {
        send("Your Cards: " + formatTrump(trumps));
    }

    @Override
    public void dealerHandChanged(final int numberOfCards, final Trumps trumps) {
        send("Dealer's Cards: " + formatTrump(trumps, numberOfCards));
    }

    @Override
    public void dealerTurnEnds(final Trumps trumps) {
        send("Dealer's Score: " + trumps.computeScore());
    }

    @Override
    public void playerTurnEnds(final Trumps trumps) {
        send("Your Score: " + trumps.computeScore());
    }

    @Override
    public void showWinner(final Trumps playerTrumps, final Trumps dealerTrumps) {
        if (playerTrumps.computeScore() > dealerTrumps.computeScore()) {
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
        this.output.send(output);
    }

    // TODO: formatter?
    private String formatTrump(Trumps trumps) {
        return trumps.raw().stream()
                     .map(TrumpToStringFormatter::format)
                     .collect(Collectors.joining(" "));
    }

    private String formatTrump(Trumps trumps, int howManyShowingCards) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        for (Trump trump : trumps.raw()) {
            if (i < howManyShowingCards) {
                sb.append(TrumpToStringFormatter.format(trump));
            } else {
                sb.append("(??)");
            }
            sb.append(" ");
            i++;
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private static class TrumpToStringFormatter {
        private static final Map<Trump.Suit, String> suitMap = new HashMap<>() {{
            put(Trump.Suit.HEART, "♥️");
            put(Trump.Suit.SPADE, "♠️");
            put(Trump.Suit.DIAMOND, "♦️");
            put(Trump.Suit.CLUB, "♣️");
        }};

        private static final Map<Trump.Value, String> valueMap = new HashMap<>() {{
            put(Trump.Value.ACE, "A");
            put(Trump.Value.TWO, "2");
            put(Trump.Value.THREE, "3");
            put(Trump.Value.FOUR, "4");
            put(Trump.Value.FIVE, "5");
            put(Trump.Value.SIX, "6");
            put(Trump.Value.SEVEN, "7");
            put(Trump.Value.EIGHT, "8");
            put(Trump.Value.NINE, "9");
            put(Trump.Value.TEN, "10");
            put(Trump.Value.JACK, "J");
            put(Trump.Value.QUEEN, "Q");
            put(Trump.Value.KING, "K");
        }};

        public static String format(final Trump trump) {
            return String.format("(%s%s)", suitMap.get(trump.suit), valueMap.get(trump.value));
        }
    }
}