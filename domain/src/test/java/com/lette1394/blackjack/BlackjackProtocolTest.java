package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BlackjackProtocolTest {

    @Test
    void protocolCanParseKeyAndValueContainingWhitespace() {
        final String rawInput = "player=dealer; command=join";
        final BlackjackProtocol protocol = new BlackjackProtocol(rawInput);

        assertThat(protocol.getPlayer(), is("dealer"));
        assertThat(protocol.getCommand(), is("join"));
    }

    private static final String MANDATORY_FILED = "player";
    @Test
    void protocolThrowsExceptionWhenMandatoryKeyDoesNotExist() {
        final String rawInput = "hello=world";
        assertThat(rawInput, not(containsString(MANDATORY_FILED)));
        assertThrows(CannotParseBlackjackProtocolException.class, () -> new BlackjackProtocol(rawInput));
    }

    @Test
    void protocolThrowsExceptionWhenParsingIllegalFormat() {
        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new BlackjackProtocol("hello==world"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new BlackjackProtocol("hello;;world"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new BlackjackProtocol("hello=world;; hello=everyone"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new BlackjackProtocol("hello=world hello=everyone"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new BlackjackProtocol("hello=world; hello=; hello=everyone"));

        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new BlackjackProtocol("hello=world; =hello; hello=everyone"));
    }

    @Test
    void protocolThrowsExceptionWhenInputHasDuplicateKeys() {
        assertThrows(CannotParseBlackjackProtocolException.class,
                     () -> new BlackjackProtocol("hello=world; hello=everyone"));
    }
}