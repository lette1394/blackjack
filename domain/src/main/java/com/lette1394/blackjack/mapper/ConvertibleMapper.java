package com.lette1394.blackjack.mapper;

import java.util.function.Function;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConvertibleMapper<From, Middle, To> implements Mapper<From, To> {
    private final Function<From, Middle> converter;
    private final Mapper<Middle, To> mapper;

    @Override
    public boolean matches(final From from) {
        return mapper.matches(converter.apply(from));
    }

    @Override
    public To map(final From from) {
        return mapper.map(converter.apply(from));
    }
}
