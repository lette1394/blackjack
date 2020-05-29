package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class BlackjackEndToEndTest {
    private final ConsoleGameRunner runner = new ConsoleGameRunner();
    private final ConsoleGameTestRunner assertions = new ConsoleGameTestRunner();
    private final StandardInputOutputUI player = new StandardInputOutputUI();

    @Test
    void APlayerJoinTheGame() {
        runner.waitForPlayer();
        assertions.hasShownWaitForPlayer();

        player.join();
        assertions.hasReceivedPlayerJoinInput();

        runner.start();
        assertions.hasShownGameIsStarted();

        runner.end();
        assertions.hasShownGameIsEnded();
    }

    @Test
    @Timeout(1)
    void APlayerReceivedTwoTrumpsCardsThenStayAfterJoin() {
        runner.waitForPlayer();
        assertions.hasShownWaitForPlayer();

        player.join();
        assertions.hasReceivedPlayerJoinInput();

        runner.start();
        assertions.hasShownGameIsStarted();

        Trump[] trumps = new Trump[]{ new Trump("♦️", "2"), new Trump("♣️", "8") };
        runner.drawTrumps(trumps);
        assertions.hasShownCards("(♦️2) (♣️8)");

        player.stay();
        assertions.hasReceivedPlayerStayInput();

        runner.showPlayerScore();
        assertions.hasShownPlayerScore(2 + 8);

        runner.end();
        assertions.hasShownGameIsEnded();
    }

    // 이거 이렇게 일일히 호출하는거 말고,
    // 트리거를 시켜야 한다.
    // 그러니까, player.hit()를 호출하면,
    // runner.showPlayerScore(); 가 딱 한 번 자동으로 트리거링 된다고 표현해야한다.
    //
    // 테스트는 >>>> 이벤트 발생 -> process -> process 의 assertion 이렇게 해야지
    // 여기서는 지금 runner를 직접 호출하고 있어서 이러면 안된다.

    @Test
    @Timeout(1)
    void APlayerReceivedTwoTrumpsCardsThenHitAfterJoin() {
        runner.waitForPlayer();
        assertions.hasShownWaitForPlayer();

        player.join();
        assertions.hasReceivedPlayerJoinInput();

        runner.start();
        assertions.hasShownGameIsStarted();

        Trump[] trumps = new Trump[]{ new Trump("♦️", "2"), new Trump("♣️", "8") };
        runner.drawTrumps(trumps);
        assertions.hasShownCards("(♦️2) (♣️8)");

        player.hit();
        assertions.hasReceivedPlayerHitInput();

        runner.showPlayerScore();
        assertions.hasShownPlayerScore(2 + 8);

        runner.end();
        assertions.hasShownGameIsEnded();
    }

}
