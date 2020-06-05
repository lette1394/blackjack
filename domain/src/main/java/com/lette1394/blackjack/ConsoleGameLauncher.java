package com.lette1394.blackjack;

import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.ConsolePlayerInputGameOutput;
import com.lette1394.blackjack.ui.PlayerInputGameOutput;

@Slf4j
public class ConsoleGameLauncher {
    public static void main(String[] args) {
        final BlackjackPlayerInputTranslator blackjackPlayerInputTranslator = new BlackjackPlayerInputTranslator(new InMemoryPlayerRepository());
        final PlayerInputGameOutput playerInputGameOutput = new ConsolePlayerInputGameOutput(System.in, System.out);
        final BlackjackGameRunner runner = new BlackjackGameRunner(playerInputGameOutput,
                                                                   blackjackPlayerInputTranslator);

        runner.run();
    }
}
