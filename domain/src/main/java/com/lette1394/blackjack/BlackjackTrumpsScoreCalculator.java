package com.lette1394.blackjack;

import com.lette1394.blackjack.domain.Trump;
import com.lette1394.blackjack.domain.Trumps;
import com.lette1394.blackjack.domain.TrumpsTranslator;
import com.lette1394.blackjack.mapper.HashMapBasedMapper;
import com.lette1394.blackjack.mapper.Mapper;

import static com.lette1394.blackjack.domain.Trump.Value.ACE;
import static com.lette1394.blackjack.domain.Trump.Value.EIGHT;
import static com.lette1394.blackjack.domain.Trump.Value.FIVE;
import static com.lette1394.blackjack.domain.Trump.Value.FOUR;
import static com.lette1394.blackjack.domain.Trump.Value.JACK;
import static com.lette1394.blackjack.domain.Trump.Value.KING;
import static com.lette1394.blackjack.domain.Trump.Value.NINE;
import static com.lette1394.blackjack.domain.Trump.Value.QUEEN;
import static com.lette1394.blackjack.domain.Trump.Value.SEVEN;
import static com.lette1394.blackjack.domain.Trump.Value.SIX;
import static com.lette1394.blackjack.domain.Trump.Value.TEN;
import static com.lette1394.blackjack.domain.Trump.Value.THREE;
import static com.lette1394.blackjack.domain.Trump.Value.TWO;

public class BlackjackTrumpsScoreCalculator implements TrumpsTranslator<Integer> {
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
    public Integer translate(final Trumps trumps) {
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

    private int computeScoreClosestTo21(final int initialValue, final int numberOfAce) {
        if (initialValue == 21) {
            return 21;
        }

        int ret = initialValue;
        int value = initialValue;
        int prevDiff = 21 - initialValue;
        for (int i = 0; i < numberOfAce; i++) {
            // Ace는 11점 혹은 1점으로 계산될 수 있다.
            // 기본 값이 1점으로 이미 계산 되었으므로, (11-1)점씩 더해본다.
            value += 10;
            int currentDiff = Math.abs(21 - value);

            if (prevDiff > currentDiff && value <= 21) {
                ret = value;
            }
        }

        return ret;
    }
}
