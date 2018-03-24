package org.chathamrobotics.nova.system;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/24/2018
 */

/**
 * A system in the robot
 */
@SuppressWarnings("unused")
public interface RobotSystem {
    /**
     * The state of the system
     */
    enum State {
        INITIALIZED,
        RUNNING,
        STOPPED,
    }

    /**
     * Initializes the system
     */
    void init();

    /**
     * Checks whether or not the system is initialized
     * @return  whether or not the system is initialized
     */
    boolean isInitialized();

    /**
     * Starts the system
     * @throws IllegalStateException thrown if the system has not been initialized yet
     */
    void start();

    /**
     * Checks whether or not the system is running
     * @return  whether or not the system is running
     */
    boolean isRunning();

    /**
     * Stops the system
     */
    void stop();

    /**
     * Gets the state of the system
     * @return  the state of the system
     */
    State getState();
}
