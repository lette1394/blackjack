package com.lette1394.blackjack.runner;

import lombok.Builder;

import com.lette1394.blackjack.domain.BlackjackGame;
import com.lette1394.blackjack.io.ConsoleOutput;
import com.lette1394.blackjack.domain.InvalidCommandListener;
import com.lette1394.blackjack.domain.player.PlayerRepository;
import com.lette1394.blackjack.domain.trump.RandomTrumpProvider;
import com.lette1394.blackjack.io.ConsoleInputTranslator;
import com.lette1394.blackjack.io.InputOutput;

public class BlackjackGameLauncher {
    private final PlayerRepository playerRepository;
    private final InputOutput inputOutput;
    private final InvalidCommandListener invalidBlackjackPlayerCommandListener;
    private final int dealerStopScoreInclusive;
    private final int loopIntervalMillis;

    @Builder
    public BlackjackGameLauncher(final PlayerRepository playerRepository,
                                 final InputOutput inputOutput,
                                 final InvalidCommandListener invalidBlackjackPlayerCommandListener,
                                 final int dealerStopScoreInclusive,
                                 final int loopIntervalMillis) {
        this.playerRepository = playerRepository;
        this.inputOutput = inputOutput;
        this.invalidBlackjackPlayerCommandListener = invalidBlackjackPlayerCommandListener;
        this.dealerStopScoreInclusive = dealerStopScoreInclusive;
        this.loopIntervalMillis = loopIntervalMillis;
    }

    public void launch() {
        final ConsoleInputTranslator consoleInputTranslator = new ConsoleInputTranslator(playerRepository);
        final BlackjackGameRunner runner = new BlackjackGameRunner(inputOutput,
                                                                   consoleInputTranslator, loopIntervalMillis);

        BlackjackGame blackjackGame = new BlackjackGame(dealerStopScoreInclusive, new RandomTrumpProvider());
        blackjackGame.addListener(new ConsoleOutput(inputOutput));
        blackjackGame.addListener(new ShutDownHook(inputOutput, runner));

        consoleInputTranslator.addListener(blackjackGame);
        consoleInputTranslator.addListener(invalidBlackjackPlayerCommandListener);

        runner.run();
    }
}
