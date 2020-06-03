package com.lette1394.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jmock.Expectations;
import org.jmock.auto.Mock;

import com.lette1394.blackjack.util.BaseTest;

class BlackjackPlayerInputTranslatorTest extends BaseTest {
    @Mock PlayerCommandListener listener;
    private BlackjackPlayerInputTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new BlackjackPlayerInputTranslator();
        translator.addListener(listener);
    }

    @Test
    void notifyJoinWhenAPlayerInputsJoin() {
        context.checking(new Expectations() {{
            oneOf(listener).join();
        }});

        translator.translate("join");
    }

    @Test
    void notifyHitWhenAPlayerInputsStay() {
        context.checking(new Expectations() {{
            oneOf(listener).hit();
        }});

        translator.translate("hit");
    }

    @Test
    void notifyStayWhenAPlayerInputsStay() {
        context.checking(new Expectations() {{
            oneOf(listener).stay();
        }});

        translator.translate("stay");
    }


    @Test
    void notifyCannotHandleWhenAPlayerInputAnInvalidCommand() {
        context.checking(new Expectations() {{
            oneOf(listener).cannotHandle("invalid command");
        }});

        translator.translate("invalid command");
    }
}