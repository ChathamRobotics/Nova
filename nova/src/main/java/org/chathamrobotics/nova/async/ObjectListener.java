package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

/**
 * Listens for conditions pertaining to a object
 * @param <T>   the type of the object
 */
@SuppressWarnings("unused")
public class ObjectListener<T> extends Listener {
    /**
     * Condition based on a object
     * @param <T>   the type of the object
     */
    public interface Condition<T> {
        boolean test(T value);
    }

    /**
     * Creates a new {@link ObjectListener}
     * @param object    the object to test
     * @param condition the condition to test
     * @param handler   the handler to be called when the condition is met
     */
    public ObjectListener(final T object, final Condition<T> condition, Runnable handler) {
        super(new Listener.Condition() {
            @Override
            public boolean test() {
                return condition.test(object);
            }
        }, handler);
    }
}
