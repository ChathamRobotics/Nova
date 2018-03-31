package org.chathamrobotics.nova.util.units;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/30/2018
 */

/**
 * A unit of time
 */
@SuppressWarnings("unused")
public enum TimeUnit implements Unit<TimeUnit> {
    SECOND,
    PICOSECONDS(1e-12),
    NANOSECOND(1e-9),
    MICROSECOND(1e-6),
    MILLISECOND(1e-3),
    MINUTE(60),
    HOUR(60*60);

    private final double toBaseFactor;

    TimeUnit() {
        toBaseFactor = 1;
    }

    TimeUnit(double toBaseFactor) {
        this.toBaseFactor = toBaseFactor;
    }

    /**
     * Converts the from the current unit value to seconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double toSeconds(double value) {
        return convertTo(SECOND, value);
    }

    /**
     * Converts the value to the current unit from seconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromSeconds(double value) {
        return convertFrom(SECOND, value);
    }

    /**
     * Converts the from the current unit value to picoseconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double toPicoseconds(double value) {
        return convertTo(PICOSECONDS, value);
    }

    /**
     * Converts the value to the current unit from picoseconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromPicoseconds(double value) {
        return convertFrom(PICOSECONDS, value);
    }
    /**
     * Converts the from the current unit value to nanoseconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double toNanoseconds(double value) {
        return convertTo(NANOSECOND, value);
    }

    /**
     * Converts the value to the current unit from nanoseconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromNanoseconds(double value) {
        return convertFrom(NANOSECOND, value);
    }

    /**
     * Converts the from the current unit value to microseconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMicroseconds(double value) {
        return convertTo(MICROSECOND, value);
    }

    /**
     * Converts the value to the current unit from microseconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMicroseconds(double value) {
        return convertFrom(MICROSECOND, value);
    }

    /**
     * Converts the from the current unit value to milliseconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMillisecond(double value) {
        return convertTo(MILLISECOND, value);
    }

    /**
     * Converts the value to the current unit from milliseconds
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMilliseconds(double value) {
        return convertFrom(MILLISECOND, value);
    }

    /**
     * Converts the from the current unit value to minutes
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMinutes(double value) {
        return convertTo(MINUTE, value);
    }

    /**
     * Converts the value to the current unit from minutes
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMinutes(double value) {
        return convertFrom(MINUTE, value);
    }

    /**
     * Converts the from the current unit value to hours
     * @param value the value to convert
     * @return      the converted value
     */
    public double toHours(double value) {
        return convertTo(HOUR, value);
    }

    /**
     * Converts the value to the current unit from hours
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromHours(double value) {
        return convertFrom(HOUR, value);
    }

    /**
     * Converts from the current unit into the given unit
     * @param unit  the unit to convert to
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertTo(TimeUnit unit, double value) {
        if (this == unit) return value;

        return value * this.toBaseFactor / unit.toBaseFactor;
    }

    /**
     * Converts from the given unit to the current unit
     * @param unit  the unit to convert from
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertFrom(TimeUnit unit, double value) {
        return unit.convertTo(this, value);
    }
}
