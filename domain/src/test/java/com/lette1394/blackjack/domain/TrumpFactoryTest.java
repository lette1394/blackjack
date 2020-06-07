package com.lette1394.blackjack.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.lette1394.blackjack.domain.trump.Trump;
import com.lette1394.blackjack.domain.trump.TrumpFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("TrumpFactory can make")
class TrumpFactoryTest {
    @Nested
    @DisplayName("emoji suit with")
    class EmojiSuit {
        @Test
        @DisplayName("Five")
        void numericValue() {
            final Trump trump = TrumpFactory.trump("♦️", "5");
            assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.FIVE)));
        }

        @Test
        @DisplayName("Ace")
        void aceValue() {
            final Trump trump = TrumpFactory.trump("♦️", "Ace");
            assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.ACE)));
        }

        @Test
        @DisplayName("Jack")
        void jackValue() {
            final Trump trump = TrumpFactory.trump("♦️", "Jack");
            assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.JACK)));
        }

        @Test
        @DisplayName("Queen")
        void queenValue() {
            final Trump trump = TrumpFactory.trump("♦️", "Queen");
            assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.QUEEN)));
        }

        @Test
        @DisplayName("King")
        void kingValue() {
            final Trump trump = TrumpFactory.trump("♦️", "King");
            assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.KING)));
        }
    }

    @Nested
    @DisplayName("string suit with")
    class StringSuit {
        @Test
        @DisplayName("Five")
        void numericValue() {
            final Trump trump = TrumpFactory.trump("Diamond", "5");
            assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.FIVE)));
        }

        @Test
        @DisplayName("Ace")
        void aceValue() {
            final Trump trump = TrumpFactory.trump("Spade", "Ace");
            assertThat(trump, is(new Trump(Trump.Suit.SPADE, Trump.Value.ACE)));
        }

        @Test
        @DisplayName("Jack")
        void jackValue() {
            final Trump trump = TrumpFactory.trump("Club", "Jack");
            assertThat(trump, is(new Trump(Trump.Suit.CLUB, Trump.Value.JACK)));
        }

        @Test
        @DisplayName("Queen")
        void queenValue() {
            final Trump trump = TrumpFactory.trump("Heart", "Queen");
            assertThat(trump, is(new Trump(Trump.Suit.HEART, Trump.Value.QUEEN)));
        }

        @Test
        @DisplayName("King")
        void kingValue() {
            final Trump trump = TrumpFactory.trump("Diamond", "King");
            assertThat(trump, is(new Trump(Trump.Suit.DIAMOND, Trump.Value.KING)));
        }
    }
}