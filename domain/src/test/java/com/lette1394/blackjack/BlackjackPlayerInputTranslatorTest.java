package com.lette1394.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jmock.Expectations;
import org.jmock.auto.Mock;

import com.lette1394.blackjack.util.BaseTest;

class BlackjackPlayerInputTranslatorTest extends BaseTest {
    @Mock BlackjackPlayerCommandListener listener;
    private BlackjackPlayerInputTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new BlackjackPlayerInputTranslator();
        translator.addListener(listener);
    }

    @Test
    void notifyJoinWhenAPlayerInputsJoin() {
        context.checking(new Expectations() {{
            oneOf(listener).join("1234");
        }});

        translator.translate("playerId=1234; command=join");
    }

    @Test
    void notifyHitWhenAPlayerInputsStay() {
        context.checking(new Expectations() {{
            oneOf(listener).hit("1234");
        }});

        translator.translate("playerId=1234; command=hit");
    }

    @Test
    void notifyStayWhenAPlayerInputsStay() {
        context.checking(new Expectations() {{
            oneOf(listener).stay("1234");
        }});

        translator.translate("playerId=1234; command=stay");
    }


    @Test
    void notifyCannotHandleWhenAPlayerInputAnInvalidCommand() {
        context.checking(new Expectations() {{
            oneOf(listener).cannotHandle("invalid command");
        }});

        translator.translate("invalid command");
    }
}