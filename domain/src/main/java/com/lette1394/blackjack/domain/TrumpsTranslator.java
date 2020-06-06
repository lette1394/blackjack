package com.lette1394.blackjack.domain;

@FunctionalInterface
public interface TrumpsTranslator<T> {
    T translate(Trumps trumps);
}
