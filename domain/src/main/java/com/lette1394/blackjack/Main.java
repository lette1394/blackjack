package com.lette1394.blackjack;

import com.lette1394.blackjack.domain.player.InMemoryPlayerRepository;
import com.lette1394.blackjack.io.ConsoleInvalidPlayerInputHandler;
import com.lette1394.blackjack.runner.BlackjackGameLauncher;
import com.lette1394.blackjack.io.ConsolePlayerInputGameOutput;
import com.lette1394.blackjack.io.StringFormatTemplateInputGameOutput;

public class Main {
    public static void main(String[] args) {
        console().launch();
    }

    private static BlackjackGameLauncher console() {
        final ConsolePlayerInputGameOutput consolePlayerInputGameOutput = new ConsolePlayerInputGameOutput(System.in,
                                                                                                           System.out);
        return BlackjackGameLauncher
                       .builder()
                       .dealerStopScoreInclusive(17)
                       .invalidBlackjackPlayerCommandListener(new ConsoleInvalidPlayerInputHandler(consolePlayerInputGameOutput))
                       .playerInputGameOutput(new StringFormatTemplateInputGameOutput("playerId=0000; command=%s", consolePlayerInputGameOutput))
                       .playerRepository(new InMemoryPlayerRepository())
                       .loopIntervalMillis(100)
                       .build();
    }
}
