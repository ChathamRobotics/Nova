package org.chathamrobotics.nova.robot;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/5/2018
 */


import android.support.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Handles logging for the robot
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class RobotLogger {
    ///// CONSTANTS /////

    ///// STATICS /////

    ///// CLASSES /////

    /**
     * Logging levels
     */
    public enum Level {

        FATAL(7),
        ERROR(6),
        WARN(5),
        INFO(4),
        DEBUG(3),
        VERBOSE(2);

        private final int priority;

        Level(int priority) {
            this.priority = priority;
        }

        /**
         * Gets the levels priority. This is a int corresponding to the importance of the message
         *
         * @return  the levels priority
         */
        public int getPriority() {
            return priority;
        }
    }


    ///// INSTANCE FIELDS /////

    private final String tag;
    private final Telemetry telemetry;
    private final RobotLogger parent;

    private Level teleLevel;


    ///// CONSTRUCTORS /////

    /**
     * Creates a new instance of {@link RobotLogger}
     *
     * @param tag       the tag to log under
     * @param telemetry the telemetry for the opmode
     */
    public RobotLogger(String tag, Telemetry telemetry) {
        this.tag = tag;
        this.telemetry = telemetry;
        this.parent = null;
    }

    // for sub loggers
    private RobotLogger(String tag, RobotLogger parent) {
        this.tag = tag;
        this.telemetry = null;
        this.parent = parent;
    }

    ///// ASSESSORS /////

    /**
     * Sets the level for the telemetry output. If any messages are logged below the specified level
     * they will not be output to the telemetry.
     *
     * @param level the level to set the telemetry to.
     */
    public void setTelemetryLevel(Level level) {
        if (parent != null) parent.teleLevel = level;
        else teleLevel = level;
    }

    /**
     * Gets the log level of the telemetry.
     *
     * @return  the log level of the telemetry
     */
    public Level getTelemetryLevel() {
        if (parent != null) return parent.teleLevel;

        return teleLevel;
    }

    /**
     * Gets the telemetry used by the logger
     * @return  the telemetry used by the logger
     */
    public Telemetry getTelemetry() {
        if (parent != null) return parent.telemetry;
        return telemetry;
    }

    ///// BEHAVIOR /////

    /**
     * Creates a child logger. A logger that will log through it's parent but will have a unique tag
     * @param tag   the tag for the child logger
     * @return      the child logger
     */
    public RobotLogger childLogger(@NonNull String tag) {
        return new RobotLogger(tag, this);
    }

    public void update() {
        if (parent != null) parent.update();
        else if (telemetry != null) telemetry.update();
    }

    // DEBUG

}
