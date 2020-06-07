package com.lette1394.blackjack.domain;

import java.util.EventListener;

import com.lette1394.blackjack.domain.player.Player;

public interface BlackjackPlayerCommandListener extends EventListener {
    void join(Player player);

    void hit(Player player);

    void stay(Player player);

    void cannotHandle(String invalidInput);
}
