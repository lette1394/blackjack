package com.lette1394.blackjack;

import com.lette1394.blackjack.domain.player.InMemoryPlayerRepository;
import com.lette1394.blackjack.io.ConsoleHelloMessageInputOutputAdapter;
import com.lette1394.blackjack.io.ConsoleInvalidCommandListener;
import com.lette1394.blackjack.runner.BlackjackGameLauncher;
import com.lette1394.blackjack.io.ConsoleInputOutput;
import com.lette1394.blackjack.io.StringFormatTemplateInputOutputAdapter;

public class Main {
    public static void main(String[] args) {
        console().launch();
    }

    private static BlackjackGameLauncher console() {
        final ConsoleInputOutput consoleInputGameOutput = new ConsoleInputOutput(System.in,
                                                                                 System.out);
        return BlackjackGameLauncher
                       .builder()
                       .dealerStopScoreInclusive(17)
                       .invalidBlackjackPlayerCommandListener(new ConsoleInvalidCommandListener(consoleInputGameOutput))
                       .inputOutput(new ConsoleHelloMessageInputOutputAdapter("input [join]",
                                                                              new StringFormatTemplateInputOutputAdapter(
                                                                                      "playerId=0000; command=%s",
                                                                                      consoleInputGameOutput)))
                       .playerRepository(new InMemoryPlayerRepository())
                       .loopIntervalMillis(100)
                       .build();
    }
}
