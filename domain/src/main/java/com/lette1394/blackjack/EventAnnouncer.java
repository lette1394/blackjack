package com.lette1394.blackjack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class EventAnnouncer<T extends EventListener> {
    private final T proxy;
    private final List<T> listeners = new ArrayList<>();

    public EventAnnouncer(final Class<T> listenerType) {

        for (Method method : listenerType.getMethods()) {
            if (isVoidType(method.getReturnType())) {
                continue;
            }
            throw new IllegalArgumentException(String.format("return type of [%s] is not void", method));
        }

        proxy = listenerType.cast(
                Proxy.newProxyInstance(
                        listenerType.getClassLoader(), new Class<?>[]{ listenerType },
                        (aProxy, method, args) -> {
                            dispatch(method, args);
                            return null;
                        }));
    }

    public void add(final T listener) {
        listeners.add(listener);
    }

    public T announce() {
        return proxy;
    }

    private void dispatch(Method method, Object[] args) {
        try {
            for (T listener : listeners) {
                method.invoke(listener, args);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("could not invoke listener", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();

            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw new UnsupportedOperationException("listener threw exception", cause);
            }
        }
    }

    private boolean isVoidType(final Class<?> clazz) {
        return void.class.equals(clazz) || Void.class.equals(clazz);
    }
}
