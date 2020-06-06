package com.lette1394.blackjack.mapper;

public interface Mapper<From, To> {
    boolean matches(From from);

    To map(From from);
}
