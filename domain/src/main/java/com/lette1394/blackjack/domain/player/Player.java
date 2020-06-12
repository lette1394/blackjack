package com.lette1394.blackjack.domain.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Player {
    private final String id;
    private final String name;

    @Getter
    @Setter
    @Accessors(chain = true)
    private int coins;

    public String getId() {
        return id;
    }

    public void bet(int coin) {
        throw new UnsupportedOperationException();
    }

    public void win() {
        throw new UnsupportedOperationException();
    }
}
