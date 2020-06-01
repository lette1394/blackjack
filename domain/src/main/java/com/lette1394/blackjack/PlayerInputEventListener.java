package com.lette1394.blackjack;

import java.util.EventListener;

public interface PlayerInputEventListener extends EventListener {
    void join();

    void stay();

    void cannotHandle(final String rawInput);
}
