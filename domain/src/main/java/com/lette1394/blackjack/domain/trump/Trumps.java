package com.lette1394.blackjack.domain.trump;

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

    public void add(final Trumps trumps) {
        this.trumps.addAll(trumps.raw());
    }

    public int size() {
        return trumps.size();
    }

    public Trump pop() {
        return trumps.remove(0);
    }

    Trumps makeDrawReady() {
        return new DrawReadyTrumps(trumps);
    }
}
