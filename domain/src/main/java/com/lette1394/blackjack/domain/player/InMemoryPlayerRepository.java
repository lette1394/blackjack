package com.lette1394.blackjack.domain.player;

public class InMemoryPlayerRepository implements PlayerRepository {
    @Override
    public Player find(final String playerId) {
        return new Player("");
    }
}
