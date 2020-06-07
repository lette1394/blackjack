package com.lette1394.blackjack.domain.trump;

public class RandomTrumpProvider implements TrumpProvider {
    private final Trumps trumps;

    public RandomTrumpProvider() {
        this(1);
    }

    public RandomTrumpProvider(final int decks) {
        trumps = TrumpFactory.decks(decks, true)
                             .makeDrawReady();
    }

    @Override
    public Trump provide() {
         return trumps.pop();
    }
}
