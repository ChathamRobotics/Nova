package org.chathamrobotics.nova.systems;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/5/2018
 */

import org.chathamrobotics.nova.async.AsyncCallback;

/**
 * A system on the robot eg. a drive train or lift
 */
@SuppressWarnings("unused")
public interface System {
    /**
     * Initializes the system
     * @param callback  called when the system is initialized
     */
    void init(AsyncCallback callback);

    /**
     * Starts the system
     * @param callback  called when the system is running (successfully started)
     */
    void start(AsyncCallback callback);

    /**
     * Stops the system
     */
    void stop();

    /**
     * Checks whether or not the system is initialized
     * @return  whether or not the system is initialized
     */
    boolean isInitialized();

    /**
     * Checks whether or not the system is running (has been started)
     * @return  whether or not the system is running
     */
    boolean isRunning();
}
