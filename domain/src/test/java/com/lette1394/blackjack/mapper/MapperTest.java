package com.lette1394.blackjack.mapper;

import org.junit.jupiter.api.Test;

import com.lette1394.blackjack.Trump;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapperTest {
    @Test
    void emojiSuitMapper() {
        Mapper<String, Trump.Suit> mapper = new ListAwareMapper<>(
                asList("♥️", "♠️", "♦️", "♣️"),
                asList(Trump.Suit.HEART, Trump.Suit.SPADE, Trump.Suit.DIAMOND, Trump.Suit.CLUB)
        );

        assertThat(mapper.map("♥️"), is(Trump.Suit.HEART));
        assertThat(mapper.map("♠️"), is(Trump.Suit.SPADE));
        assertThat(mapper.map("♦️"), is(Trump.Suit.DIAMOND));
        assertThat(mapper.map("♣️"), is(Trump.Suit.CLUB));
    }

    @Test
    void mismatchSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ListAwareMapper<>(
                    asList("♥️", "♠️", "♦️", "♣️", "additional-string"),
                    asList(Trump.Suit.HEART, Trump.Suit.SPADE, Trump.Suit.DIAMOND, Trump.Suit.CLUB)
            );
        });
    }

    @Test
    void unexpectedFrom() {
        final ListAwareMapper<String, Trump.Suit> mapper = new ListAwareMapper<>(
                asList("♥️", "♠️", "♦️", "♣️"),
                asList(Trump.Suit.HEART, Trump.Suit.SPADE, Trump.Suit.DIAMOND, Trump.Suit.CLUB)
        );

        assertThrows(IllegalArgumentException.class, () -> mapper.map("unexpected-string"));
    }
}