package org.chathamrobotics.nova.systems;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/11/2018
 */

/**
 * A drive train system
 */
@SuppressWarnings("unused")
public interface DriveTrain extends RobotSystem {
    /**
     * Gets the magnitude of the drive train's power
     * @return  the magnitude of the drive train's power
     */
    double getPower();

    /**
     * Sets the magnitude of the drive train's power
     * @param power the magnitude of the drive train's power
     */
    void setPower(double power);

    /**
     * Rotates the robot at the given power
     * @param power the power to rotate the robot at. If power > 0 the robot will rotate clockwise and if power < 0 the robot will rotate counter-clockwise
     */
    void rotate(double power);
}
