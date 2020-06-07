package com.lette1394.blackjack.domain.player;

public interface PlayerRepository {
    Player find(String playerId);
}
