package com.lette1394.blackjack.testutil;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.jmock.junit5.JUnit5Mockery;

public class BaseTest {
    @RegisterExtension
    public final JUnit5Mockery context = new JUnit5Mockery();
}
