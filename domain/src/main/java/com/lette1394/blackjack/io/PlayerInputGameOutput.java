package com.lette1394.blackjack.io;

import lombok.SneakyThrows;

public interface PlayerInputGameOutput extends PlayerInput, GameOutput {
    @SneakyThrows
    void close();
}
