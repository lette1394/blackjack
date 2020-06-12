package com.lette1394.blackjack.io;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import com.lette1394.blackjack.domain.BlackjackEventListener;
import com.lette1394.blackjack.domain.BlackjackGameState;
import com.lette1394.blackjack.domain.GameWinner;
import com.lette1394.blackjack.domain.player.Player;
import com.lette1394.blackjack.domain.trump.Trump;
import com.lette1394.blackjack.domain.trump.Trumps;

@RequiredArgsConstructor
public class ConsoleOutput implements BlackjackEventListener {
    private final Output output;

    @Override
    public void onGameStateChanged(final BlackjackGameState snapshot) {
        if (snapshot.isDrawing()) {
            send("new blackjack game start");
            return;
        }
        if (snapshot.isFinishing()) {
            send("game ended");
            return;
        }
    }

    @Override
    public void onNewPlayerJoin(final Player player) {
        send(String.format("playerId:[%s] joined", player.getId()));
        send(String.format("Your coins: %s", player.getCoins()));
    }

    @Override
    public void onPlayerBetting(final Player player) {
        send(String.format("Your coins: %s", player.getCoins()));
    }

    @Override
    public void onPlayerHandChanged(final Trumps trumps) {
        send("Your Cards: " + formatTrump(trumps));
    }

    @Override
    public void onDealerHandChanged(final int numberOfCards, final Trumps trumps) {
        send("Dealer's Cards: " + formatTrump(trumps, numberOfCards));
    }

    @Override
    public void onDealerTurnEnds(final Trumps trumps) {
        final int score = trumps.computeScore();
        if (score > 21) {
            send("Dealer got BUST. score: " + trumps.computeScore());
            return;
        }
        send("Dealer's Score: " + trumps.computeScore());
    }

    @Override
    public void onPlayerTurnEnds(final Trumps trumps) {
        final int score = trumps.computeScore();
        if (score > 21) {
            send("You got BUST. score: " + trumps.computeScore());
            return;
        }
        send("Your Score: " + trumps.computeScore());
    }

    @Override
    public void onShowWinner(final Player player, final GameWinner winner) {
        if (winner == GameWinner.PLAYER) {
            send("You WIN");
        } else if (winner == GameWinner.DEALER) {
            send("You LOSE");
        } else {
            send("Game DRAW");
        }

        send("Your coins: " + player.getCoins());
        if (player.getCoins() == 0) {
            send("GAME OVER. You got bankrupt.");
        } else {
            send("Another game? [join|leave]");
        }
    }

    @Override
    public void onIllegalCommand(final String message) {
        send(message);
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
        return sb.substring(0, sb.toString().length() - 1);
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
