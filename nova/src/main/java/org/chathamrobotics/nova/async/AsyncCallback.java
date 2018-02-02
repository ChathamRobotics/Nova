package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

/**
 * A asynchronous callback function
 */
@SuppressWarnings("unused")
public interface AsyncCallback {
    /**
     * Runs the callback
     * @param thr   a error/exception if there was one. Null if there wasn't
     */
    void run(Throwable thr);
}
