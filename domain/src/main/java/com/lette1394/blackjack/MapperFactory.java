package com.lette1394.blackjack;

import java.util.List;
import java.util.function.Function;

public final class MapperFactory {
    public static <From, To> Mapper<From, To> list(final List<From> from, final List<To> to) {
        return new ListAwareMapper<>(from, to);
    }

    public static <From, Middle, To> Mapper<From, To> convert(final Function<From, Middle> converter,
                                                              final Mapper<Middle, To> mapper) {
        return new ConvertibleMapper<From, Middle, To>(converter, mapper);
    }
}
