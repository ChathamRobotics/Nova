package org.chathamrobotics.nova.util;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/11/2018
 */


import java.util.concurrent.TimeoutException;

/**
 * A utility for keeping track of timeouts
 */
@SuppressWarnings("unused")
public class TimeoutChecker {
    private final long endTime;
    private final String message;

    /**
     * Creates a new instance of {@link TimeoutChecker}
     * @param timeout   the timeout
     */
    public TimeoutChecker(long timeout) {
        this(timeout, "Timed out");
    }

    /**
     * Creates a new instance of {@link TimeoutChecker}
     * @param timeout   the timeout
     * @param message   the message to throw the timeout exception with
     */
    public TimeoutChecker(long timeout, String message) {
        this.endTime = System.currentTimeMillis() + timeout;
        this.message = message;
    }

    /**
     * Checks whether or not the timeout has been reached. Throws a timeout exception if it has
     * @throws TimeoutException thrown if the timeout has been reached
     */
    public void check() throws TimeoutException {
        if (System.currentTimeMillis() > endTime)
            throw new TimeoutException(message);
    }
}
