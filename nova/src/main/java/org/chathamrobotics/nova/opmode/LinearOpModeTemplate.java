package org.chathamrobotics.nova.opmode;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 4/3/2018
 */


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.chathamrobotics.nova.robot.Robot;
import org.chathamrobotics.nova.util.Controller;

/**
 * A template for creating linear opmodes
 * @param <R>   the robot type
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class LinearOpModeTemplate<R extends Robot> extends LinearOpMode {
    private final boolean redTeam;

    /**
     * The robot
     */
    protected R robot;

    /**
     * The controllers
     */
    protected Controller controller1, controller2;

    /**
     * Creates a new instance of {@link LinearOpModeTemplate}
     * @param isRedTeam whether or not this opmode is begin run for the red team
     */
    public LinearOpModeTemplate(boolean isRedTeam) {
        this.redTeam = isRedTeam;
    }

    /**
     * Checks whether or not this is opmode is begin run for the read team
     * @return true if this is the red team and false if this is the blue team
     */
    public boolean isRedTeam() {
        return redTeam;
    }

    /**
     * This method is called after begin and should be used perform the goal of the opmode
     */
    public abstract void run();

    /**
     * Synonymous with {@link OpMode#init()}, this method sets up the robot.
     * This methods is called on init
     */
    public abstract void setup();

    /**
     * Synonymous with {@link OpMode#start()}, this method starts the robot if one has been set.
     * This method is automatically called at the beginning of the opmode, after start has been pressed
     */
    public void begin() {
        if (robot != null) robot.start();
    }

    /**
     * Synonymous with {@link OpMode#stop()}, this method stops the robot if one has been set.
     * This method is automatically called at the end of the opmode
     */
    public void end() {
        if (robot != null) robot.stop();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        try {
            controller1 = new Controller(gamepad1);
            controller2 = new Controller(gamepad2);

            // init
            setup();

            // start
            waitForStart();
            begin();

            // run
            run();
        } finally {
            end();
        }
    }
}