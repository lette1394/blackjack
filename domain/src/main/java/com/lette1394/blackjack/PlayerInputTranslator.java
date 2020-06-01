package com.lette1394.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerInputTranslator {
    private static final String COMMAND_JOIN = "join";
    private static final String COMMAND_STAY = "stay";
    private static final String COMMAND_HIT = "hit";

    // TODO: refactoring: 일급 컬렉션
    private final List<PlayerInputEventListener> listeners = new ArrayList<>();

    public void translate(final String playerInput) {
        if (playerInput.equals(COMMAND_JOIN)) {
            notify(PlayerInputEventListener::join);
        } else if (playerInput.equals(COMMAND_STAY)) {
            notify(PlayerInputEventListener::stay);
        } else if (playerInput.equals(COMMAND_HIT)) {
            notify(PlayerInputEventListener::hit);
        } else {
            notify(listener -> listener.cannotHandle(playerInput));
        }
    }

    public void addListener(final PlayerInputEventListener listener) {
        this.listeners.add(listener);
    }

    private void notify(final Consumer<PlayerInputEventListener> listenerConsumer) {
        for (PlayerInputEventListener listener : listeners) {
            listenerConsumer.accept(listener);
        }
    }
}
