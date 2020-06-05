package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackPlayerCommandListener extends EventListener {
    void join(Player player);

    void hit(Player player);

    void stay(Player player);

    void cannotHandle(String invalidInput);
}
