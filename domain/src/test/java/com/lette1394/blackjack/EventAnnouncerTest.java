package com.lette1394.blackjack;

import java.util.EventListener;

import org.junit.jupiter.api.Test;
import org.jmock.Expectations;
import org.jmock.auto.Mock;

import com.lette1394.blackjack.util.BaseTest;
import com.lette1394.blackjack.util.EventAnnouncer;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EventAnnouncerTest extends BaseTest {
    interface SampleListener extends EventListener {
        void call();
    }

    private final EventAnnouncer<SampleListener> dispatcher = new EventAnnouncer<>(SampleListener.class);

    @Mock private SampleListener sampleListener1;

    @Test
    void eventDispatcherCallSingleListeners() {
        context.checking(new Expectations() {{
            oneOf(sampleListener1).call();
        }});

        dispatcher.addListener(sampleListener1);
        dispatcher.announce().call();
    }

    @Mock private SampleListener sampleListener2;

    @Test
    void eventDispatcherCallMultiListeners() {
        context.checking(new Expectations() {{
            oneOf(sampleListener1).call();
            oneOf(sampleListener2).call();
        }});

        dispatcher.addListener(sampleListener1);
        dispatcher.addListener(sampleListener2);
        dispatcher.announce().call();
    }


    interface ReturnTypeIsNotVoidListener extends EventListener {
        int call();
    }
    @Test
    void eventDispatcherThrowExceptionWhenNonVoidListeners() {
        assertThrows(IllegalArgumentException.class, () -> new EventAnnouncer<>(ReturnTypeIsNotVoidListener.class));
    }
}