package com.lette1394.blackjack;

import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.PlayerInputGameOutput;

@Slf4j
public class BlackjackGameRunner implements GameRunner {
    private final PlayerInputGameOutput playerInputGameOutput;
    private final GameRunner gameRunner;

    public BlackjackGameRunner(final PlayerInputGameOutput playerInputGameOutput,
                               final BlackjackPlayerInputTranslator blackjackPlayerInputTranslator) {
        this.playerInputGameOutput = playerInputGameOutput;
        this.gameRunner = new SingleThreadGameRunner(new InfiniteLoopGameRunner(() -> {
            try {
                String userInput = playerInputGameOutput.get();
                blackjackPlayerInputTranslator.translate(userInput);
                Thread.sleep(50);
            } catch (Exception e) {
                log.error("unexpected error : " + e);
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void run() {
        playerInputGameOutput.send("wait for player...");
        gameRunner.run();
    }

    @Override
    public void close() {
        gameRunner.close();
    }
}
