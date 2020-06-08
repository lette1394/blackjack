package com.lette1394.blackjack.runner;

import lombok.Builder;

import com.lette1394.blackjack.domain.BlackjackGame;
import com.lette1394.blackjack.domain.InvalidCommandListener;
import com.lette1394.blackjack.domain.player.PlayerRepository;
import com.lette1394.blackjack.domain.trump.RandomTrumpProvider;
import com.lette1394.blackjack.io.ConsoleInputProcessor;
import com.lette1394.blackjack.io.ConsoleOutput;
import com.lette1394.blackjack.io.InputOutput;

public class BlackjackGameLauncher {
    private final PlayerRepository playerRepository;
    private final InputOutput inputOutput;
    private final InvalidCommandListener invalidBlackjackPlayerCommandListener;
    private final int dealerStopScoreInclusive; // TODO: dealer under over?
    private final int loopIntervalMillis;
    private final boolean isSingleGame;

    @Builder
    public BlackjackGameLauncher(final PlayerRepository playerRepository,
                                 final InputOutput inputOutput,
                                 final InvalidCommandListener invalidBlackjackPlayerCommandListener,
                                 final int dealerStopScoreInclusive,
                                 final int loopIntervalMillis,
                                 final boolean isSingleGame) {
        this.playerRepository = playerRepository;
        this.inputOutput = inputOutput;
        this.invalidBlackjackPlayerCommandListener = invalidBlackjackPlayerCommandListener;
        this.dealerStopScoreInclusive = dealerStopScoreInclusive;
        this.loopIntervalMillis = loopIntervalMillis;
        this.isSingleGame = isSingleGame;
    }

    public void launch() {
        final ConsoleInputProcessor consoleInputTranslator = new ConsoleInputProcessor(playerRepository);
        final BlackjackGameRunner runner = new BlackjackGameRunner(inputOutput,
                                                                   consoleInputTranslator, loopIntervalMillis);

        BlackjackGame blackjackGame = new BlackjackGame(dealerStopScoreInclusive, new RandomTrumpProvider());
        blackjackGame.addListener(new ConsoleOutput(inputOutput));
        if (isSingleGame) {
            blackjackGame.addListener(new ShutDownHook(inputOutput, runner));
        }

        consoleInputTranslator.addListener(blackjackGame);
        consoleInputTranslator.addListener(invalidBlackjackPlayerCommandListener);

        runner.run();
    }
}
