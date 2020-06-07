package com.lette1394.blackjack.io;

import java.io.Closeable;

public interface Input extends Closeable {
    String get();
}
