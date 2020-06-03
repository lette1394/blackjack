package com.lette1394.blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

public class TrumpFactory {
    private final List<MapperTo<Trump.Suit>> suitMappers = new ArrayList<>();
    private final List<MapperTo<Trump.Value>> valueMappers = new ArrayList<>();

    public TrumpFactory() {
        suitMappers.add(new EmojiSuitMapper());
        valueMappers.add(new IgnoreCaseStringValueMapper());
    }

    public Trump trump(final String rawSuit, final String rawValue) {
        return new Trump(parse(rawSuit, suitMappers, String.format("%s is not a emoji suit. Choose one of ♥️/♠️/♦️/♣️", rawSuit)),
                         parse(rawValue, valueMappers, String.format("%s is not a trump value. Choose one of Ace, 2~10, Jack, Queen, King", rawValue)));
    }

    private <T, MAPPER extends List<MapperTo<T>>> T parse(final String rawString,
                                                          final MAPPER mapper,
                                                          final String exceptionMessage) {
        return mapper.stream()
                     .filter(suitMapper -> suitMapper.matches(rawString))
                     .map(suitMapper -> suitMapper.map(rawString))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(exceptionMessage));
    }

    private interface MapperTo<T> {
        boolean matches(final String rawString);

        T map(final String rawString);
    }

    @RequiredArgsConstructor
    private static class EmojiSuitMapper implements MapperTo<Trump.Suit> {
        private final Map<String, Trump.Suit> map = new HashMap<>() {{
            put("♥️", Trump.Suit.HEART);
            put("♠️", Trump.Suit.SPADE);
            put("♦️", Trump.Suit.DIAMOND);
            put("♣️", Trump.Suit.CLUB);
        }};

        @Override
        public boolean matches(final String rawString) {
            return map.containsKey(rawString);
        }

        public Trump.Suit map(final String rawString) {
            return map.get(rawString);
        }
    }

    @RequiredArgsConstructor
    private static class IgnoreCaseStringValueMapper implements MapperTo<Trump.Value> {
        private final Map<String, Trump.Value> map = new HashMap<>() {{
            put("A", Trump.Value.ACE);
            put("ACE", Trump.Value.ACE);
            put("2", Trump.Value.TWO);
            put("3", Trump.Value.THREE);
            put("4", Trump.Value.FOUR);
            put("5", Trump.Value.FIVE);
            put("6", Trump.Value.SIX);
            put("7", Trump.Value.SEVEN);
            put("8", Trump.Value.EIGHT);
            put("9", Trump.Value.NINE);
            put("10", Trump.Value.TEN);
            put("J", Trump.Value.JACK);
            put("JACK", Trump.Value.JACK);
            put("Q", Trump.Value.QUEEN);
            put("QUEEN", Trump.Value.QUEEN);
            put("K", Trump.Value.KING);
            put("KING", Trump.Value.KING);
        }};

        @Override
        public boolean matches(final String rawString) {
            return map.containsKey(toUpperCase(rawString));
        }

        public Trump.Value map(final String rawString) {
            return map.get(toUpperCase(rawString));
        }

        private String toUpperCase(final String rawString) {
            return rawString.toUpperCase();
        }
    }
}
