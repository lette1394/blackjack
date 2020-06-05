package com.lette1394.blackjack;

import java.util.List;

import com.google.common.collect.Lists;

public class Trumps {
    final List<Trump> trumps;

    public Trumps(final List<Trump> trumps) {
        this.trumps = trumps;
    }

    public Trumps(final Trump... trumps) {
        this.trumps = Lists.newArrayList(trumps);
    }

    public int computeScore() {
        return compute(new BlackjackTrumpsScoreCalculator());
    }

    public <T> T compute(final TrumpsTranslator<T> translator) {
        return translator.translate(this);
    }

    public List<Trump> raw() {
        return trumps;
    }

    public void add(final Trump trump) {
        trumps.add(trump);
    }
}
