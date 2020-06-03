package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InfiniteLoopGameRunner implements GameRunner {
    private final GameRunner gameRunner;

    @Override
    public void run() {
        while (true) {
            gameRunner.run();
        }
    }
}
