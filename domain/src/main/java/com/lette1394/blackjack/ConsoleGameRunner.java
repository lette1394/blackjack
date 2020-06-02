package com.lette1394.blackjack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.lette1394.blackjack.ui.PlayerInputGameOutput;

@Slf4j
@RequiredArgsConstructor
public class ConsoleGameRunner implements GameRunner {
    // TODO: 이거 좀 없앨 수 없나? 흠...
    //  생성자 의존성으로 빼야하는데 그렇게하면 테스트 만들기가 어렵네. 부분 빌더 패턴?
    @Setter
    private CardProvider cardProvider;

    private final PlayerInputGameOutput playerInputGameOutput;
    private final PlayerInputTranslator playerInputTranslator;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void run() {
        playerInputGameOutput.send("wait for player...");
        executorService.submit(this::runLoop);
    }

    private void runLoop() {
        playerInputTranslator.addListener(new PlayerInputEventAdapter(new BlackjackGame(cardProvider, playerInputGameOutput)));

        while (true) {
            try {
                String userInput = playerInputGameOutput.get();
                playerInputTranslator.translate(userInput);
                Thread.sleep(50);
            } catch (Exception e) {
                log.error("unexpected error : " + e);
                e.printStackTrace();
                System.exit(99);
            }
        }
    }
}
