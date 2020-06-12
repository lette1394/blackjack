package com.lette1394.blackjack.domain.player;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Account {
    private final int coin;

    public int getCoin() {
        return coin;
    }

    public void won() {

    }
}
