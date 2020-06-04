package com.lette1394.blackjack;

import java.util.List;

public class BlackjackTrumps extends Trumps {
    public BlackjackTrumps(final List<Trump> trumps) {
        super(trumps);
    }

    public BlackjackTrumps(final Trump... trumps) {
        super(trumps);
    }

    @Override
    public int getScore() {
        return getAvailableMaxScore();
    }

    private int getAvailableMaxScore() {
        long numberOfAce = trumps.stream()
                                 .filter(trump -> trump.value.equals(Trump.Value.ACE))
                                 .count();

        int defaultValue = trumps.stream()
                                 .map(Trump::getScore)
                                 .reduce(0, Integer::sum);

        if (numberOfAce > 0) {
            defaultValue += 10;
        }
        return defaultValue;
    }
}
