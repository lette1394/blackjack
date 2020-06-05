package com.lette1394.blackjack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;

// blackjack protocol:
// <key1>=<value1>; <key2>=<value2>
public class BlackjackProtocol {
    private static final String PAIR_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";

    private static final String PLAYER = "playerId";
    private static final String COMMAND = "command";

    private static final Set<String> MANDATORY_KEYS = Sets.newHashSet(PLAYER, COMMAND);

    private final Map<String, String> map = new HashMap<>();

    public BlackjackProtocol(final String rawInput) {
        parse(rawInput);
    }

    private void parse(final String rawInput) {
        putAllPairs(rawInput);
        checkMandatoryKeys();
    }

    private void putAllPairs(final String rawInput) {
        final String[] pairs = checkPairsThenReturn(rawInput);
        for (final String rawPair : pairs) {
            final String[] keyValue = checkKeyValueThenReturn(rawPair);
            map.put(keyValue[0], keyValue[1]);
        }
    }

    private void checkMandatoryKeys() {
        final Set<String> skippedKeys = new HashSet<>();
        for (final String mandatoryKey : MANDATORY_KEYS) {
            if (map.containsKey(mandatoryKey)) {
                continue;
            }
            skippedKeys.add(mandatoryKey);
        }

        if (skippedKeys.size() > 0) {
            throw new CannotParseBlackjackProtocolException(String.format("Blackjack protocol must have the key: %s",
                                                                          Arrays.toString(skippedKeys.toArray())));
        }
    }

    private String[] checkPairsThenReturn(final String rawInput) {
        if (Objects.isNull(rawInput) || rawInput.length() == 0) {
            throw new IllegalArgumentException();
        }

        final String[] pairs = trim(rawInput.split(PAIR_DELIMITER));
        if (pairs.length == 0) {
            throw new CannotParseBlackjackProtocolException(String.format("input does not have delimiter: '%s', but %s",
                                                                          PAIR_DELIMITER, rawInput));
        }
        return pairs;
    }

    private String[] checkKeyValueThenReturn(final String rawPair) {
        final String[] keyValue = trim(rawPair.split(KEY_VALUE_DELIMITER));
        if (keyValue.length != 2) {
            throw new CannotParseBlackjackProtocolException(String.format(
                    "Blackjack protocol must have the format: <key>=<value>, but: %s",
                    Arrays.toString(keyValue)));
        }
        return keyValue;
    }

    private String[] trim(final String[] strs) {
        return Arrays.stream(strs)
                     .map(String::trim)
                     .toArray(String[]::new);
    }

    public String getPlayerId() {
        return map.get(PLAYER);
    }

    public String getCommand() {
        return map.get(COMMAND);
    }
}
