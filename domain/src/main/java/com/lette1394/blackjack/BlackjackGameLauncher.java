package com.lette1394.blackjack;

import lombok.Builder;

import com.lette1394.blackjack.ui.PlayerInputGameOutput;

public class BlackjackGameLauncher {
    private final PlayerRepository playerRepository;
    private final PlayerInputGameOutput playerInputGameOutput;
    private final InvalidBlackjackPlayerCommandListener invalidBlackjackPlayerCommandListener;
    private final int dealerStopScoreInclusive;

    @Builder
    public BlackjackGameLauncher(final PlayerRepository playerRepository,
                                 final PlayerInputGameOutput playerInputGameOutput,
                                 final InvalidBlackjackPlayerCommandListener invalidBlackjackPlayerCommandListener,
                                 final int dealerStopScoreInclusive) {
        this.playerRepository = playerRepository;
        this.playerInputGameOutput = playerInputGameOutput;
        this.invalidBlackjackPlayerCommandListener = invalidBlackjackPlayerCommandListener;
        this.dealerStopScoreInclusive = dealerStopScoreInclusive;
    }

    public void launch() {
        final BlackjackPlayerInputTranslator blackjackPlayerInputTranslator = new BlackjackPlayerInputTranslator(playerRepository);
        final BlackjackGameRunner runner = new BlackjackGameRunner(playerInputGameOutput, blackjackPlayerInputTranslator);

        BlackjackGame blackjackGame = new BlackjackGame(dealerStopScoreInclusive, new RandomTrumpProvider());
        blackjackGame.addListener(new ConsoleBlackjackGame(playerInputGameOutput));
        blackjackGame.addListener(new ShutDownHook(playerInputGameOutput, runner));

        blackjackPlayerInputTranslator.addListener(blackjackGame);
        blackjackPlayerInputTranslator.addListener(invalidBlackjackPlayerCommandListener);

        runner.run();
    }
}
