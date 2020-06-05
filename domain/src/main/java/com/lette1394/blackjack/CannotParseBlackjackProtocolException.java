package com.lette1394.blackjack;

public class CannotParseBlackjackProtocolException extends RuntimeException {
    public CannotParseBlackjackProtocolException(final String rawInput) {
        super(rawInput);
    }
}
