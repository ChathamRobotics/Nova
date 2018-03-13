package org.chathamrobotics.nova.robot;

import android.support.annotation.NonNull;
import android.util.Log;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A logger for the robot
 */
public class RobotLogger {
    /////////// CONSTANTS ///////////

    ////////// INNER CLASSES ////////

    /**
     * Log levels for the robot logger
     */
    public enum Level {
        FATAL(Log.ASSERT),
        ERROR(Log.ERROR),
        WARN(Log.WARN),
        INFO(Log.INFO),
        DEBUG(Log.DEBUG),
        VERBOSE(Log.VERBOSE);

        private final int priority;

        Level(int priority) {
            this.priority = priority;
        }
    }

    ////////// FIELDS ///////////////
    private final Telemetry telemetry;
    private final RobotLogger parent;
    private final String tag;

    private Level level;

    ////////// CONSTRUCTORS /////////

    public RobotLogger(@NonNull String tag, @NonNull Telemetry telemetry) {
        this.tag = tag;

        this.telemetry = telemetry;
        this.parent = null;
    }

    private RobotLogger(@NonNull String tag, @NonNull RobotLogger parent) {
        this.tag = parent.tag + "/" + tag;

        this.telemetry = null;
        this.parent = parent;
    }

    ////////// ACCESSORS ////////////

    /**
     * Gets the internal telemetry that is used for logging
     * @return  the internal telemetry
     */
    public Telemetry getTelemetry() {
        if (parent != null)
            return parent.telemetry;

        return telemetry;
    }

    /**
     * Gets the current level of the logger
     * @return  the current level of the logger
     */
    public Level getLevel() {
        if (parent != null)
            return parent.getLevel();

        return level;
    }

    /**
     * Sets the logging level. NOTE: Logs will be sent to logcat regardless of level
     * @param level the level to log at
     */
    public void setLevel(Level level) {
        if (parent != null)
            parent.setLevel(level);
        else
            this.level = level;
    }

    ////////// BEHAVIOR ////////////


    private void out(@NonNull Level logLevel, String message, Throwable throwable) {
        RobotLog.internalLog(logLevel.priority, tag, throwable, message);
    }

    private void out(@NonNull Level logLevel, String message) {
        RobotLog.internalLog(logLevel.priority, tag, message);
    }
}
