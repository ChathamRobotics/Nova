package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Listens for given condition and then calls the handler when it is met
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public class Listener {
    /**
     * A condition that a listener will test to determine whether or not to call the handler
     */
    public interface Condition {
        /**
         * Returns true if the value meets the condition and false if it does not
         * @return      whether or not the condition is met
         */
        boolean test() throws Exception;
    }

    /**
     * Adds a timeout to the listener
     * @param listener  the listener
     * @param timeout   the timeout in ms
     * @param eventLoop the event loop to remove the listener from
     * @return          the listener
     */
    public static Listener timeout(@NonNull final Listener listener, long timeout, @NonNull final EventLoop eventLoop) {
        final long endTime = System.currentTimeMillis() + timeout;

        final Condition cnd = listener.condition;
        listener.condition = new Condition() {
            @Override
            public boolean test() throws Exception {
                if (System.currentTimeMillis() > endTime) {
                    eventLoop.removeListener(listener);
                    throw new TimeoutException("Listener timed out");
                }

                return cnd.test();
            }
        };

        return listener;
    }

    /**
     * Sets a call limit on the listener
     * @param listener  the listener
     * @param max       the maximum number of times the handler can be called
     * @param eventLoop the event loop to remove the listener from
     * @return          the listener
     */
    public static Listener callLimit(@NonNull final Listener listener, final int max, @NonNull final EventLoop eventLoop) {
        final AsyncCallback cb = listener.handler;
        listener.handler = new AsyncCallback() {
            private int callCount;

            @Override
            public void run(Throwable thr) {
                callCount++;

                cb.run(thr);

                if (callCount >= max) eventLoop.removeListener(listener);
            }
        };

        return listener;
    }

    /**
     * Sets a call limit of 1 on the listener
     * @param listener  the listener
     * @param eventLoop the event loop to remove the listener from
     * @return          the listener
     */
    public static Listener once(@NonNull Listener listener, @NonNull EventLoop eventLoop) {
        return callLimit(listener, 1, eventLoop);
    }

    private Condition condition;
    private AsyncCallback handler;

    /**
     * Creates a new instance of {@link Listener}
     * @param condition the condition to listen for
     * @param handler   the handler for when the condition is met
     */
    public Listener(@NonNull Condition condition, @NonNull AsyncCallback handler) {
        this.condition = condition;
        this.handler = handler;
    }

    /**
     * Runs the listener
     */
    public void run() {
        boolean result;
        try {
            result = condition.test();
        } catch (Throwable thr) {
            handler.run(thr);
            return;
        }

        if (result) handler.run(null);
    }
}
