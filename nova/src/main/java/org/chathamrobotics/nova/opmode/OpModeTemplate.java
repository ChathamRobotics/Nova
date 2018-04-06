package org.chathamrobotics.nova.opmode;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 4/5/2018
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.chathamrobotics.nova.robot.Robot;
import org.chathamrobotics.nova.util.Controller;

/**
 * A template for creating a opmode
 * @param <R>   the type of the robot
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class OpModeTemplate<R extends Robot> extends OpMode {
    /**
     * The robot
     */
    protected R robot;

    /**
     * The controllers
     */
    protected Controller controller1, controller2;

    /**
     * Sets up the controllers
     */
    @Override
    public void internalPreInit() {
        super.internalPreInit();
        controller1 = new Controller(gamepad1);
        controller2 = new Controller(gamepad2);
    }

    /**
     * Starts the robot if there is one
     */
    @Override
    public void start() {
        super.start();
        if (robot != null) robot.start();
    }

    /**
     * Stops the robot if there is one
     */
    @Override
    public void stop() {
        super.stop();
        if (robot != null) robot.stop();
    }

    /**
     * Updates the controllers
     */
    @Override
    public void loop() {
        controller1.update();
        controller2.update();
    }
}
