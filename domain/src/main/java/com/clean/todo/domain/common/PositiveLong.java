package com.clean.todo.domain.common;

import lombok.EqualsAndHashCode;
import lombok.experimental.Delegate;

@EqualsAndHashCode
public final class PositiveLong {
    @Delegate
    private final Long value;

    public PositiveLong(final long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(String.format("need positive long value: %s", value));
        }
        this.value = value;
    }
}
