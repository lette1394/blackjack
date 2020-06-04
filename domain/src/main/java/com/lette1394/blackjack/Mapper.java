package com.lette1394.blackjack;

public interface Mapper<From, To> {
    boolean matches(final From from);

    To map(final From from);
}
