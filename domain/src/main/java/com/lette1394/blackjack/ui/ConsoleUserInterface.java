package com.lette1394.blackjack.ui;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ConsoleGameLauncher;
import com.lette1394.blackjack.PlayerInputTranslator;

@Slf4j
public class ConsoleUserInterface implements UserInterface {

    private final PlayerInputTranslator playerInputTranslator;

    private final Scanner in;
    private final PrintStream out;

    public ConsoleUserInterface(InputStream in, OutputStream out, ConsoleGameLauncher gameRunner) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out, true);

        this.playerInputTranslator = new PlayerInputTranslator(gameRunner);
    }

    @Override
    @SneakyThrows
    public void runLoop() {
        // TODO: 무한 루프 괜찮나? -> 나중에 웹으로 게임을 제공하면 어떻게 되나?
        //  runner가 여러개 있어야할듯.

        while (true) {
            try {
                // TODO: blocking 되는 코드 괜찮나? notify 형식으로 해야하는거 아닌가...
                final String userInput = in.nextLine();
                System.out.println("\f");
                playerInputTranslator.translate(userInput);
                Thread.sleep(50);
            } catch (Exception e) {
                log.error("unexpected error : " + e);
                e.printStackTrace();
                System.exit(99);
            }
        }
    }

    @Override
    public void send(final Object output) {
        out.println(output);
    }
}

