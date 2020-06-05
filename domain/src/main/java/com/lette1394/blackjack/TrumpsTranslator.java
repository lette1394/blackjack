package com.lette1394.blackjack;

@FunctionalInterface
public interface TrumpsTranslator<T> {
    T translate(Trumps trumps);
}
