package com.lette1394.blackjack.io;

import java.io.Closeable;

public interface Output extends Closeable {
    void send(Object output);
}
