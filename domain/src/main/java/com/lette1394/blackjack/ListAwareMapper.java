package com.lette1394.blackjack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAwareMapper<From, To> implements Mapper<From, To> {
    private final Map<From, To> map;

    public ListAwareMapper(final List<From> from, final List<To> to) {
        if (from.size() != to.size()) {
            throw new IllegalArgumentException(
                    String.format("list size mismatch exception. from: %s, to: %s", from.size(), to.size()));
        }
        this.map = makeMap(from, to);
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

    private Map<From, To> makeMap(final List<From> from, final List<To> to) {
        final Map<From, To> map = new HashMap<>();
        for (int i = 0; i < from.size(); i++) {
            map.put(from.get(i), to.get(i));
        }
        return map;
    }
}
