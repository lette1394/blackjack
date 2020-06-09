package com.lette1394.blackjack.domain;

import java.util.EventListener;

import com.lette1394.blackjack.domain.player.Player;

public interface CommandListener extends EventListener {
    void onHistory(Player player);

    void onJoin(Player player);

    void onBet(Player player);

    void onHit(Player player);

    void onStay(Player player);

    void onRejoin(Player player);

    void onLeave(Player player);

    void onInvalidCommand(String rawInput);
}
