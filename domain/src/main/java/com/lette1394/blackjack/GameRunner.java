package com.lette1394.blackjack;

import java.io.Closeable;

@FunctionalInterface
public interface GameRunner extends Closeable {
    void run();

    default void close() {
    }
}
