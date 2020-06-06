package com.lette1394.blackjack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.PlayerInputGameOutput;

@Slf4j
@RequiredArgsConstructor
public class BlackjackGameRunner implements GameRunner {
    private final PlayerInputGameOutput playerInputGameOutput;
    private final BlackjackPlayerInputTranslator blackjackPlayerInputTranslator;

    @Override
    public void run() {
        playerInputGameOutput.send("wait for player...");

        new SingleThreadGameRunner(new InfiniteLoopGameRunner(() -> {
            try {
                String userInput = playerInputGameOutput.get();
                blackjackPlayerInputTranslator.translate(userInput);
                Thread.sleep(50);
            } catch (Exception e) {
                log.error("unexpected error : " + e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        })).run();
    }
}
