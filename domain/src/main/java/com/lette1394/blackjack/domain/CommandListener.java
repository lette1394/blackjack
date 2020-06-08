package com.lette1394.blackjack.domain;

import java.util.EventListener;

import com.lette1394.blackjack.domain.player.Player;

public interface CommandListener extends EventListener {
    void onJoin(Player player);

    void onHit(Player player);

    void onStay(Player player);

    void onLeave(Player player);

    void onInvalidCommand(String rawInput);
}
