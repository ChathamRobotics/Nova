package org.chathamrobotics.nova.system;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/24/2018
 */

/**
 * A robot system which handles driving
 */
public interface DriveSystem extends RobotSystem {
    /**
     * Halts the movement of the robot
     */
    void halt();

    /**
     * Rotates the robot
     * @param power the power at which to rotate the robot
     */
    void rotate(double power);

    /**
     * Drives the robot
     * @param power the power with which to drive thr robot
     */
    void drive(double power);
}
