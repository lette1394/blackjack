package com.lette1394.blackjack.runner;

import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.BlackjackPlayerInputTranslator;
import com.lette1394.blackjack.io.PlayerInputGameOutput;

@Slf4j
public class BlackjackGameRunner implements GameRunner {
    private final GameRunner gameRunner;

    public BlackjackGameRunner(final PlayerInputGameOutput playerInputGameOutput,
                               final BlackjackPlayerInputTranslator blackjackPlayerInputTranslator,
                               final long loopIntervalMillis) {
        this.gameRunner = new SingleThreadGameRunner(new InfiniteLoopGameRunner(() -> {
            try {
                String userInput = playerInputGameOutput.get();
                blackjackPlayerInputTranslator.translate(userInput);
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
