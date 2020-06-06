package com.lette1394.blackjack.runner;

import lombok.Builder;

import com.lette1394.blackjack.BlackjackGame;
import com.lette1394.blackjack.io.ConsoleBlackjackGame;
import com.lette1394.blackjack.InvalidBlackjackPlayerCommandListener;
import com.lette1394.blackjack.PlayerRepository;
import com.lette1394.blackjack.domain.RandomTrumpProvider;
import com.lette1394.blackjack.io.BlackjackPlayerInputTranslator;
import com.lette1394.blackjack.io.PlayerInputGameOutput;

public class BlackjackGameLauncher {
    private final PlayerRepository playerRepository;
    private final PlayerInputGameOutput playerInputGameOutput;
    private final InvalidBlackjackPlayerCommandListener invalidBlackjackPlayerCommandListener;
    private final int dealerStopScoreInclusive;
    private final int loopIntervalMillis;

    @Builder
    public BlackjackGameLauncher(final PlayerRepository playerRepository,
                                 final PlayerInputGameOutput playerInputGameOutput,
                                 final InvalidBlackjackPlayerCommandListener invalidBlackjackPlayerCommandListener,
                                 final int dealerStopScoreInclusive,
                                 final int loopIntervalMillis) {
        this.playerRepository = playerRepository;
        this.playerInputGameOutput = playerInputGameOutput;
        this.invalidBlackjackPlayerCommandListener = invalidBlackjackPlayerCommandListener;
        this.dealerStopScoreInclusive = dealerStopScoreInclusive;
        this.loopIntervalMillis = loopIntervalMillis;
    }

    public void launch() {
        final BlackjackPlayerInputTranslator blackjackPlayerInputTranslator = new BlackjackPlayerInputTranslator(playerRepository);
        final BlackjackGameRunner runner = new BlackjackGameRunner(playerInputGameOutput, blackjackPlayerInputTranslator, loopIntervalMillis);

        BlackjackGame blackjackGame = new BlackjackGame(dealerStopScoreInclusive, new RandomTrumpProvider());
        blackjackGame.addListener(new ConsoleBlackjackGame(playerInputGameOutput));
        blackjackGame.addListener(new ShutDownHook(playerInputGameOutput, runner));

        blackjackPlayerInputTranslator.addListener(blackjackGame);
        blackjackPlayerInputTranslator.addListener(invalidBlackjackPlayerCommandListener);

        runner.run();
    }
}
