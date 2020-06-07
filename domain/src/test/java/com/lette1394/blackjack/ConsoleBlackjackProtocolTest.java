package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import com.lette1394.blackjack.io.CannotParseBlackjackProtocolException;
import com.lette1394.blackjack.io.ConsoleBlackjackProtocol;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsoleBlackjackProtocolTest {

    @Test
    void protocolCanParseKeyAndValueContainingWhitespace() {
        final String rawInput = "playerId=dealer; command=join";
        final ConsoleBlackjackProtocol protocol = new ConsoleBlackjackProtocol(rawInput);

        assertThat(protocol.getPlayerId(), is("dealer"));
        assertThat(protocol.getCommand(), is("join"));
    }

    private static final String MANDATORY_FILED = "player";
    @Test
    void protocolThrowsExceptionWhenMandatoryKeyDoesNotExist() {
        final String rawInput = "hello=world";
        assertThat(rawInput, not(containsString(MANDATORY_FILED)));
        assertThrows(CannotParseBlackjackProtocolException.class, () -> new ConsoleBlackjackProtocol(rawInput));
    }

    @Test
    void protocolThrowsExceptionWhenParsingIllegalFormat() {
        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new ConsoleBlackjackProtocol("hello==world"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new ConsoleBlackjackProtocol("hello;;world"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new ConsoleBlackjackProtocol("hello=world;; hello=everyone"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new ConsoleBlackjackProtocol("hello=world hello=everyone"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new ConsoleBlackjackProtocol("hello=world; hello=; hello=everyone"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new ConsoleBlackjackProtocol("hello=world; =hello; hello=everyone"));
    }

    @Test
    void protocolThrowsExceptionWhenInputHasDuplicateKeys() {
        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new ConsoleBlackjackProtocol("hello=world; hello=everyone"));
    }
}