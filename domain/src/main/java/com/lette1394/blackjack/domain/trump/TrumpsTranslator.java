package com.lette1394.blackjack.domain.trump;

@FunctionalInterface
public interface TrumpsTranslator<T> {
    T translate(Trumps trumps);
}
