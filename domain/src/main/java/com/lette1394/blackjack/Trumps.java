package com.lette1394.blackjack;

import java.util.List;

import com.google.common.collect.Lists;

public class Trumps {
    private final List<Trump> trumps;

    public Trumps(final List<Trump> trumps) {
        this.trumps = trumps;
    }

    public Trumps(Trump... trumps) {
        this.trumps = Lists.newArrayList(trumps);
    }

    public int getScore() {
        return trumps.stream()
                     .map(trump -> trump.value)
                     .map(Integer::valueOf)
                     .reduce(0, Integer::sum);
    }

    public List<Trump> raw() {
        return trumps;
    }

    public void add(final Trump trump) {
        trumps.add(trump);
    }
}
