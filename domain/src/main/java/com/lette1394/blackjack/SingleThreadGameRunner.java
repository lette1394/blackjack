package com.lette1394.blackjack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SingleThreadGameRunner implements GameRunner {
    private final GameRunner gameRunner;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void run() {
        executorService.submit(gameRunner::run);
    }

    @Override
    public void close() {
        gameRunner.close();
        executorService.shutdown();
    }
}
