package com.lette1394.blackjack;

public class InMemoryPlayerRepository implements PlayerRepository {
    @Override
    public Player find(final String playerId) {
        return new Player();
    }
}
