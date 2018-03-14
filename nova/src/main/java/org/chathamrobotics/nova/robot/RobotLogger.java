package org.chathamrobotics.nova.robot;

import android.support.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A logger for the robot
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public class RobotLogger implements Telemetry {
    /////////// CONSTANTS ///////////

    ////////// INNER CLASSES ////////

    /**
     * Log levels for the robot logger
     */
    public enum Level {
        FATAL(android.util.Log.ASSERT),
        ERROR(android.util.Log.ERROR),
        WARN(android.util.Log.WARN),
        INFO(android.util.Log.INFO),
        DEBUG(android.util.Log.DEBUG),
        VERBOSE(android.util.Log.VERBOSE);

        private final int priority;

        Level(int priority) {
            this.priority = priority;
        }
    }

    ////////// FIELDS ///////////////
    private final Telemetry telemetry;
    private final RobotLogger parent;
    private final String tag;

    // If this is main logger these are used
    private Level level = Level.DEBUG;
    private Level teleLevel = Level.DEBUG;
    private String captionValueSeparator = " : ";

    ////////// CONSTRUCTORS /////////

    public RobotLogger(@NonNull String tag, @NonNull Telemetry telemetry) {
        this.tag = tag;

        this.telemetry = telemetry;
        this.parent = null;

        telemetry.setCaptionValueSeparator("");
    }

    private RobotLogger(@NonNull String tag, @NonNull RobotLogger parent) {
        this.tag = parent.tag + "/" + tag;

        this.telemetry = null;
        this.parent = parent;
    }

    ////////// ACCESSORS ////////////

    /**
     * Gets the current level of the logger
     * @return  the current level of the logger
     */
    public Level getLevel() {
        return getMainLogger().level;
    }

    /**
     * Sets the logging level. NOTE: Logs will be sent to logcat regardless of level
     * @param level the level to log at
     */
    public void setLevel(Level level) {
        getMainLogger().level = level;
    }

    /**
     * Sets the caption value separator
     * @see Telemetry#setCaptionValueSeparator(String)
     */
    @Override
    public void setCaptionValueSeparator(String separator) {
        getMainLogger().captionValueSeparator = separator;
    }

    /**
     * Returns the log for the telemetry
     * @see Telemetry#log()
     */
    @Override
    public Log log() {
        return getTelemetry().log();
    }

    /**
     * Gets the caption value separator
     * @see Telemetry#getCaptionValueSeparator()
     */
    @Override
    public String getCaptionValueSeparator() {
        return getMainLogger().captionValueSeparator;
    }

    /**
     * @see Telemetry#isAutoClear()
     */
    @Override
    public boolean isAutoClear() {
        return getTelemetry().isAutoClear();
    }

    /**
     * @see Telemetry#setAutoClear(boolean)
     */
    @Override
    public void setAutoClear(boolean autoClear) {
        getTelemetry().setAutoClear(autoClear);
    }

    /**
     * @see Telemetry#getMsTransmissionInterval()
     */
    @Override
    public int getMsTransmissionInterval() {
        return getTelemetry().getMsTransmissionInterval();
    }

    /**
     * @see Telemetry#setMsTransmissionInterval(int)
     */
    @Override
    public void setMsTransmissionInterval(int msTransmissionInterval) {
        getTelemetry().setMsTransmissionInterval(msTransmissionInterval);
    }

    /**
     * @see Telemetry#getItemSeparator()
     */
    @Override
    public String getItemSeparator() {
        return getTelemetry().getItemSeparator();
    }

    /**
     * @see Telemetry#setItemSeparator(String)
     */
    @Override
    public void setItemSeparator(String itemSeparator) {
        getTelemetry().setItemSeparator(itemSeparator);
    }

    /**
     * Sets the level for telemetry methods to log at
     * @param level the level for telemetry methods to log at
     */
    public void setTelemetryLevel(Level level) {
        getMainLogger().teleLevel = level;
    }

    /**
     * Gets the level used by the telemetry methods
     * @return  the level used by the telemetry methods
     */
    public Level getTelemetryLevel() {
        return getMainLogger().teleLevel;
    }

    /**
     * Gets the parent of this logger
     * @return  the parent of this logger. Null if there is not parent
     */
    public RobotLogger getParent() {
        return parent;
    }

    /**
     * Gets the internal telemetry that is used for logging
     * @return  the internal telemetry
     */
    protected Telemetry getTelemetry() {
        return getMainLogger().telemetry;
    }

    ////////// BEHAVIOR ////////////

    /**
     * @see Telemetry#addData(String, String, Object...)
     */
    @Override
    public Item addData(String caption, String format, Object... args) {
        return log(getTelemetryLevel(), caption, format, args);
    }

    /**
     * @see Telemetry#addData(String, Object)
     */
    @Override
    public Item addData(String caption, Object value) {
        return log(getTelemetryLevel(), caption, value);
    }

    /**
     * @see Telemetry#addData(String, Func)
     */
    @Override
    public <T> Item addData(String caption, Func<T> valueProducer) {
        return log(getTelemetryLevel(), caption, valueProducer);
    }

    /**
     * @see Telemetry#addData(String, String, Func)
     */
    @Override
    public <T> Item addData(String caption, String format, Func<T> valueProducer) {
        return log(getTelemetryLevel(), caption, format, valueProducer);
    }

    /**
     * @see Telemetry#removeItem(Item)
     */
    @Override
    public boolean removeItem(Item item) {
        return getTelemetry().removeItem(item);
    }

    /**
     * @see Telemetry#clear()
     */
    @Override
    public void clear() {
        getTelemetry().clear();
    }

    /**
     * @see Telemetry#clearAll()
     */
    @Override
    public void clearAll() {
        getTelemetry().clearAll();
    }

    /**
     * @see Telemetry#addAction(Runnable)
     */
    @Override
    public Object addAction(Runnable action) {
        return getTelemetry().addAction(action);
    }

    /**
     * @see Telemetry#removeAction(Object)
     */
    @Override
    public boolean removeAction(Object token) {
        return getTelemetry().removeAction(token);
    }

    /**
     * Updates the telemetry
     * @see Telemetry#update()
     */
    @Override
    public boolean update() {
        return getTelemetry().update();
    }

    /**
     * WARN: This method does not log the line or any of it's data to logcat
     * @see Telemetry#addLine()
     */
    @Override
    public Line addLine() {
        return getTelemetry().addLine();
    }

    /**
     * WARN: This method does not log the line or any of it's data to logcat
     * @see Telemetry#addLine(String)
     */
    @Override
    public Line addLine(String lineCaption) {
        lcOut(getTelemetryLevel(), lineCaption);

        if (shouldLog(getTelemetryLevel()))
            return getTelemetry().addLine(lineCaption);

        return null;
    }

    /**
     * @see Telemetry#removeLine(Line)
     */
    @Override
    public boolean removeLine(Line line) {
        return getTelemetry().removeLine(line);
    }

    // FATAL LOG METHODS

    // short hand

    /**
     * Logs the message at the fatal level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item f(String message) {
        return fatal(false, message);
    }

    /**
     * Logs the message at the fatal level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item f(boolean retain, String message) {
        return log(Level.FATAL, retain, message);
    }

    /**
     * Logs the message at the fatal level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item f(String caption, Object value) {
        return fatal(false, caption, value);
    }

    /**
     * Logs the message at the fatal level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item f(boolean retain, String caption, Object value) {
        return log(Level.FATAL, retain, caption, value);
    }

    /**
     * Logs the message at the fatal level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item f(String caption, String format, Object... args) {
        return fatal(false, caption, format, args);
    }

    /**
     * Logs the message at the fatal level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item f(boolean retain, String caption, String format, Object... args) {
        return log(Level.FATAL, retain, caption, format, args);
    }

    /**
     * Logs the message at the fatal level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item f(String caption, Func<T> valueProducer) {
        return fatal(false, caption, valueProducer);
    }

    /**
     * Logs the message at the fatal level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item f(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.FATAL, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the fatal level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item f(String caption, String format, Func<T> valueProducer) {
        return fatal(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the fatal level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item f(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.FATAL, retain, caption, valueProducer);
    }

    // long

    /**
     * Logs the message at the fatal level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatal(String message) {
        return fatal(false, message);
    }

    /**
     * Logs the message at the fatal level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatal(boolean retain, String message) {
        return log(Level.FATAL, retain, message);
    }

    /**
     * Logs the message at the fatal level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatal(String caption, Object value) {
        return fatal(false, caption, value);
    }

    /**
     * Logs the message at the fatal level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatal(boolean retain, String caption, Object value) {
        return log(Level.FATAL, retain, caption, value);
    }

    /**
     * Logs the message at the fatal level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatal(String caption, String format, Object... args) {
        return fatal(false, caption, format, args);
    }

    /**
     * Logs the message at the fatal level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatal(boolean retain, String caption, String format, Object... args) {
        return log(Level.FATAL, retain, caption, format, args);
    }

    /**
     * Logs the message at the fatal level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item fatal(String caption, Func<T> valueProducer) {
        return fatal(false, caption, valueProducer);
    }

    /**
     * Logs the message at the fatal level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item fatal(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.FATAL, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the fatal level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item fatal(String caption, String format, Func<T> valueProducer) {
        return fatal(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the fatal level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item fatal(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.FATAL, retain, caption, valueProducer);
    }

    // format short

    /**
     * Logs and formats the message at the fatal level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item ff(String format, Object... args) {
        return fatalf(false, format, args);
    }

    /**
     * Logs and formats the message at the fatal level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item ff(boolean retain, String format, Object... args) {
        return logf(Level.FATAL, retain, format, args);
    }

    /**
     * Logs and formats the message at the fatal level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item ff(String format, Func<T> valueProducer) {
        return fatalf(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the fatal level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item ff(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.FATAL, retain, format, valueProducer);
    }

    // format long

    /**
     * Logs and formats the message at the fatal level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatalf(String format, Object... args) {
        return fatalf(false, format, args);
    }

    /**
     * Logs and formats the message at the fatal level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatalf(boolean retain, String format, Object... args) {
        return logf(Level.FATAL, retain, format, args);
    }

    /**
     * Logs and formats the message at the fatal level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item fatalf(String format, Func<T> valueProducer) {
        return fatalf(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the fatal level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item fatalf(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.FATAL, retain, format, valueProducer);
    }

    // retain short

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fr(String message) {
        return fatal(true, message);
    }

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fr(String caption, Object value) {
        return fatal(true, caption, value);
    }

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fr(String caption, String format, Object... args) {
        return fatal(true, caption, format, args);
    }

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item fr(String caption, Func<T> valueProducer) {
        return fatal(true, caption, valueProducer);
    }

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item fr(String caption, String format, Func<T> valueProducer) {
        return fatal(true, caption, format, valueProducer);
    }

    // retain long

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatalr(String message) {
        return fatal(true, message);
    }

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatalr(String caption, Object value) {
        return fatal(true, caption, value);
    }

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item fatalr(String caption, String format, Object... args) {
        return fatal(true, caption, format, args);
    }

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item fatalr(String caption, Func<T> valueProducer) {
        return fatal(true, caption, valueProducer);
    }

    /**
     * Logs the message at the fatal level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item fatalr(String caption, String format, Func<T> valueProducer) {
        return fatal(true, caption, format, valueProducer);
    }

    // ERROR LOG METHODS

    // short hand

    /**
     * Logs the message at the error level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item e(String message) {
        return error(false, message);
    }

    /**
     * Logs the message at the error level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item e(boolean retain, String message) {
        return log(Level.ERROR, retain, message);
    }

    /**
     * Logs the message at the error level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item e(String caption, Object value) {
        return error(false, caption, value);
    }

    /**
     * Logs the message at the error level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item e(boolean retain, String caption, Object value) {
        return log(Level.ERROR, retain, caption, value);
    }

    /**
     * Logs the message at the error level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item e(String caption, String format, Object... args) {
        return error(false, caption, format, args);
    }

    /**
     * Logs the message at the error level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item e(boolean retain, String caption, String format, Object... args) {
        return log(Level.ERROR, retain, caption, format, args);
    }

    /**
     * Logs the message at the error level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item e(String caption, Func<T> valueProducer) {
        return error(false, caption, valueProducer);
    }

    /**
     * Logs the message at the error level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item e(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.ERROR, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the error level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item e(String caption, String format, Func<T> valueProducer) {
        return error(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the error level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item e(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.ERROR, retain, caption, valueProducer);
    }

    // long

    /**
     * Logs the message at the error level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item error(String message) {
        return error(false, message);
    }

    /**
     * Logs the message at the error level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item error(boolean retain, String message) {
        return log(Level.ERROR, retain, message);
    }

    /**
     * Logs the message at the error level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item error(String caption, Object value) {
        return error(false, caption, value);
    }

    /**
     * Logs the message at the error level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item error(boolean retain, String caption, Object value) {
        return log(Level.ERROR, retain, caption, value);
    }

    /**
     * Logs the message at the error level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item error(String caption, String format, Object... args) {
        return error(false, caption, format, args);
    }

    /**
     * Logs the message at the error level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item error(boolean retain, String caption, String format, Object... args) {
        return log(Level.ERROR, retain, caption, format, args);
    }

    /**
     * Logs the message at the error level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item error(String caption, Func<T> valueProducer) {
        return error(false, caption, valueProducer);
    }

    /**
     * Logs the message at the error level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item error(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.ERROR, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the error level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item error(String caption, String format, Func<T> valueProducer) {
        return error(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the error level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item error(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.ERROR, retain, caption, valueProducer);
    }

    // format short

    /**
     * Logs and formats the message at the error level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item ef(String format, Object... args) {
        return errorf(false, format, args);
    }

    /**
     * Logs and formats the message at the error level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item ef(boolean retain, String format, Object... args) {
        return logf(Level.ERROR, retain, format, args);
    }

    /**
     * Logs and formats the message at the error level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item ef(String format, Func<T> valueProducer) {
        return errorf(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the error level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item ef(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.ERROR, retain, format, valueProducer);
    }

    // format long

    /**
     * Logs and formats the message at the error level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item errorf(String format, Object... args) {
        return errorf(false, format, args);
    }

    /**
     * Logs and formats the message at the error level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item errorf(boolean retain, String format, Object... args) {
        return logf(Level.ERROR, retain, format, args);
    }

    /**
     * Logs and formats the message at the error level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item errorf(String format, Func<T> valueProducer) {
        return errorf(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the error level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item errorf(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.ERROR, retain, format, valueProducer);
    }

    // retain short

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item er(String message) {
        return error(true, message);
    }

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item er(String caption, Object value) {
        return error(true, caption, value);
    }

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item er(String caption, String format, Object... args) {
        return error(true, caption, format, args);
    }

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item er(String caption, Func<T> valueProducer) {
        return error(true, caption, valueProducer);
    }

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item er(String caption, String format, Func<T> valueProducer) {
        return error(true, caption, format, valueProducer);
    }

    // retain long

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item errorr(String message) {
        return error(true, message);
    }

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item errorr(String caption, Object value) {
        return error(true, caption, value);
    }

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item errorr(String caption, String format, Object... args) {
        return error(true, caption, format, args);
    }

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item errorr(String caption, Func<T> valueProducer) {
        return error(true, caption, valueProducer);
    }

    /**
     * Logs the message at the error level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item errorr(String caption, String format, Func<T> valueProducer) {
        return error(true, caption, format, valueProducer);
    }

    // WARN LOG METHODS

    // short hand

    /**
     * Logs the message at the warn level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item w(String message) {
        return warn(false, message);
    }

    /**
     * Logs the message at the warn level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item w(boolean retain, String message) {
        return log(Level.WARN, retain, message);
    }

    /**
     * Logs the message at the warn level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item w(String caption, Object value) {
        return warn(false, caption, value);
    }

    /**
     * Logs the message at the warn level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item w(boolean retain, String caption, Object value) {
        return log(Level.WARN, retain, caption, value);
    }

    /**
     * Logs the message at the warn level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item w(String caption, String format, Object... args) {
        return warn(false, caption, format, args);
    }

    /**
     * Logs the message at the warn level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item w(boolean retain, String caption, String format, Object... args) {
        return log(Level.WARN, retain, caption, format, args);
    }

    /**
     * Logs the message at the warn level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item w(String caption, Func<T> valueProducer) {
        return warn(false, caption, valueProducer);
    }

    /**
     * Logs the message at the warn level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item w(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.WARN, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the warn level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item w(String caption, String format, Func<T> valueProducer) {
        return warn(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the warn level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item w(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.WARN, retain, caption, valueProducer);
    }

    // long

    /**
     * Logs the message at the warn level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warn(String message) {
        return warn(false, message);
    }

    /**
     * Logs the message at the warn level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warn(boolean retain, String message) {
        return log(Level.WARN, retain, message);
    }

    /**
     * Logs the message at the warn level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warn(String caption, Object value) {
        return warn(false, caption, value);
    }

    /**
     * Logs the message at the warn level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warn(boolean retain, String caption, Object value) {
        return log(Level.WARN, retain, caption, value);
    }

    /**
     * Logs the message at the warn level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warn(String caption, String format, Object... args) {
        return warn(false, caption, format, args);
    }

    /**
     * Logs the message at the warn level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warn(boolean retain, String caption, String format, Object... args) {
        return log(Level.WARN, retain, caption, format, args);
    }

    /**
     * Logs the message at the warn level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item warn(String caption, Func<T> valueProducer) {
        return warn(false, caption, valueProducer);
    }

    /**
     * Logs the message at the warn level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item warn(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.WARN, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the warn level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item warn(String caption, String format, Func<T> valueProducer) {
        return warn(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the warn level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item warn(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.WARN, retain, caption, valueProducer);
    }

    // format short

    /**
     * Logs and formats the message at the warn level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item wf(String format, Object... args) {
        return warnf(false, format, args);
    }

    /**
     * Logs and formats the message at the warn level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item wf(boolean retain, String format, Object... args) {
        return logf(Level.WARN, retain, format, args);
    }

    /**
     * Logs and formats the message at the warn level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item wf(String format, Func<T> valueProducer) {
        return warnf(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the warn level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item wf(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.WARN, retain, format, valueProducer);
    }

    // format long

    /**
     * Logs and formats the message at the warn level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warnf(String format, Object... args) {
        return warnf(false, format, args);
    }

    /**
     * Logs and formats the message at the warn level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warnf(boolean retain, String format, Object... args) {
        return logf(Level.WARN, retain, format, args);
    }

    /**
     * Logs and formats the message at the warn level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item warnf(String format, Func<T> valueProducer) {
        return warnf(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the warn level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item warnf(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.WARN, retain, format, valueProducer);
    }

    // retain short

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item wr(String message) {
        return warn(true, message);
    }

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item wr(String caption, Object value) {
        return warn(true, caption, value);
    }

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item wr(String caption, String format, Object... args) {
        return warn(true, caption, format, args);
    }

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item wr(String caption, Func<T> valueProducer) {
        return warn(true, caption, valueProducer);
    }

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item wr(String caption, String format, Func<T> valueProducer) {
        return warn(true, caption, format, valueProducer);
    }

    // retain long

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warnr(String message) {
        return warn(true, message);
    }

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warnr(String caption, Object value) {
        return warn(true, caption, value);
    }

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item warnr(String caption, String format, Object... args) {
        return warn(true, caption, format, args);
    }

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item warnr(String caption, Func<T> valueProducer) {
        return warn(true, caption, valueProducer);
    }

    /**
     * Logs the message at the warn level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item warnr(String caption, String format, Func<T> valueProducer) {
        return warn(true, caption, format, valueProducer);
    }

    // INFO LOG METHODS

    // short hand

    /**
     * Logs the message at the info level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item i(String message) {
        return info(false, message);
    }

    /**
     * Logs the message at the info level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item i(boolean retain, String message) {
        return log(Level.INFO, retain, message);
    }

    /**
     * Logs the message at the info level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item i(String caption, Object value) {
        return info(false, caption, value);
    }

    /**
     * Logs the message at the info level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item i(boolean retain, String caption, Object value) {
        return log(Level.INFO, retain, caption, value);
    }

    /**
     * Logs the message at the info level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item i(String caption, String format, Object... args) {
        return info(false, caption, format, args);
    }

    /**
     * Logs the message at the info level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item i(boolean retain, String caption, String format, Object... args) {
        return log(Level.INFO, retain, caption, format, args);
    }

    /**
     * Logs the message at the info level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item i(String caption, Func<T> valueProducer) {
        return info(false, caption, valueProducer);
    }

    /**
     * Logs the message at the info level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item i(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.INFO, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the info level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item i(String caption, String format, Func<T> valueProducer) {
        return info(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the info level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item i(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.INFO, retain, caption, valueProducer);
    }

    // long

    /**
     * Logs the message at the info level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item info(String message) {
        return info(false, message);
    }

    /**
     * Logs the message at the info level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item info(boolean retain, String message) {
        return log(Level.INFO, retain, message);
    }

    /**
     * Logs the message at the info level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item info(String caption, Object value) {
        return info(false, caption, value);
    }

    /**
     * Logs the message at the info level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item info(boolean retain, String caption, Object value) {
        return log(Level.INFO, retain, caption, value);
    }

    /**
     * Logs the message at the info level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item info(String caption, String format, Object... args) {
        return info(false, caption, format, args);
    }

    /**
     * Logs the message at the info level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item info(boolean retain, String caption, String format, Object... args) {
        return log(Level.INFO, retain, caption, format, args);
    }

    /**
     * Logs the message at the info level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item info(String caption, Func<T> valueProducer) {
        return info(false, caption, valueProducer);
    }

    /**
     * Logs the message at the info level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item info(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.INFO, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the info level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item info(String caption, String format, Func<T> valueProducer) {
        return info(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the info level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item info(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.INFO, retain, caption, valueProducer);
    }

    // format long

    /**
     * Logs and formats the message at the info level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item infof(String format, Object... args) {
        return infof(false, format, args);
    }

    /**
     * Logs and formats the message at the info level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item infof(boolean retain, String format, Object... args) {
        return logf(Level.INFO, retain, format, args);
    }

    /**
     * Logs and formats the message at the info level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item infof(String format, Func<T> valueProducer) {
        return infof(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the info level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item infof(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.INFO, retain, format, valueProducer);
    }

    // retain short

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item ir(String message) {
        return info(true, message);
    }

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item ir(String caption, Object value) {
        return info(true, caption, value);
    }

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item ir(String caption, String format, Object... args) {
        return info(true, caption, format, args);
    }

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item ir(String caption, Func<T> valueProducer) {
        return info(true, caption, valueProducer);
    }

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item ir(String caption, String format, Func<T> valueProducer) {
        return info(true, caption, format, valueProducer);
    }

    // retain long

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item infor(String message) {
        return info(true, message);
    }

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item infor(String caption, Object value) {
        return info(true, caption, value);
    }

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item infor(String caption, String format, Object... args) {
        return info(true, caption, format, args);
    }

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item infor(String caption, Func<T> valueProducer) {
        return info(true, caption, valueProducer);
    }

    /**
     * Logs the message at the info level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item infor(String caption, String format, Func<T> valueProducer) {
        return info(true, caption, format, valueProducer);
    }

    // DEBUG LOG METHODS

    // short hand

    /**
     * Logs the message at the debug level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item d(String message) {
        return debug(false, message);
    }

    /**
     * Logs the message at the debug level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item d(boolean retain, String message) {
        return log(Level.DEBUG, retain, message);
    }

    /**
     * Logs the message at the debug level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item d(String caption, Object value) {
        return debug(false, caption, value);
    }

    /**
     * Logs the message at the debug level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item d(boolean retain, String caption, Object value) {
        return log(Level.DEBUG, retain, caption, value);
    }

    /**
     * Logs the message at the debug level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item d(String caption, String format, Object... args) {
        return debug(false, caption, format, args);
    }

    /**
     * Logs the message at the debug level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item d(boolean retain, String caption, String format, Object... args) {
        return log(Level.DEBUG, retain, caption, format, args);
    }

    /**
     * Logs the message at the debug level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item d(String caption, Func<T> valueProducer) {
        return debug(false, caption, valueProducer);
    }

    /**
     * Logs the message at the debug level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item d(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.DEBUG, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the debug level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item d(String caption, String format, Func<T> valueProducer) {
        return debug(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the debug level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item d(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.DEBUG, retain, caption, valueProducer);
    }

    // long

    /**
     * Logs the message at the debug level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debug(String message) {
        return debug(false, message);
    }

    /**
     * Logs the message at the debug level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debug(boolean retain, String message) {
        return log(Level.DEBUG, retain, message);
    }

    /**
     * Logs the message at the debug level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debug(String caption, Object value) {
        return debug(false, caption, value);
    }

    /**
     * Logs the message at the debug level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debug(boolean retain, String caption, Object value) {
        return log(Level.DEBUG, retain, caption, value);
    }

    /**
     * Logs the message at the debug level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debug(String caption, String format, Object... args) {
        return debug(false, caption, format, args);
    }

    /**
     * Logs the message at the debug level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debug(boolean retain, String caption, String format, Object... args) {
        return log(Level.DEBUG, retain, caption, format, args);
    }

    /**
     * Logs the message at the debug level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item debug(String caption, Func<T> valueProducer) {
        return debug(false, caption, valueProducer);
    }

    /**
     * Logs the message at the debug level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item debug(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.DEBUG, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the debug level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item debug(String caption, String format, Func<T> valueProducer) {
        return debug(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the debug level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item debug(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.DEBUG, retain, caption, valueProducer);
    }

    // format short

    /**
     * Logs and formats the message at the debug level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item df(String format, Object... args) {
        return debugf(false, format, args);
    }

    /**
     * Logs and formats the message at the debug level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item df(boolean retain, String format, Object... args) {
        return logf(Level.DEBUG, retain, format, args);
    }

    /**
     * Logs and formats the message at the debug level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item df(String format, Func<T> valueProducer) {
        return debugf(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the debug level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item df(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.DEBUG, retain, format, valueProducer);
    }

    // format long

    /**
     * Logs and formats the message at the debug level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debugf(String format, Object... args) {
        return debugf(false, format, args);
    }

    /**
     * Logs and formats the message at the debug level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debugf(boolean retain, String format, Object... args) {
        return logf(Level.DEBUG, retain, format, args);
    }

    /**
     * Logs and formats the message at the debug level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item debugf(String format, Func<T> valueProducer) {
        return debugf(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the debug level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item debugf(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.DEBUG, retain, format, valueProducer);
    }

    // retain short

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item dr(String message) {
        return debug(true, message);
    }

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item dr(String caption, Object value) {
        return debug(true, caption, value);
    }

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item dr(String caption, String format, Object... args) {
        return debug(true, caption, format, args);
    }

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item dr(String caption, Func<T> valueProducer) {
        return debug(true, caption, valueProducer);
    }

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item dr(String caption, String format, Func<T> valueProducer) {
        return debug(true, caption, format, valueProducer);
    }

    // retain long

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debugr(String message) {
        return debug(true, message);
    }

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debugr(String caption, Object value) {
        return debug(true, caption, value);
    }

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item debugr(String caption, String format, Object... args) {
        return debug(true, caption, format, args);
    }

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item debugr(String caption, Func<T> valueProducer) {
        return debug(true, caption, valueProducer);
    }

    /**
     * Logs the message at the debug level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item debugr(String caption, String format, Func<T> valueProducer) {
        return debug(true, caption, format, valueProducer);
    }

    // VERBOSE LOG METHODS

    // short hand

    /**
     * Logs the message at the verbose level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item v(String message) {
        return verbose(false, message);
    }

    /**
     * Logs the message at the verbose level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item v(boolean retain, String message) {
        return log(Level.VERBOSE, retain, message);
    }

    /**
     * Logs the message at the verbose level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item v(String caption, Object value) {
        return verbose(false, caption, value);
    }

    /**
     * Logs the message at the verbose level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item v(boolean retain, String caption, Object value) {
        return log(Level.VERBOSE, retain, caption, value);
    }

    /**
     * Logs the message at the verbose level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item v(String caption, String format, Object... args) {
        return verbose(false, caption, format, args);
    }

    /**
     * Logs the message at the verbose level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item v(boolean retain, String caption, String format, Object... args) {
        return log(Level.VERBOSE, retain, caption, format, args);
    }

    /**
     * Logs the message at the verbose level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item v(String caption, Func<T> valueProducer) {
        return verbose(false, caption, valueProducer);
    }

    /**
     * Logs the message at the verbose level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item v(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.VERBOSE, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the verbose level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item v(String caption, String format, Func<T> valueProducer) {
        return verbose(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the verbose level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item v(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.VERBOSE, retain, caption, valueProducer);
    }

    // long

    /**
     * Logs the message at the verbose level
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verbose(String message) {
        return verbose(false, message);
    }

    /**
     * Logs the message at the verbose level
     * @param retain    whether or not to retain the item in telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verbose(boolean retain, String message) {
        return log(Level.VERBOSE, retain, message);
    }

    /**
     * Logs the message at the verbose level
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verbose(String caption, Object value) {
        return verbose(false, caption, value);
    }

    /**
     * Logs the message at the verbose level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verbose(boolean retain, String caption, Object value) {
        return log(Level.VERBOSE, retain, caption, value);
    }

    /**
     * Logs the message at the verbose level
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verbose(String caption, String format, Object... args) {
        return verbose(false, caption, format, args);
    }

    /**
     * Logs the message at the verbose level
     * @param retain    whether or not to retain the item in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verbose(boolean retain, String caption, String format, Object... args) {
        return log(Level.VERBOSE, retain, caption, format, args);
    }

    /**
     * Logs the message at the verbose level
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item verbose(String caption, Func<T> valueProducer) {
        return verbose(false, caption, valueProducer);
    }

    /**
     * Logs the message at the verbose level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item verbose(boolean retain, String caption, Func<T> valueProducer) {
        return log(Level.VERBOSE, retain, caption, valueProducer);
    }

    /**
     * Logs the message at the verbose level
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item verbose(String caption, String format, Func<T> valueProducer) {
        return verbose(false, caption, format, valueProducer);
    }

    /**
     * Logs the message at the verbose level
     * @param retain        whether or not to retain the item in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item verbose(boolean retain, String caption, String format, Func<T> valueProducer) {
        return log(Level.VERBOSE, retain, caption, valueProducer);
    }

    // format short

    /**
     * Logs and formats the message at the verbose level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item vf(String format, Object... args) {
        return verbosef(false, format, args);
    }

    /**
     * Logs and formats the message at the verbose level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item vf(boolean retain, String format, Object... args) {
        return logf(Level.VERBOSE, retain, format, args);
    }

    /**
     * Logs and formats the message at the verbose level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item vf(String format, Func<T> valueProducer) {
        return verbosef(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the verbose level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item vf(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.VERBOSE, retain, format, valueProducer);
    }

    // format long

    /**
     * Logs and formats the message at the verbose level
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verbosef(String format, Object... args) {
        return verbosef(false, format, args);
    }

    /**
     * Logs and formats the message at the verbose level
     * @param retain    whether or not to retain the item in telemetry
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verbosef(boolean retain, String format, Object... args) {
        return logf(Level.VERBOSE, retain, format, args);
    }

    /**
     * Logs and formats the message at the verbose level
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item verbosef(String format, Func<T> valueProducer) {
        return verbosef(false, format, valueProducer);
    }

    /**
     * Logs and formats the message at the verbose level
     * @param retain        whether or not to retain the item in telemetry
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item verbosef(boolean retain, String format, Func<T> valueProducer) {
        return logf(Level.VERBOSE, retain, format, valueProducer);
    }

    // retain short

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item vr(String message) {
        return verbose(true, message);
    }

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item vr(String caption, Object value) {
        return verbose(true, caption, value);
    }

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item vr(String caption, String format, Object... args) {
        return verbose(true, caption, format, args);
    }

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item vr(String caption, Func<T> valueProducer) {
        return verbose(true, caption, valueProducer);
    }

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item vr(String caption, String format, Func<T> valueProducer) {
        return verbose(true, caption, format, valueProducer);
    }

    // retain long

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param message   the message
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verboser(String message) {
        return verbose(true, message);
    }

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verboser(String caption, Object value) {
        return verbose(true, caption, value);
    }

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the replacement values for the format
     * @return          the telemetry item. Null if not logged to telemetry
     */
    public Item verboser(String caption, String format, Object... args) {
        return verbose(true, caption, format, args);
    }

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item verboser(String caption, Func<T> valueProducer) {
        return verbose(true, caption, valueProducer);
    }

    /**
     * Logs the message at the verbose level and retains it in the telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item. Null if not logged to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item verboser(String caption, String format, Func<T> valueProducer) {
        return verbose(true, caption, format, valueProducer);
    }

    // GENERIC LOGGING

    /**
     * Logs out the message
     * @param level     the level to log at
     * @param message   the message to log
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, String message) {
        return log(level, false, message);
    }

    /**
     * Logs out the message
     * @param level     the level to log at
     * @param retain    whether or not to retain the log in telemetry
     * @param message   the message to log
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, boolean retain, String message) {
        lcOut(level, message);

        return tOut(level, retain, message);
    }

    /**
     * Logs out the message
     * @param level     the level to log at
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, String caption, Object value) {
        return log(level, false, caption, value);
    }

    /**
     * Logs out the message
     * @param level     the level to log at
     * @param retain    whether or not to retain the log in telemetry
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, boolean retain, String caption, Object value) {
        lcOut(level, caption, value);

        return tOut(level, retain, caption, value);
    }

    /**
     * Logs out the message
     * @param level     the level to log at
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, String caption, String format, Object... args) {
        return log(level, false, caption, format, args);
    }

    /**
     * Logs out the message
     * @param level     the level to log at
     * @param retain    whether or not to retain the log in telemetry
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, boolean retain, String caption, String format, Object... args) {
        lcOut(level, caption, format, args);

        return tOut(level, retain, caption, format, args);
    }

    /**
     * Logs out the message
     * @param level         the level to log at
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item log(Level level, String caption, Func<T> valueProducer) {
        return log(level, false, caption, valueProducer);
    }

    /**
     * Logs out the message
     * @param level         the level to log at
     * @param retain        whether or not to retain the log in telemetry
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item log(Level level, boolean retain, String caption, Func<T> valueProducer) {
        lcOut(level, caption, valueProducer);

        return tOut(level, retain, caption, valueProducer);
    }

    /**
     * Logs out the message
     * @param level         the level to log at
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item log(Level level, String caption, String format,  Func<T> valueProducer) {
        return log(level, false, caption, format, valueProducer);
    }

    /**
     * Logs out the message
     * @param level         the level to log at
     * @param retain        whether or not to retain the log in telemetry
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item log(Level level, boolean retain, String caption, String format,  Func<T> valueProducer) {
        lcOut(level, caption, format, valueProducer);

        return tOut(level, retain, caption, format, valueProducer);
    }

    /**
     * Logs and formats the message
     * @param level     the level to log at
     * @param format    the format of the message
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logf(Level level, String format, Object... args) {
        return logf(level, false, format, args);
    }

    /**
     * Logs and formats the message
     * @param level     the level to log at
     * @param retain    whether or not to retain the log in telemetry
     * @param format    the format of the message
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logf(Level level, boolean retain, String format, Object... args) {
        return log(level, retain, String.format(format, args));
    }

    /**
     * Logs and formats the message
     * @param level         the level to log at
     * @param format        the format of the message
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see #log(Level, String, String, Func)
     */
    public <T> Item logf(Level level, String format, Func<T> valueProducer) {
        return logf(level, false, format, valueProducer);
    }

    /**
     * Logs and formats the message
     * @param level         the level to log at
     * @param retain        whether or not to retain the log in telemetry
     * @param format        the format of the message
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see #log(Level, String, String, Func) 
     */
    public <T> Item logf(Level level, boolean retain, String format, Func<T> valueProducer) {
        return log(level, retain, String.format(format, valueProducer.value()));
    }

    private RobotLogger getMainLogger() {
        if (parent != null) return parent.getMainLogger();

        return this;
    }

    private String formatMessage(String caption, Object value) {
        return String.format("%s%s%s", caption, getCaptionValueSeparator(), value);
    }

    // HANDLING LOGCAT OUTPUT
    private void lcOut(Level level, String caption, String format, Object... args) {
        lcOut(level, caption, String.format(format, args));
    }

    private void lcOut(Level level, String caption, Object value) {
        lcOut(level, formatMessage(caption, value));
    }

    private <T> void lcOut(Level level, String caption, Func<T> valueProducer) {
        lcOut(level, formatMessage(caption, valueProducer.value()));
    }

    private <T> void lcOut(Level level, String caption, String format, Func<T> valueProducer) {
        lcOut(level, formatMessage(caption, String.format(format, valueProducer.value())));
    }

    private void lcOut(Level level, String message) {
        android.util.Log.println(level.priority, tag, message);
    }


    // HANDLING TELEMETRY OUTPUT
    private <T> Item tOut(Level level, boolean retain, String caption, Func<T> valueProducer) {
        if (shouldLog(level))
            return tLine(level).addData(caption, valueProducer).setRetained(retain);

        return null;
    }

    private <T> Item tOut(Level level, boolean retain, String caption, String format, Func<T> valueProducer) {
        if (shouldLog(level))
            return tLine(level).addData(caption, format, valueProducer).setRetained(retain);

        return null;
    }

    private Item tOut(Level level, boolean retain, String caption, String format, Object... args) {
        if (shouldLog(level))
            return tLine(level).addData(caption, format, args).setRetained(retain);

        return null;
    }

    private Item tOut(Level level, boolean retain, String caption, Object value) {
        if (shouldLog(level))
            return  tLine(level).addData(caption + getCaptionValueSeparator(), value)
                .setRetained(retain);

        return null;
    }

    private Item tOut(Level level, boolean retain, @NonNull String message) {
        if (shouldLog(level))
            return tLine(level).addData("", message).setRetained(retain);

        return null;
    }

    private Line tLine(Level level) {
        return getTelemetry().addLine("(" + tag + "/" + level.name() + ")");
    }

    private boolean shouldLog(Level level) {
        return getLevel().priority < level.priority;
    }
}
