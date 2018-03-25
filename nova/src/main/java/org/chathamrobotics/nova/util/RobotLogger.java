package org.chathamrobotics.nova.util;

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

    /**
     * Creates logging methods for level
     */
    public static class LevelLogger {
        private final Level level;
        private final RobotLogger parent;

        /**
         * Creates a new instance of {@link LevelLogger}
         * @param level     the level to log at
         * @param parent    the parent logger
         */
        public LevelLogger(Level level, RobotLogger parent) {
            this.level = level;
            this.parent = parent;
        }

        /**
         * Logs out the message
         * @param message   the message to log
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(String message) {
            return log(false, message);
        }

        /**
         * Logs out the message
         * @param throwable the exception to log
         * @param message   the message to log
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(Throwable throwable, String message) {
            return log(false, throwable, message);
        }

        /**
         * Logs out the message
         * @param retain    whether or not to retain the log in telemetry
         * @param message   the message to log
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(boolean retain, String message) {
            return parent.log(level, retain, message);
        }

        /**
         * Logs out the message
         * @param retain    whether or not to retain the log in telemetry
         * @param throwable the exception to log
         * @param message   the message to log
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(boolean retain, Throwable throwable, String message) {
            return parent.log(level, retain, throwable, message);
        }

        /**
         * Logs out the message
         * @param caption   the message caption
         * @param value     the message value
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(String caption, Object value) {
            return log(false, caption, value);
        }

        /**
         * Logs out the message
         * @param throwable the exception to log
         * @param caption   the message caption
         * @param value     the message value
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(Throwable throwable, String caption, Object value) {
            return log(false, throwable, caption, value);
        }

        /**
         * Logs out the message
         * @param retain    whether or not to retain the log in telemetry
         * @param caption   the message caption
         * @param value     the message value
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(boolean retain, String caption, Object value) {
            return parent.log(level, retain, caption, value);
        }

        /**
         * Logs out the message
         * @param retain    whether or not to retain the log in telemetry
         * @param throwable the exception to log
         * @param caption   the message caption
         * @param value     the message value
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(boolean retain, Throwable throwable, String caption, Object value) {
            return parent.log(level, retain, throwable, caption, value);
        }

        /**
         * Logs out the message
         * @param caption   the message caption
         * @param format    the message format
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(String caption, String format, Object... args) {
            return log(false, caption, format, args);
        }

        /**
         * Logs out the message
         * @param throwable the exception to log
         * @param caption   the message caption
         * @param format    the message format
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(Throwable throwable, String caption, String format, Object... args) {
            return log(false, throwable, caption, format, args);
        }

        /**
         * Logs out the message
         * @param retain    whether or not to retain the log in telemetry
         * @param caption   the message caption
         * @param format    the message format
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(boolean retain, String caption, String format, Object... args) {
            return parent.log(level, retain, caption, format, args);
        }

        /**
         * Logs out the message
         * @param retain    whether or not to retain the log in telemetry
         * @param throwable the exception to log
         * @param caption   the message caption
         * @param format    the message format
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item log(boolean retain, Throwable throwable, String caption, String format, Object... args) {
            return parent.log(level, retain, throwable, caption, format, args);
        }

        /**
         * Logs out the message
         * @param caption       the message caption
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, Func)
         */
        public <T> Item log(String caption, Func<T> valueProducer) {
            return log(false, caption, valueProducer);
        }

        /**
         * Logs out the message
         * @param throwable the exception to log
         * @param caption       the message caption
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, Func)
         */
        public <T> Item log(Throwable throwable, String caption, Func<T> valueProducer) {
            return log(false, throwable, caption, valueProducer);
        }

        /**
         * Logs out the message
         * @param retain        whether or not to retain the log in telemetry
         * @param caption       the message caption
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, Func)
         */
        public <T> Item log(boolean retain, String caption, Func<T> valueProducer) {
            return parent.logf(level, retain, caption, valueProducer);
        }

        /**
         * Logs out the message
         * @param retain        whether or not to retain the log in telemetry
         * @param throwable     the exception to log
         * @param caption       the message caption
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, Func)
         */
        public <T> Item log(boolean retain, Throwable throwable, String caption, Func<T> valueProducer) {
            return parent.log(level, retain, throwable, caption, valueProducer);
        }

        /**
         * Logs out the message
         * @param caption       the message caption
         * @param format        the message format
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, String, Func)
         */
        public <T> Item log(String caption, String format,  Func<T> valueProducer) {
            return log(false, caption, format, valueProducer);
        }

        /**
         * Logs out the message
         * @param throwable     the exception to log
         * @param caption       the message caption
         * @param format        the message format
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, String, Func)
         */
        public <T> Item log(Throwable throwable, String caption, String format,  Func<T> valueProducer) {
            return log(false, throwable, caption, format, valueProducer);
        }

        /**
         * Logs out the message
         * @param retain        whether or not to retain the log in telemetry
         * @param caption       the message caption
         * @param format        the message format
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, String, Func)
         */
        public <T> Item log(boolean retain, String caption, String format,  Func<T> valueProducer) {
            return parent.logf(level, retain, caption, format, valueProducer);
        }

        /**
         * Logs out the message
         * @param retain        whether or not to retain the log in telemetry
         * @param throwable     the exception to log
         * @param caption       the message caption
         * @param format        the message format
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, String, Func)
         */
        public <T> Item log(boolean retain, Throwable throwable, String caption, String format,  Func<T> valueProducer) {
            return  parent.log(level, retain, throwable, caption, format, valueProducer);
        }

        /**
         * Logs and formats the message
         * @param format    the format of the message
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logf(String format, Object... args) {
            return logf(false, format, args);
        }

        /**
         * Logs and formats the message
         * @param throwable the exception to log
         * @param format    the format of the message
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logf(Throwable throwable, String format, Object... args) {
            return logf(false, throwable, format, args);
        }

        /**
         * Logs and formats the message
         * @param retain    whether or not to retain the log in telemetry
         * @param format    the format of the message
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logf(boolean retain, String format, Object... args) {
            return log(retain, String.format(format, args));
        }

        /**
         * Logs and formats the message
         * @param retain    whether or not to retain the log in telemetry
         * @param throwable the exception to log
         * @param format    the format of the message
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logf(boolean retain, Throwable throwable, String format, Object... args) {
            return log(retain, throwable, String.format(format, args));
        }

        /**
         * Logs and formats the message
         * @param format        the format of the message
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see #log(Level, String, String, Func)
         */
        public <T> Item logf(String format, Func<T> valueProducer) {
            return logf(false, format, valueProducer);
        }

        /**
         * Logs and formats the message
         * @param throwable     the exception to log
         * @param format        the format of the message
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see #log(Level, String, String, Func)
         */
        public <T> Item logf(Throwable throwable, String format, Func<T> valueProducer) {
            return logf(false, throwable, format, valueProducer);
        }

        /**
         * Logs and formats the message
         * @param retain        whether or not to retain the log in telemetry
         * @param format        the format of the message
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see #log(Level, String, String, Func)
         */
        public <T> Item logf(boolean retain, String format, Func<T> valueProducer) {
            return log(retain, String.format(format, valueProducer.value()));
        }

        /**
         * Logs and formats the message
         * @param retain        whether or not to retain the log in telemetry
         * @param throwable the exception to log
         * @param format        the format of the message
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see #log(Level, String, String, Func)
         */
        public <T> Item logf(boolean retain, Throwable throwable, String format, Func<T> valueProducer) {
            return log(retain, throwable, String.format(format, valueProducer.value()));
        }

        /**
         * Logs out the message and retains it
         * @param message   the message to log
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logr(String message) {
            return log(true, message);
        }

        /**
         * Logs out the message and retains it
         * @param throwable the exception to log
         * @param message   the message to log
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logr(Throwable throwable, String message) {
            return log(true, throwable, message);
        }

        /**
         * Logs out the message and retains it
         * @param caption   the message caption
         * @param value     the message value
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logr(String caption, Object value) {
            return log(true, caption, value);
        }

        /**
         * Logs out the message and retains it
         * @param throwable the exception to log
         * @param caption   the message caption
         * @param value     the message value
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logr(Throwable throwable, String caption, Object value) {
            return log(true, throwable, caption, value);
        }

        /**
         * Logs out the message and retains it
         * @param caption   the message caption
         * @param format    the message format
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logr(String caption, String format, Object... args) {
            return log(true, caption, format, args);
        }

        /**
         * Logs out the message and retains it
         * @param throwable the exception to log
         * @param caption   the message caption
         * @param format    the message format
         * @param args      the values to replace the format
         * @return          the telemetry item for the log. Null if not outputted to telemetry
         */
        public Item logr(Throwable throwable, String caption, String format, Object... args) {
            return log(true, throwable, caption, format, args);
        }

        /**
         * Logs out the message and retains it
         * @param caption       the message caption
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, Func)
         */
        public <T> Item logr(String caption, Func<T> valueProducer) {
            return log(true, caption, valueProducer);
        }

        /**
         * Logs out the message and retains it
         * @param throwable the exception to log
         * @param caption       the message caption
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, Func)
         */
        public <T> Item logr(Throwable throwable, String caption, Func<T> valueProducer) {
            return log(true, throwable, caption, valueProducer);
        }

        /**
         * Logs out the message and retains it
         * @param caption       the message caption
         * @param format        the message format
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, String, Func)
         */
        public <T> Item logr(String caption, String format,  Func<T> valueProducer) {
            return log(true, caption, format, valueProducer);
        }

        /**
         * Logs out the message and retains it
         * @param throwable     the exception to log
         * @param caption       the message caption
         * @param format        the message format
         * @param valueProducer a function that produces a value
         * @return              the telemetry item for the log. Null if not outputted to telemetry
         * @see Telemetry#addData(String, String, Func)
         */
        public <T> Item logr(Throwable throwable, String caption, String format,  Func<T> valueProducer) {
            return log(true, throwable, caption, format, valueProducer);
        }
    }

    ////////// FIELDS ///////////////
    private final Telemetry telemetry;
    private final RobotLogger parent;
    private final String tag;

    /**
     * Logging at the fatal level
     */
    public final LevelLogger fatal = new LevelLogger(Level.FATAL, this), f = fatal;

    /**
     * Logging at the error level
     */
    public final LevelLogger error = new LevelLogger(Level.ERROR, this), e = error;

    /**
     * Logging at the warning level
     */
    public final LevelLogger warn = new LevelLogger(Level.WARN, this), w = warn;

    /**
     * Logging at the info level
     */
    public final LevelLogger info = new LevelLogger(Level.INFO, this), i = info;

    /**
     * Logging at the debug level
     */
    public final LevelLogger debug = new LevelLogger(Level.DEBUG, this), d = debug;

    /**
     * Logging at the verbose level
     */
    public final LevelLogger verbose = new LevelLogger(Level.VERBOSE, this), v = verbose;

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
     * Creates a new child logger
     * @param tag   the tag for the child logger
     * @return      the child logger
     */
    public RobotLogger child(String tag) {
        return new RobotLogger(tag, this);
    }

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
     * @param throwable the exception to log
     * @param message   the message to log
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, Throwable throwable, String message) {
        return log(level, false, throwable, message);
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
     * @param retain    whether or not to retain the log in telemetry
     * @param throwable the exception to log
     * @param message   the message to log
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, boolean retain, Throwable throwable, String message) {
        lcOut(level, throwable, message);

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
     * @param throwable the exception to log
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, Throwable throwable, String caption, Object value) {
        return log(level, false, throwable, caption, value);
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
     * @param retain    whether or not to retain the log in telemetry
     * @param throwable the exception to log
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, boolean retain, Throwable throwable, String caption, Object value) {
        lcOut(level, throwable, caption, value);

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
     * @param throwable the exception to log
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, Throwable throwable, String caption, String format, Object... args) {
        return log(level, false, throwable, caption, format, args);
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
     * @param level     the level to log at
     * @param retain    whether or not to retain the log in telemetry
     * @param throwable the exception to log
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item log(Level level, boolean retain, Throwable throwable, String caption, String format, Object... args) {
        lcOut(level, throwable, caption, format, args);

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
     * @param throwable the exception to log
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item log(Level level, Throwable throwable, String caption, Func<T> valueProducer) {
        return log(level, false, throwable, caption, valueProducer);
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
     * @param retain        whether or not to retain the log in telemetry
     * @param throwable     the exception to log
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item log(Level level, boolean retain, Throwable throwable, String caption, Func<T> valueProducer) {
        lcOut(level, throwable, caption, valueProducer);

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
     * @param throwable     the exception to log
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item log(Level level, Throwable throwable, String caption, String format,  Func<T> valueProducer) {
        return log(level, false, throwable, caption, format, valueProducer);
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
     * Logs out the message
     * @param level         the level to log at
     * @param retain        whether or not to retain the log in telemetry
     * @param throwable the exception to log
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item log(Level level, boolean retain, Throwable throwable, String caption, String format,  Func<T> valueProducer) {
        lcOut(level, throwable, caption, format, valueProducer);

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
     * @param throwable the exception to log
     * @param format    the format of the message
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logf(Level level, Throwable throwable, String format, Object... args) {
        return logf(level, false, throwable, format, args);
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
     * @param level     the level to log at
     * @param retain    whether or not to retain the log in telemetry
     * @param throwable the exception to log
     * @param format    the format of the message
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logf(Level level, boolean retain, Throwable throwable, String format, Object... args) {
        return log(level, retain, throwable, String.format(format, args));
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
     * @param throwable     the exception to log
     * @param format        the format of the message
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see #log(Level, String, String, Func)
     */
    public <T> Item logf(Level level, Throwable throwable, String format, Func<T> valueProducer) {
        return logf(level, false, throwable, format, valueProducer);
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

    /**
     * Logs and formats the message
     * @param level         the level to log at
     * @param retain        whether or not to retain the log in telemetry
     * @param throwable the exception to log
     * @param format        the format of the message
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see #log(Level, String, String, Func)
     */
    public <T> Item logf(Level level, boolean retain, Throwable throwable, String format, Func<T> valueProducer) {
        return log(level, retain, throwable, String.format(format, valueProducer.value()));
    }

    /**
     * Logs out the message and retains it
     * @param level     the level to log at
     * @param message   the message to log
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logr(Level level, String message) {
        return log(level, true, message);
    }

    /**
     * Logs out the message and retains it
     * @param level     the level to log at
     * @param throwable the exception to log
     * @param message   the message to log
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logr(Level level, Throwable throwable, String message) {
        return log(level, true, throwable, message);
    }

    /**
     * Logs out the message and retains it
     * @param level     the level to log at
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logr(Level level, String caption, Object value) {
        return log(level, true, caption, value);
    }

    /**
     * Logs out the message and retains it
     * @param level     the level to log at
     * @param throwable the exception to log
     * @param caption   the message caption
     * @param value     the message value
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logr(Level level, Throwable throwable, String caption, Object value) {
        return log(level, true, throwable, caption, value);
    }

    /**
     * Logs out the message and retains it
     * @param level     the level to log at
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logr(Level level, String caption, String format, Object... args) {
        return log(level, true, caption, format, args);
    }

    /**
     * Logs out the message and retains it
     * @param level     the level to log at
     * @param throwable the exception to log
     * @param caption   the message caption
     * @param format    the message format
     * @param args      the values to replace the format
     * @return          the telemetry item for the log. Null if not outputted to telemetry
     */
    public Item logr(Level level, Throwable throwable, String caption, String format, Object... args) {
        return log(level, true, throwable, caption, format, args);
    }

    /**
     * Logs out the message and retains it
     * @param level         the level to log at
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item logr(Level level, String caption, Func<T> valueProducer) {
        return log(level, true, caption, valueProducer);
    }

    /**
     * Logs out the message and retains it
     * @param level         the level to log at
     * @param throwable the exception to log
     * @param caption       the message caption
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, Func)
     */
    public <T> Item logr(Level level, Throwable throwable, String caption, Func<T> valueProducer) {
        return log(level, true, throwable, caption, valueProducer);
    }

    /**
     * Logs out the message and retains it
     * @param level         the level to log at
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item logr(Level level, String caption, String format,  Func<T> valueProducer) {
        return log(level, true, caption, format, valueProducer);
    }

    /**
     * Logs out the message and retains it
     * @param level         the level to log at
     * @param throwable     the exception to log
     * @param caption       the message caption
     * @param format        the message format
     * @param valueProducer a function that produces a value
     * @return              the telemetry item for the log. Null if not outputted to telemetry
     * @see Telemetry#addData(String, String, Func)
     */
    public <T> Item logr(Level level, Throwable throwable, String caption, String format,  Func<T> valueProducer) {
        return log(level, true, throwable, caption, format, valueProducer);
    }

    private RobotLogger getMainLogger() {
        if (parent != null) return parent.getMainLogger();

        return this;
    }

    private String formatMessage(String caption, Object value) {
        return String.format("%s%s%s", caption, getCaptionValueSeparator(), value);
    }

    // HANDLING LOGCAT OUTPUT
    private void lcOut(Level level, Throwable throwable, String caption, String format, Object... args) {
        lcOut(level, caption, format, args);
        lcOut(level, throwable);
    }

    private void lcOut(Level level, String caption, String format, Object... args) {
        lcOut(level, caption, String.format(format, args));
    }

    private void lcOut(Level level, Throwable throwable, String caption, Object value) {
        lcOut(level, caption, value);
        lcOut(level, throwable);
    }

    private void lcOut(Level level, String caption, Object value) {
        lcOut(level, formatMessage(caption, value));
    }

    private <T> void lcOut(Level level, Throwable throwable, String caption, Func<T> valueProducer) {
        lcOut(level, caption, valueProducer);
        lcOut(level, throwable);
    }

    private <T> void lcOut(Level level, String caption, Func<T> valueProducer) {
        lcOut(level, formatMessage(caption, valueProducer.value()));
    }

    private <T> void lcOut(Level level, Throwable throwable, String caption, String format, Func<T> valueProducer) {
        lcOut(level, caption, format, valueProducer);
        lcOut(level, throwable);
    }

    private <T> void lcOut(Level level, String caption, String format, Func<T> valueProducer) {
        lcOut(level, formatMessage(caption, String.format(format, valueProducer.value())));
    }

    private void lcOut(Level level, Throwable throwable, String message) {
        lcOut(level, message);
        lcOut(level, throwable);
    }

    private void lcOut(Level level, String message) {
        android.util.Log.println(level.priority, tag, message);
    }

    private void lcOut(Level level, Throwable thr) {
        lcOut(level, android.util.Log.getStackTraceString(thr));
    }

    // HANDLING TELEMETRY OUTPUT
    private <T> Item tOut(Level level, boolean retain, Throwable throwable, String caption, Func<T> valueProducer) {
        if (shouldLog(level))
            return tLine(level).addData(caption, valueProducer).setRetained(retain);

        return null;
    }

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

    private Item tOut(Level level, boolean retain, String message) {
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
