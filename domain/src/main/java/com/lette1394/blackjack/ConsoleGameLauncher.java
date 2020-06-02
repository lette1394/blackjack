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
        playerInputTranslator.addListener(new PlayerInputEventAdapter(runner));

        runner.run();
//        runner.setCardProvider(new ArrayDeque<>(Lists.newArrayList(new Trump("♦️", "2"), new Trump("♣️", "8"),
//                                                                   new Trump("♥️", "3"), new Trump("♠️", "9")))::poll);
    }
}
