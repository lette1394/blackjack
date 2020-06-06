package com.lette1394.blackjack;

import com.lette1394.blackjack.domain.Player;

public class InMemoryPlayerRepository implements PlayerRepository {
    @Override
    public Player find(final String playerId) {
        return new Player();
    }
}
