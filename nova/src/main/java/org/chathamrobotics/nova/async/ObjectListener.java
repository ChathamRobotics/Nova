package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

import android.support.annotation.NonNull;

/**
 * Listens for conditions pertaining to a object
 * @param <T>   the type of the object
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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
    public ObjectListener(@NonNull final T object, @NonNull final Condition<T> condition, @NonNull AsyncCallback handler) {
        super(new Listener.Condition() {
            @Override
            public boolean test() {
                return condition.test(object);
            }
        }, handler);
    }
}
