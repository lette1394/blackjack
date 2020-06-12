package com.lette1394.blackjack.domain.player;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPlayerRepository implements PlayerRepository {
    private final Map<String, Player> map = new HashMap<>();

    @Override
    public Player find(final String playerId) {
        if (map.containsKey(playerId)) {
            return map.get(playerId);
        }
        final Player player = new Player(playerId, "name-1").setCoins(1000);
        map.put(player.getId(), player);

        return player;
    }
}
