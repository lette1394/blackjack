package com.lette1394.blackjack.ui;

import java.io.Closeable;

public interface GameOutput extends Closeable {
    void send(Object output);
}
