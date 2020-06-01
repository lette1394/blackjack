package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerInputTranslator {
    private static final String COMMAND_JOIN = "join";
    private static final String COMMAND_STAY = "stay";
    private static final String COMMAND_HIT = "hit";

    private final PlayerInputEventListener listener;

    public void translate(final String playerInput) {
        if (playerInput.equals(COMMAND_JOIN)) {
            listener.join();
        } else if (playerInput.equals(COMMAND_STAY)) {
            listener.stay();
        } else {
            listener.cannotHandle(playerInput);
        }
    }
}


