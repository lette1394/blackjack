package com.lette1394.blackjack;

import org.junit.jupiter.api.Test;
import org.jmock.Expectations;
import org.jmock.auto.Mock;

import com.lette1394.blackjack.util.BaseTest;

class EventDispatcherTest extends BaseTest {
    interface SampleListener {
        void call();
    }

    private final EventDispatcher<SampleListener> dispatcher = new EventDispatcher<>(null);
    @Mock
    private SampleListener sampleListener;

    @Test
    void eventDispatcherCallSingleListeners() {
        context.checking(new Expectations() {{
            oneOf(sampleListener).call();
        }});

        dispatcher.add(sampleListener);
        dispatcher.call();
    }
}