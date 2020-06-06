package com.lette1394.blackjack.runner;

import java.io.Closeable;

@FunctionalInterface
public interface GameRunner extends Closeable {
    void run();

    default void close() {
    }
}
