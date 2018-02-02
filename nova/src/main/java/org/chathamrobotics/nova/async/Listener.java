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
 * Listens for given condition and then calls the handler when it is met
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Listener {
    /**
     * A condition that a listener will test to determine whether or not to call the handler
     */
    public interface Condition {
        /**
         * Returns true if the value meets the condition and false if it does not
         * @return      whether or not the condition is met
         */
        boolean test();
    }

    private final Condition condition;
    private final Runnable handler;

    /**
     * Creates a new instance of {@link Listener}
     * @param condition the condition to listen for
     * @param handler   the handler for when the condition is met
     */
    public Listener(@NonNull Condition condition, @NonNull Runnable handler) {
        this.condition = condition;
        this.handler = handler;
    }

    public void run() {
        if (condition.test()) handler.run();
    }
}
