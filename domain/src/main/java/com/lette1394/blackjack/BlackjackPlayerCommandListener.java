package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackPlayerCommandListener extends EventListener {
    void join(final String playerId);

    void hit(final String playerId);

    void stay(final String playerId);

    void cannotHandle(final String invalidInput);
}
