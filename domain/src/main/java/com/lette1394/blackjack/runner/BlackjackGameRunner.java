package com.lette1394.blackjack.runner;

import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.io.Input;
import com.lette1394.blackjack.io.InputProcessor;

@Slf4j
public class BlackjackGameRunner implements GameRunner {
    private final GameRunner gameRunner;

    public BlackjackGameRunner(final Input input,
                               final InputProcessor inputProcessor,
                               final long loopIntervalMillis) {
        this.gameRunner = new SingleThreadGameRunner(new InfiniteLoopGameRunner(() -> {
            try {
                inputProcessor.process(input.get());
                Thread.sleep(loopIntervalMillis);
            } catch (Exception e) {
                log.error("unexpected error : " + e);
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void run() {
        gameRunner.run();
    }

    @Override
    public void close() {
        gameRunner.close();
    }
}
