package com.clean.todo.domain.common;

import lombok.EqualsAndHashCode;
import lombok.experimental.Delegate;

@EqualsAndHashCode
public final class PositiveOrZeroInt {
    @Delegate
    private final Integer value;

    public PositiveOrZeroInt(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException(String.format("need positive int value: %s", value));
        }
        this.value = value;
    }
}
