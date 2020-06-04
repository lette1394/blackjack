package com.lette1394.blackjack;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TrumpFactory {
    private static final List<Mapper<String, Trump.Suit>> suitMappers = new ArrayList<>();
    private static final List<Mapper<String, Trump.Value>> valueMappers = new ArrayList<>();

    static {
        suitMappers.add(emojiSuitMapper());
        suitMappers.add(ignoreCaseStringSuitMapper());

        valueMappers.add(ignoreCaseStringValueMapper());
    }

    public static Trump trump(final String rawSuit, final String rawValue) {
        return new Trump(parse(rawSuit,
                               suitMappers,
                               String.format("%s is not a emoji suit. Choose one of ♥️/♠️/♦️/♣️", rawSuit)),
                         parse(rawValue,
                               valueMappers,
                               String.format("%s is not a trump value. Choose one of Ace, 2~10, Jack, Queen, King",
                                             rawValue)));
    }

    private static <From, To, MAPPERS extends List<Mapper<From, To>>> To parse(final From from,
                                                                               final MAPPERS mappers,
                                                                               final String exceptionMessage) {
        return mappers.stream()
                      .filter(suitMapper -> suitMapper.matches(from))
                      .map(suitMapper -> suitMapper.map(from))
                      .findFirst()
                      .orElseThrow(() -> new IllegalArgumentException(exceptionMessage));
    }

    private static Mapper<String, Trump.Suit> emojiSuitMapper() {
        return new HashMapBasedMapper<>() {{
            put("♥️", Trump.Suit.HEART);
            put("♠️", Trump.Suit.SPADE);
            put("♦️", Trump.Suit.DIAMOND);
            put("♣️", Trump.Suit.CLUB);
        }};
    }

    private static Mapper<String, Trump.Suit> ignoreCaseStringSuitMapper() {
        final HashMapBasedMapper<String, Trump.Suit> rawMapper = new HashMapBasedMapper<>() {{
            put("HEART", Trump.Suit.HEART);
            put("SPADE", Trump.Suit.SPADE);
            put("DIAMOND", Trump.Suit.DIAMOND);
            put("CLUB", Trump.Suit.CLUB);
        }};
        return MapperFactory.convert(String::toUpperCase, rawMapper);
    }

    private static Mapper<String, Trump.Value> ignoreCaseStringValueMapper() {
        final HashMapBasedMapper<String, Trump.Value> rawMapper = new HashMapBasedMapper<>() {{
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
        return MapperFactory.convert(String::toUpperCase, rawMapper);
    }
}
