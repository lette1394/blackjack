package com.lette1394.blackjack.io;

import java.io.Closeable;

public interface PlayerInput extends Closeable {
    String get();
}
