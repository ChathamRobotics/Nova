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
}
