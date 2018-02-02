package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

/**
 * Listens for given condition and then calls the handler when it is met
 * @param <T>   the type of the object used to test the condition
 */
@SuppressWarnings("unused")
public class Listener<T> {
    /**
     * A condition that a listener will test to determine whether or not to call the handler
     * @param <T>   the type of object to test
     */
    public interface Condition<T> {
        /**
         * Returns true if the value meets the condition and false if it does not
         * @param value the value to test
         * @return      whether or not the condition is met
         */
        boolean test(T value);
    }

    private final Condition<T> condition;
    private final Runnable handler;

    /**
     * Creates a new instance of {@link Listener}
     * @param condition the condition to listen for
     * @param handler   the handler for when the condition is met
     */
    public Listener(Condition<T> condition, Runnable handler) {
        this.condition = condition;
        this.handler = handler;
    }

    public void run(T value) {
        if (condition.test(value)) handler.run();
    }
}
