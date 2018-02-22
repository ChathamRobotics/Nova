package org.chathamrobotics.nova.util.units;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/22/2018
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public enum AngularVelocityUnit implements Unit<AngularVelocityUnit> {
    REVOLUTIONS_PER_MINUTE,
    REVOLUTIONS_PER_SECOND,
    RADIANS_PER_SECOND,
    DEGREES_PER_SECOND;

    /**
     * Converts the value from the current unit to revolutions per minute
     * @param value the value to convert
     * @return      the converted value
     */
    public double toRPM(double value) {
        return toRevolutionsPerMinute(value);
    }

    /**
     * Converts the value from the current unit to revolutions per minute
     * @param value the value to convert
     * @return      the converted value
     */
    public double toRevolutionsPerMinute(double value) {
        return convertTo(REVOLUTIONS_PER_MINUTE, value);
    }

    /**
     * Converts the value from the current unit to revolutions per second
     * @param value the value to convert
     * @return      the converted value
     */
    public double toHertz(double value) {
        return toRevolutionsPerSecond(value);
    }

    /**
     * Converts the value from the current unit to revolutions per second
     * @param value the value to convert
     * @return      the converted value
     */
    public double toRevolutionsPerSecond(double value) {
        return convertTo(REVOLUTIONS_PER_SECOND, value);
    }

    /**
     * Converts the value from the current unit to radians per second
     * @param value the value to convert
     * @return      the converted value
     */
    public double toRadiansPerSecond(double value) {
        return convertTo(RADIANS_PER_SECOND, value);
    }

    /**
     * Converts the value from the current unit to degrees per second
     * @param value the value to convert
     * @return      the converted value
     */
    public double toDegreesPerSecond(double value) {
        return convertTo(DEGREES_PER_SECOND, value);
    }

    /**
     * Converts from the given unit to the current unit
     * @param unit  the unit to convert from
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertFrom(AngularVelocityUnit unit, double value) {
        return unit.convertTo(this, value);
    }

    /**
     * Converts from the current unit to the given unit
     * @param unit  the unit to convert to
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertTo(AngularVelocityUnit unit, double value) {
        if (unit == this) return value;

        switch (this) {
            case DEGREES_PER_SECOND:
                switch (unit) {
                    case REVOLUTIONS_PER_MINUTE:
                        return value / 6;
                    case REVOLUTIONS_PER_SECOND:
                        return value / 360;
                    case RADIANS_PER_SECOND:
                        return value * Math.PI / 180;
                }
            case RADIANS_PER_SECOND:
                switch (unit) {
                    case REVOLUTIONS_PER_MINUTE:
                        return value * 30 / Math.PI;
                    case REVOLUTIONS_PER_SECOND:
                        return value / (2 * Math.PI);
                    case DEGREES_PER_SECOND:
                        return value * 180 / Math.PI;
                }
            case REVOLUTIONS_PER_MINUTE:
                switch (unit) {
                    case REVOLUTIONS_PER_SECOND:
                        return value / 60;
                    case RADIANS_PER_SECOND:
                        return value * Math.PI / 30;
                    case DEGREES_PER_SECOND:
                        return value * 6;
                }
            case REVOLUTIONS_PER_SECOND:
                switch (unit) {
                    case REVOLUTIONS_PER_MINUTE:
                        return value * 60;
                    case RADIANS_PER_SECOND:
                        return value * 2 * Math.PI;
                    case DEGREES_PER_SECOND:
                        return value * 360;
                }
        }

        throw new RuntimeException("Unreachable statement");
    }
}
