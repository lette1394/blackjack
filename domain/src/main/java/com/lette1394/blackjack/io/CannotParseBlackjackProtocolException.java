package com.lette1394.blackjack.io;

public class CannotParseBlackjackProtocolException extends RuntimeException {
    public CannotParseBlackjackProtocolException(final String rawInput) {
        super(rawInput);
    }
}
