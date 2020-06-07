package com.lette1394.blackjack.event;

import java.util.EventListener;

public interface ListenersAware<T extends EventListener> {
    void addListener(T listener);
}
