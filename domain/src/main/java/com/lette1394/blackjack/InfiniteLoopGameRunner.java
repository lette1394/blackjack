package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InfiniteLoopGameRunner implements GameRunner {
    private boolean isRunning = true;
    private final GameRunner gameRunner;

    @Override
    public void run() {
        while (true) {
            if (isRunning) {
                gameRunner.run();
                continue;
            }
            break;
        }
    }

    @Override
    public void close() {
        isRunning = false;
    }
}
