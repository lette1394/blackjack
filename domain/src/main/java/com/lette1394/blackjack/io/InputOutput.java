package com.lette1394.blackjack.io;

import lombok.SneakyThrows;

public interface InputOutput extends Input, Output {
    @SneakyThrows
    void close();
}
