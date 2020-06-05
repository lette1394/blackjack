package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BlackjackProtocolTest {

    @Test
    void test1() {
        final String rawInput = "player=dealer; command=join";
        final BlackjackProtocol protocol = new BlackjackProtocol(rawInput);

        assertThat(protocol.getPlayer(), is("dealer"));
    }
}