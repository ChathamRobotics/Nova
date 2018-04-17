package org.chathamrobotics.nova.util.units;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/30/2018
 */

/**
 * A velocity unit
 */
@SuppressWarnings("unused")
public enum VelocityUnit implements Unit<VelocityUnit> {
    METER_PER_SECOND(DistanceUnit.METER, TimeUnit.SECOND),
    KILOMETER_PER_HOUR(DistanceUnit.KILOMETER, TimeUnit.HOUR),
    FEET_PER_SECOND(DistanceUnit.FEET, TimeUnit.SECOND),
    MILE_PER_HOUR(DistanceUnit.MILE, TimeUnit.HOUR);

    private final DistanceUnit distanceUnit;
    private final TimeUnit timeUnit;

    VelocityUnit(DistanceUnit distanceUnit, TimeUnit timeUnit) {
        this.distanceUnit = distanceUnit;
        this.timeUnit = timeUnit;
    }

    /**
     * Gets the distance unit for the velocity unit
     * @return  the distance unit for the velocity unit
     */
    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    /**
     * Gets the time unit for the velocity unit
     * @return  the time unit for the velocity unit
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * Converts from the current unit into meters per second
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMetersPerSecond(double value) {
        return convertTo(METER_PER_SECOND, value);
    }

    /**
     * Converts into the current unit from meters per second
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMetersPerSecond(double value) {
        return convertFrom(METER_PER_SECOND, value);
    }

    /**
     * Converts from the current unit into kilometers per hour
     * @param value the value to convert
     * @return      the converted value
     */
    public double toKilometersPerHour(double value) {
        return convertTo(KILOMETER_PER_HOUR, value);
    }

    /**
     * Converts into the current unit from kilometers per hour
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromKilometerPerHour(double value) {
        return convertFrom(KILOMETER_PER_HOUR, value);
    }

    /**
     * Converts from the current unit into feet per second
     * @param value the value to convert
     * @return      the converted value
     */
    public double toFeetPerSecond(double value) {
        return convertTo(FEET_PER_SECOND, value);
    }

    /**
     * Converts into the current unit from feet per second
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromFeetPerSecond(double value) {
        return convertFrom(FEET_PER_SECOND, value);
    }

    /**
     * Converts from the current unit into miles per hour
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMilesPerHour(double value) {
        return convertTo(MILE_PER_HOUR, value);
    }

    /**
     * Converts into the current unit from miles per hour
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMilesPerHour(double value) {
        return convertFrom(MILE_PER_HOUR, value);
    }

    /**
     * Converts from the current unit into the given unit
     * @param unit  the unit to convert to
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertTo(VelocityUnit unit, double value) {
        if (this == unit) return value;

        return timeUnit.convertFrom(unit.timeUnit, distanceUnit.convertTo(unit.distanceUnit, value));
    }

    /**
     * Converts from the given unit into the current unit
     * @param unit  the unit to convert from
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertFrom(VelocityUnit unit, double value) {
        return unit.convertTo(this, value);
    }
}
