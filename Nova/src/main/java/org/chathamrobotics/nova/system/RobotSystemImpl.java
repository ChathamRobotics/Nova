package org.chathamrobotics.nova.system;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/24/2018
 */

import org.chathamrobotics.nova.async.Listener;
import org.chathamrobotics.nova.async.NovaEventLoop;
import org.chathamrobotics.nova.util.RobotLogger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A implementation of robot system
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class RobotSystemImpl implements RobotSystem {
    private static final NovaEventLoop EVENT_LOOP = NovaEventLoop.getInstance();

    protected final RobotLogger logger;
    protected final List<Listener> openListeners = new LinkedList<>();

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

    /**
     * To be called on start to confirm that the system is initialized
     */
    protected void preStart() {
        if (this.state != State.INITIALIZED && this.state != State.RUNNING)
            throw new IllegalStateException("The system must be initialized before starting");
    }

    /**
     * Confirms that the system is running. If not a {@link IllegalStateException} will be thrown
     * @param operation the operation being performed
     */
    protected void confirmRunning(String operation) {
        if (this.state != State.RUNNING)
            throw new IllegalStateException("The system must be running inorder to " + operation);
    }

    /**
     * Confirms that the system is initialized. If it is not a {@link IllegalStateException} will be thrown
     * @param operation the name of the operation being performed
     */
    protected void confirmInitialized(String operation) {
        if (! isInitialized())
            throw new IllegalStateException(operation + " can only be called after initialization");
    }

    /**
     * Removes all of the open listeners
     */
    protected void removeOpenListeners() {
        for (Listener listener : openListeners) EVENT_LOOP.removeListener(listener);
    }
}
