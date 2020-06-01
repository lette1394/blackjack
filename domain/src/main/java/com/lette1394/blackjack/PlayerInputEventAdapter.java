package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerInputEventAdapter implements PlayerInputEventListener {
    private final GameEventListener listener;

    @Override
    public void join() {
        listener.join();
    }

    @Override
    public void hit() {
        listener.hit();
    }

    @Override
    public void stay() {
        listener.stay();
    }

    @Override
    public void cannotHandle(final String rawInput) {
        throw new IllegalArgumentException(rawInput);
    }
}
