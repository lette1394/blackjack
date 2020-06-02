package com.lette1394.blackjack;

import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.ConsolePlayerInputGameOutput;
import com.lette1394.blackjack.ui.PlayerInputGameOutput;

@Slf4j
public class ConsoleGameLauncher {
    public static void main(String[] args) {
        final PlayerInputTranslator playerInputTranslator = new PlayerInputTranslator();
        final PlayerInputGameOutput playerInputGameOutput = new ConsolePlayerInputGameOutput(System.in, System.out);
        final ConsoleGameRunner runner = new ConsoleGameRunner(playerInputGameOutput, playerInputTranslator);

        runner.run();
    }
}
