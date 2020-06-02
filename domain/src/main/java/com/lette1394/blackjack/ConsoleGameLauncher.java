package com.lette1394.blackjack;

import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.ConsoleUserInputOutput;
import com.lette1394.blackjack.ui.UserInputOutput;

@Slf4j
public class ConsoleGameLauncher {
    public static void main(String[] args) {
        final PlayerInputTranslator playerInputTranslator = new PlayerInputTranslator();
        final UserInputOutput userInputOutput = new ConsoleUserInputOutput(System.in, System.out);
        final ConsoleGameRunner runner = new ConsoleGameRunner(userInputOutput, playerInputTranslator);
        playerInputTranslator.addListener(new PlayerInputEventAdapter(runner));

        runner.run();
//        runner.setCardProvider(new ArrayDeque<>(Lists.newArrayList(new Trump("♦️", "2"), new Trump("♣️", "8"),
//                                                                   new Trump("♥️", "3"), new Trump("♠️", "9")))::poll);
    }
}
