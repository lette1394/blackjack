package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackPlayerCommandListener extends EventListener {
    void join();

    void hit();

    void stay();

    void cannotHandle(final String playerInput);
}
