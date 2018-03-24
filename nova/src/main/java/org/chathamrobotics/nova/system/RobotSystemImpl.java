package org.chathamrobotics.nova.system;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/24/2018
 */

import org.chathamrobotics.nova.util.RobotLogger;

/**
 * A implementation of robot system
 */
@SuppressWarnings({"unused", "WeakerAccess"})
abstract class RobotSystemImpl implements RobotSystem {
    protected final RobotLogger logger;

    private State state;

    /**
     * Creates a new instance of {@link RobotSystemImpl}
     * @param logger    the logger for the system
     */
    public RobotSystemImpl(RobotLogger logger) {
        this.logger = logger;
    }

    /**
     * Checks whether or not the system is running
     * @return  whether or not the system is running
     */
    @Override
    public boolean isRunning() {
        return state == State.RUNNING;
    }

    /**
     * Checks whether or not the system is initialized
     * @return  whether or not the system is initialized
     */
    @Override
    public boolean isInitialized() {
        return state == State.INITIALIZED || state == State.RUNNING;
    }

    /**
     * Gets the state of the system
     * @return  the state of the system
     */
    public State getState() {
        return this.state;
    }

    // sets the state
    protected void setState(State state) {
        this.state = state;
    }

    protected void preStart() {
        if (this.state != State.INITIALIZED && this.state != State.RUNNING)
            throw new IllegalStateException("The system must be initialized before starting");
    }
}
