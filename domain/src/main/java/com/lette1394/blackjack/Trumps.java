package com.lette1394.blackjack;

import java.util.List;

import com.google.common.collect.Lists;

public class Trumps {
    protected final List<Trump> trumps;

    public Trumps(final List<Trump> trumps) {
        this.trumps = trumps;
    }

    public Trumps(final Trump... trumps) {
        this.trumps = Lists.newArrayList(trumps);
    }

    public int getScore() {
        return 0;
    }

    public List<Trump> raw() {
        return trumps;
    }

    public void add(final Trump trump) {
        trumps.add(trump);
    }
}
