package org.chathamrobotics.nova.robot;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/24/2018
 */

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.chathamrobotics.nova.system.RobotSystem;
import org.chathamrobotics.nova.util.RobotLogger;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

/**
 * The robot ie. a collection of robot systems
 */
@SuppressWarnings("WeakerAccess")
public class Robot extends ArrayList<RobotSystem> {

    public final RobotLogger logger;

    protected final HardwareMap hardwareMap;
    protected final Telemetry telemetry;

    /**
     * Creates a new instance of {@link Robot}
     * @param hardwareMap   the robot's hardware map
     * @param telemetry     the robot's telemetry
     */
    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        this.logger = new RobotLogger(this.getClass().getSimpleName(), telemetry);
    }

    /**
     * Gets the robot's hardware map
     * @return  the robot's hardware map
     */
    public HardwareMap getHardwareMap() {
        return hardwareMap;
    }

    /**
     * Initializes the robot
     */
    public void init() {
        // initialize all systems
        for (RobotSystem system : this)
            system.init();

        // wait for all systems to finish initializing
        for (RobotSystem system : this)
            while (! system.isInitialized()) {
                logger.debug.logf("Waiting for %s to initialize", system);
                logger.update();
                Thread.yield();
            }
    }

    /**
     * Starts the robot
     */
    public void start() {
        // start all systems
        for (RobotSystem system : this) {
            system.start();
        }

        logger.update();

        // wait for all systems to finish starting
        for (RobotSystem system : this)
            while (! system.isRunning()) {
                logger.debug.logf("Waiting for %s to start", system);
                logger.update();
                Thread.yield();
            }
    }

    /**
     * Stops the robot
     */
    public void stop() {
        // call stop on all systems
        for (RobotSystem system : this)
            system.stop();

        logger.update();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
