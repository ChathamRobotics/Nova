package org.chathamrobotics.nova.robot;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/5/2018
 */


import android.support.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;

import java.util.LinkedList;
import java.util.Locale;

/**
 * Handles logging for the robot
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class RobotLogger implements Telemetry {
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
    private final String androidLogTag;
    private final Telemetry telemetry;
    private final RobotLogger parent;

    private final

    private Level teleLevel;
    private Level defaultTeleLevel;


    ///// CONSTRUCTORS /////

    /**
     * Creates a new instance of {@link RobotLogger}
     *
     * @param tag       the tag to log under
     * @param telemetry the telemetry for the opmode
     */
    public RobotLogger(@NonNull String tag, @NonNull Telemetry telemetry) {
        this.tag = tag;
        this.androidLogTag = tag;
        this.telemetry = telemetry;
        this.parent = null;
    }

    // for sub loggers
    private RobotLogger(@NonNull String tag, @NonNull RobotLogger parent) {
        this.tag = tag;
        this.androidLogTag = parent.tag + "/" + tag;
        this.telemetry = null;
        this.parent = parent;
    }

    ///// ASSESSORS /////

    // TELEMETRY METHODS
    /**
     * @see Telemetry
     */
    @Override
    public void setMsTransmissionInterval(int msTransmissionInterval) {
        getTelemetry().setMsTransmissionInterval(msTransmissionInterval);
    }

    /**
     * @see Telemetry
     */
    @Override
    public int getMsTransmissionInterval() {
        return getTelemetry().getMsTransmissionInterval();
    }

    /**
     * @see Telemetry
     */
    @Override
    public void setItemSeparator(String itemSeparator) {
        getTelemetry().setItemSeparator(itemSeparator);
    }

    /**
     * @see Telemetry
     */
    @Override
    public String getItemSeparator() {
        return getTelemetry().getItemSeparator();
    }

    /**
     * @see Telemetry
     */
    @Override
    public void setCaptionValueSeparator(String captionValueSeparator) {
        getTelemetry().setCaptionValueSeparator(captionValueSeparator);
    }

    /**
     * @see Telemetry
     */
    @Override
    public String getCaptionValueSeparator() {
        return getTelemetry().getCaptionValueSeparator();
    }

    /**
     * @see Telemetry
     */
    @Override
    public void setAutoClear(boolean autoClear) {
        getTelemetry().setAutoClear(autoClear);
    }

    /**
     * @see Telemetry
     */
    @Override
    public boolean isAutoClear() {
        return getTelemetry().isAutoClear();
    }

    // ROBOT LOGGER METHODS
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
        if (parent != null) return parent.getTelemetry();
        return telemetry;
    }

    ///// BEHAVIOR /////

    // TELEMETRY METHODS
    /**
     * @see Telemetry
     */
    @Override
    public Item addData(String caption, Object value) {
        return output(defaultTeleLevel, caption, value);
    }

    /**
     * @see Telemetry
     */
    @Override
    public Item addData(String caption, String format, Object... args) {
        return output(defaultTeleLevel, caption, format, args);
    }

    /**
     * @see Telemetry
     */
    @Override
    public <T> Item addData(String caption, String format, Func<T> valueProducer) {
        return output(defaultTeleLevel, caption, format, valueProducer);
    }

    /**
     * @see Telemetry
     */
    @Override
    public <T> Item addData(String caption, Func<T> valueProducer) {
        return output(defaultTeleLevel, caption, valueProducer);
    }

    /**
     * @see Telemetry
     */
    @Override
    public Object addAction(Runnable action) {
        // TODO
    }

    /**
     * @see Telemetry
     */
    @Override
    public Line addLine() {
        // TODO
    }

    /**
     * @see Telemetry
     */
    @Override
    public Line addLine(String lineCaption) {
        // TODO
    }

    /**
     * @see Telemetry
     */
    @Override
    public boolean removeAction(Object token) {
        // TODO
    }

    /**
     * @see Telemetry
     */
    @Override
    public boolean removeItem(Item item) {
        // TODO
    }

    /**
     * @see Telemetry
     */
    @Override
    public boolean removeLine(Line line) {
        // TODO
    }

    /**
     * NOTE: Only clears telemetry
     * @see Telemetry
     */
    @Override
    public void clear() {
        getTelemetry().clear();
    }

    /**
     * NOTE: Only clears telemetry
     * @see Telemetry
     */
    @Override
    public void clearAll() {
        getTelemetry().clearAll();
    }

    /**
     * @see Telemetry
     */
    @Override
    public Log log() {
        return getTelemetry().log();
    }

    /**
     * @see Telemetry
     */
    @Override
    public boolean update() {
        return getTelemetry().update();
    }

    // ROBOT LOGGER METHODS
    /**
     * Creates a child logger. A logger that will log through it's parent but will have a unique tag
     * @param tag   the tag for the child logger
     * @return      the child logger
     */
    public RobotLogger childLogger(@NonNull String tag) {
        return new RobotLogger(tag, this);
    }

    // output methods log to telemetry and android
    private Item output(Level level, String caption, Object value) {
        logAndroid(level, formatMsg(caption, value));
        if (! shouldLogTele(level)) return null;

        caption = getTeleCap(level, caption);
        return getTelemetry().addData(caption, value);
    }

    private Item output(Level level, String caption, String format, Object... args) {
        logAndroid(level, formatMsg(caption, format, args));
        if (! shouldLogTele(level)) return null;

        caption = getTeleCap(level, caption);
        return getTelemetry().addData(caption, format, args);
    }

    private <T> Item output(final Level level, String caption, final Func<T> valueProducer) {
        caption = getTeleCap(level, caption);

        // wrap value producer so that it only logs at right level;
        return getTelemetry().addData(caption, new Func<T>() {
            @Override
            public T value() {
                if (! shouldLogTele(level)) return null;

                return valueProducer.value();
            }
        });
    }

    private <T> Item output(final Level level, String caption, String format, final Func<T> valueProducer) {
        caption = getTeleCap(level, caption);

        Item item = getTelemetry().addData(caption, format, new Func<T>() {
            @Override
            public T value() {
                if (! shouldLogTele(level)) return null;

                return valueProducer.value();
            }
        });

        return item;
    }

    private String getTeleCap(Level level, String currentCap) {
        if (parent != null) return "(" + tag + "/" + level.name().toUpperCase() + ") " + currentCap;

        return "(" + level + ") " + currentCap;
    }

    private boolean shouldLogTele(Level level) {
        return level.priority >= teleLevel.priority;
    }

    private String formatMsg(String caption, Object value) {
        return String.format(Locale.US, "%s%s%s", caption, getCaptionValueSeparator(), value.toString());
    }

    private String formatMsg(String caption, String format, Object... args) {
        return formatMsg(caption, String.format(Locale.US, format, args));
    }

    private void logAndroid(Level level, String msg) {
        android.util.Log.println(level.priority, androidLogTag, msg);
    }
}
