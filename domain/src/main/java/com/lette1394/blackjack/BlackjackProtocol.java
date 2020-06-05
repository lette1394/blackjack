package com.lette1394.blackjack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// blackjack protocol:
// player=<playerId>; command=<command>
public class BlackjackProtocol {
    private final Map<String, String> map = new HashMap<>();

    public BlackjackProtocol(final String rawInput) {
        parse(rawInput);
    }

    private void parse(final String rawInput) {
        if (Objects.isNull(rawInput) || rawInput.length() == 0) {
            throw new IllegalArgumentException();
        }

        final String[] split = rawInput.split(";");
        if (split.length == 0) {
            throw new CannotParseBlackjackProtocolException(String.format("cannot parse raw input: %s", rawInput));
        }

        for (final String pair : split) {
            final String[] keyValue = pair.split("=");
            if (keyValue.length != 2) {
                throw new CannotParseBlackjackProtocolException(String.format(
                        "Blackjack protocol must have the format: <key>=<value>, but: %s",
                        Arrays.toString(keyValue)));
            }
            map.put(keyValue[0], keyValue[1]);
        }

        if (map.containsKey("player") == false) {
            throw new CannotParseBlackjackProtocolException("Blackjack protocol must have the key: PLAYER");
        }
    }

    public String getPlayer() {
        throw new UnsupportedOperationException();
    }
}
