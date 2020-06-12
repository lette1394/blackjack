package com.lette1394.blackjack.domain.player;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Player {
    private final String id;
    private final String name;

    public String getId() {
        return id;
    }

    public int getCoins() {
        return 900;
    }
}
