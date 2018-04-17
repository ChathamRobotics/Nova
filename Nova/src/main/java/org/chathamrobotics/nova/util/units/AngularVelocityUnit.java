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
    REVOLUTIONS_PER_MINUTE(AngleUnit.REVOLUTIONS, TimeUnit.MINUTE),
    REVOLUTIONS_PER_SECOND(AngleUnit.REVOLUTIONS, TimeUnit.SECOND),
    RADIANS_PER_SECOND(AngleUnit.RADIANS, TimeUnit.SECOND),
    DEGREES_PER_SECOND(AngleUnit.DEGREES, TimeUnit.SECOND);

    private final AngleUnit angleUnit;
    private final TimeUnit timeUnit;

    AngularVelocityUnit(AngleUnit angleUnit, TimeUnit timeUnit) {
        this.angleUnit = angleUnit;
        this.timeUnit = timeUnit;
    }

    /**
     * Gets the angle unit for the angular velocity unit
     * @return  the angle unit for the angular velocity unit
     */
    public AngleUnit getAngleUnit() {
        return angleUnit;
    }

    /**
     * Gets the time unit for the angular velocity unit
     * @return  the time unit for the angular velocity unit
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * Converts the value from the current unit to revolutions per minute
     * @param value the value to convert
     * @return      the converted value
     */
    public double toRPM(double value) {
        return toRevolutionsPerMinute(value);
    }

    /**
     * Converts the value from revolutions per minute to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromRPM(double value) {
        return fromRevolutionsPerMinute(value);
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
     * Converts the value from revolutions per minute to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromRevolutionsPerMinute(double value) {
        return convertFrom(REVOLUTIONS_PER_MINUTE, value);
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
     * Converts the value from revolutions per second to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromHertz(double value) {
        return fromRevolutionsPerSecond(value);
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
     * Converts the value from revolutions per second to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromRevolutionsPerSecond(double value) {
        return convertFrom(REVOLUTIONS_PER_SECOND, value);
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
     * Converts the value from radians per second to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromRadiansPerSecond(double value) {
        return convertFrom(RADIANS_PER_SECOND, value);
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
     * Converts the value from degrees per second to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromDegreesPerSecond(double value) {
        return convertFrom(DEGREES_PER_SECOND, value);
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

        return timeUnit.convertFrom(unit.timeUnit, angleUnit.convertTo(unit.angleUnit, value));
    }
}
