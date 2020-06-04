package com.lette1394.blackjack;

import static com.lette1394.blackjack.Trump.Value.ACE;
import static com.lette1394.blackjack.Trump.Value.EIGHT;
import static com.lette1394.blackjack.Trump.Value.FIVE;
import static com.lette1394.blackjack.Trump.Value.FOUR;
import static com.lette1394.blackjack.Trump.Value.JACK;
import static com.lette1394.blackjack.Trump.Value.KING;
import static com.lette1394.blackjack.Trump.Value.NINE;
import static com.lette1394.blackjack.Trump.Value.QUEEN;
import static com.lette1394.blackjack.Trump.Value.SEVEN;
import static com.lette1394.blackjack.Trump.Value.SIX;
import static com.lette1394.blackjack.Trump.Value.TEN;
import static com.lette1394.blackjack.Trump.Value.THREE;
import static com.lette1394.blackjack.Trump.Value.TWO;

public class BlackjackTrumpsScoreCalculator implements TrumpsTranslator {
    private static final Mapper<Trump.Value, Integer> mapper;

    static {
        mapper = new HashMapBasedMapper<>() {{
            put(ACE, 1);
            put(TWO, 2);
            put(THREE, 3);
            put(FOUR, 4);
            put(FIVE, 5);
            put(SIX, 6);
            put(SEVEN, 7);
            put(EIGHT, 8);
            put(NINE, 9);
            put(TEN, 10);
            put(JACK, 10);
            put(QUEEN, 10);
            put(KING, 10);
        }};
    }

    @Override
    public int translate(final Trumps trumps) {
        final int defaultValue = computeDefaultValue(trumps);
        final int numberOfAce = computeNumberOfAce(trumps);

        return computeScoreClosestTo21(defaultValue, numberOfAce);
    }

    private int computeDefaultValue(final Trumps trumps) {
        return trumps.raw().stream()
                     .map(trump -> mapper.map(trump.value))
                     .reduce(0, Integer::sum);
    }

    private int computeNumberOfAce(final Trumps trumps) {
        return Math.toIntExact(trumps.raw().stream()
                                     .filter(trump -> trump.value.equals(ACE))
                                     .count());
    }

    private int computeScoreClosestTo21(final int defaultValue, final int numberOfAce) {

        return 0;
    }
}
