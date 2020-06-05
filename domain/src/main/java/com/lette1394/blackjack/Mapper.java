package com.lette1394.blackjack;

public interface Mapper<From, To> {
    boolean matches(From from);

    To map(From from);
}
