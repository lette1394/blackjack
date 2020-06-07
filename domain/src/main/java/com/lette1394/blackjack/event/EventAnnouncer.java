package com.lette1394.blackjack.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;

public class EventAnnouncer<T extends EventListener> implements ListenersAware<T> {
    private final T proxy;
    private final Collection<T> listeners;

    public EventAnnouncer(final Class<T> listenerType) {
        this(listenerType, new ArrayList<>());
    }

    public EventAnnouncer(final Class<T> listenerType, final Collection<T> listeners) {
        ensureMethodReturnTypeIsNotVoid(listenerType);

        this.listeners = listeners;
        this.proxy = listenerType.cast(
                Proxy.newProxyInstance(
                        listenerType.getClassLoader(), new Class<?>[]{ listenerType },
                        (aProxy, method, args) -> {
                            dispatch(method, args);
                            return null;
                        }));
    }

    public T announce() {
        return proxy;
    }

    @Override
    public void addListener(final T listener) {
        listeners.add(listener);
    }

    private void ensureMethodReturnTypeIsNotVoid(final Class<T> listenerType) {
        for (Method method : listenerType.getMethods()) {
            if (isVoidType(method.getReturnType())) {
                continue;
            }
            throw new IllegalArgumentException(String.format("return type of [%s] is not void", method));
        }
    }

    private boolean isVoidType(final Class<?> clazz) {
        return void.class.equals(clazz) || Void.class.equals(clazz);
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
}
