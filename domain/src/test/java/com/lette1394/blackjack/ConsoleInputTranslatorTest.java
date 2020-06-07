package com.lette1394.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jmock.Expectations;
import org.jmock.auto.Mock;

import com.lette1394.blackjack.domain.CommandListener;
import com.lette1394.blackjack.domain.player.Player;
import com.lette1394.blackjack.domain.player.PlayerRepository;
import com.lette1394.blackjack.io.ConsoleInputTranslator;
import com.lette1394.blackjack.testutil.BaseTest;

class ConsoleInputTranslatorTest extends BaseTest {
    @Mock CommandListener listener;
    @Mock PlayerRepository repository;
    private ConsoleInputTranslator translator;

    private static final Player player = new Player();

    @BeforeEach
    void setUp() {
        translator = new ConsoleInputTranslator(repository);
        translator.addListener(listener);
    }

    @Test
    void notifyJoinWhenAPlayerInputsJoin() {
        context.checking(new Expectations() {{
            oneOf(repository).find("1234"); will(returnValue(player));

            oneOf(listener).join(player);
        }});

        translator.translate("playerId=1234; command=join");
    }

    @Test
    void notifyHitWhenAPlayerInputsStay() {
        context.checking(new Expectations() {{
            oneOf(repository).find("1234"); will(returnValue(player));

            oneOf(listener).hit(player);
        }});

        translator.translate("playerId=1234; command=hit");
    }

    @Test
    void notifyStayWhenAPlayerInputsStay() {
        context.checking(new Expectations() {{
            oneOf(repository).find("1234"); will(returnValue(player));

            oneOf(listener).stay(player);
        }});

        translator.translate("playerId=1234; command=stay");
    }

    @Test
    void notifyCannotHandleWhenAPlayerInputAnInvalidString() {
        context.checking(new Expectations() {{
            oneOf(listener).cannotHandle("invalid command");
        }});

        translator.translate("invalid command");
    }

    @Test
    void notifyCannotHandleWhenAPlayerInputAnInvalidCommand() {
        context.checking(new Expectations() {{
            oneOf(repository).find("1234"); will(returnValue(player));

            oneOf(listener).cannotHandle("playerId=1234; command=unknown-command");
        }});

        translator.translate("playerId=1234; command=unknown-command");
    }
}