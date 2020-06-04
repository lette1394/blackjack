package com.lette1394.blackjack;

import java.util.HashMap;
import java.util.Map;

public class HashMapBasedMapper<From, To> implements Mapper<From, To> {
    private final Map<From, To> map = new HashMap<>();

    void put(From key, To value) {
        if (map.containsKey(key)) {
            throw new IllegalArgumentException(String.format("key:[%s] is already in this mapper. key:[%s], value[%s]",
                                                             key, key, value));
        }
        map.put(key, value);
    }

    @Override
    public boolean matches(final From from) {
        return map.containsKey(from);
    }

    @Override
    public To map(final From from) {
        if (matches(from)) {
            return map.get(from);
        }
        throw new IllegalArgumentException(String.format("%s does not exists in this mapper", from));
    }
}
