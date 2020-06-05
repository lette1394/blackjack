package com.lette1394.blackjack;

import java.util.EventListener;

public interface BlackjackPlayerCommandListener extends EventListener {
    void join(final Player player);

    void hit(final Player player);

    void stay(final Player player);

    void cannotHandle(final String invalidInput);
}
