package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackGameEventListener extends EventListener {
    void join();

    void hit();

    void stay();
}
