package com.lette1394.blackjack;

import com.lette1394.blackjack.domain.Player;

public interface PlayerRepository {
    Player find(String playerId);
}
