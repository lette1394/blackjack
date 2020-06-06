package com.lette1394.blackjack.ui;

import lombok.SneakyThrows;

public interface PlayerInputGameOutput extends PlayerInput, GameOutput {
    @SneakyThrows
    void close();
}
