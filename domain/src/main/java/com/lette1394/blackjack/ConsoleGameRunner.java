package com.lette1394.blackjack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.PlayerInputGameOutput;

@Slf4j
@RequiredArgsConstructor
public class ConsoleGameRunner implements GameRunner {
    private final PlayerInputGameOutput playerInputGameOutput;
    private final PlayerInputTranslator playerInputTranslator;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void run() {
        playerInputGameOutput.send("wait for player...");
        executorService.submit(this::runLoop);
    }

    private void runLoop() {
        while (true) {
            try {
                String userInput = playerInputGameOutput.get();
                playerInputTranslator.translate(userInput);
                Thread.sleep(50);
            } catch (Exception e) {
                log.error("unexpected error : " + e);
                e.printStackTrace();
                System.exit(99);
            }
        }
    }
}
