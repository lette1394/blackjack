package com.lette1394.blackjack;

import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.ConsoleUserInterface;
import com.lette1394.blackjack.ui.UserInterface;

@Slf4j
public class ConsoleGameLauncher {
    public static final String WAIT_MESSAGE = "wait for player...";
    public static final String START_MESSAGE = "new blackjack game start";
    public static final String END_MESSAGE = "game ended";

    public static final String COMMAND_JOIN = "join";
    public static final String COMMAND_HIT = "hit";
    public static final String COMMAND_STAY = "stay";

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // TODO: remove
    private final UserInterface userInterface;

    public ConsoleGameLauncher(final UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public static void main(String[] args) {
        final PlayerInputTranslator playerInputTranslator = new PlayerInputTranslator();
        final UserInterface userInterface = new ConsoleUserInterface(System.in,
                                                                     System.out,
                                                                     playerInputTranslator);
        final ConsoleGameRunner runner = new ConsoleGameRunner(userInterface);
        playerInputTranslator.addListener(new PlayerInputEventAdapter(runner));

        final ConsoleGameLauncher launcher = new ConsoleGameLauncher(userInterface);
        launcher.run();

        runner.setCardProvider(new ArrayDeque<>(Lists.newArrayList(new Trump("♦️", "2"), new Trump("♣️", "8"),
                                                                   new Trump("♥️", "3"), new Trump("♠️", "9")))::poll);
    }

    public void run() {
        waitForPlayer();
        executorService.submit(() -> userInterface.runLoop());
    }

    // TODO: remove
    public void waitForPlayer() {
        send(WAIT_MESSAGE);
    }

    // TODO: remove
    private void send(final Object output) {
        userInterface.send(output);
    }
}
