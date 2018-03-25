package org.chathamrobotics.nova.util.units;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/25/2018
 */

/**
 * A unit of distance
 */
@SuppressWarnings("unused")
public enum DistanceUnit implements Unit<DistanceUnit> {
    // SI
    METER,
    MILLIMETER(METER, 1e-3),
    CENTIMETER(METER, 1e-2),
    KILOMETER(METER, 1e3),

    // Imperial
    FEET,
    INCH(FEET, 1/12d),
    YARD(FEET, 3),
    MILE(FEET, 5280);

    private static final double METER_TO_FOOT = 3.28084;
    private static final double MILE_TO_FOOT = 5280;

    private final DistanceUnit baseUnit;
    private final double toBaseFactor;

    DistanceUnit() {
        this.baseUnit = this;
        this.toBaseFactor = 1;
    }

    DistanceUnit(DistanceUnit baseUnit, double toBaseFactor) {
        this.baseUnit = baseUnit;
        this.toBaseFactor = toBaseFactor;
    }

    /**
     * Checks whether or not the unit is from the metric system (international system)
     * @return  whether or not the unit is from the metric system
     */
    public boolean isSI() {
        return this == METER || this == MILLIMETER || this == CENTIMETER || this == KILOMETER;
    }

    /**
     * Checks whether or not the unit is from the imperial system (international system)
     * @return  whether or not the unit is from the imperial system
     */
    public boolean isImperial() {
        return ! isSI();
    }

    /**
     * Converts the value to millimeters
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMM(double value) {
        return toMillimeters(value);
    }

    /**
     * Converts the value from millimeters to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMM(double value) {
        return fromMillimeters(value);
    }

    /**
     * Converts the value to millimeters
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMillimeters(double value) {
        return convertTo(MILLIMETER, value);
    }

    /**
     * Converts the value from millimeters to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMillimeters(double value) {
        return convertFrom(MILLIMETER, value);
    }

    /**
     * Converts the value to centimeters
     * @param value the value to convert
     * @return      the converted value
     */
    public double toCM(double value) {
        return toCentimeters(value);
    }

    /**
     * Converts the value from centimeters to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromCM(double value) {
        return fromCentimeters(value);
    }

    /**
     * Converts the value to centimeters
     * @param value the value to convert
     * @return      the converted value
     */
    public double toCentimeters(double value) {
        return convertTo(CENTIMETER, value);
    }

    /**
     * Converts the value from centimeters to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromCentimeters(double value) {
        return convertFrom(CENTIMETER, value);
    }

    /**
     * Converts the value to meters
     * @param value the value to convert
     * @return      the converted value
     */
    public double toM(double value) {
        return toMeters(value);
    }

    /**
     * Converts the value from meters to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromM(double value) {
        return fromMeters(value);
    }

    /**
     * Converts the value to meters
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMeters(double value) {
        return convertTo(METER, value);
    }

    /**
     * Converts the value from meters to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMeters(double value) {
        return convertFrom(METER, value);
    }

    /**
     * Converts the value to kilometers
     * @param value the value to convert
     * @return      the converted value
     */
    public double toKM(double value) {
        return toKilometers(value);
    }

    /**
     * Converts the value from kilometers to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromKM(double value) {
        return fromKilometers(value);
    }

    /**
     * Converts the value to kilometers
     * @param value the value to convert
     * @return      the converted value
     */
    public double toKilometers(double value) {
        return convertTo(KILOMETER, value);
    }

    /**
     * Converts the value from kilometers to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromKilometers(double value) {
        return convertFrom(KILOMETER, value);
    }

    /**
     * Converts the value to inches
     * @param value the value to convert
     * @return      the converted value
     */
    public double toIn(double value) {
        return toInches(value);
    }

    /**
     * Converts the value from inches to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromIn(double value) {
        return fromInches(value);
    }

    /**
     * Converts the value to inches
     * @param value the value to convert
     * @return      the converted value
     */
    public double toInches(double value) {
        return convertTo(INCH, value);
    }

    /**
     * Converts the value from inches to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromInches(double value) {
        return convertFrom(INCH, value);
    }

    /**
     * Converts the value to feet
     * @param value the value to convert
     * @return      the converted value
     */
    public double toFt(double value) {
        return toFeet(value);
    }

    /**
     * Converts the value from feet to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromFt(double value) {
        return fromFeet(value);
    }

    /**
     * Converts the value to feet
     * @param value the value to convert
     * @return      the converted value
     */
    public double toFeet(double value) {
        return convertTo(FEET, value);
    }

    /**
     * Converts the value from feet to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromFeet(double value) {
        return convertFrom(FEET, value);
    }

    /**
     * Converts the value to yard
     * @param value the value to convert
     * @return      the converted value
     */
    public double toYd(double value) {
        return toYard(value);
    }

    /**
     * Converts the value from yard to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromYd(double value) {
        return fromYard(value);
    }

    /**
     * Converts the value to yard
     * @param value the value to convert
     * @return      the converted value
     */
    public double toYard(double value) {
        return convertTo(YARD, value);
    }

    /**
     * Converts the value from yard to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromYard(double value) {
        return convertFrom(YARD, value);
    }

    /**
     * Converts the value to mile
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMi(double value) {
        return toMile(value);
    }

    /**
     * Converts the value from mile to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMi(double value) {
        return fromMile(value);
    }

    /**
     * Converts the value to mile
     * @param value the value to convert
     * @return      the converted value
     */
    public double toMile(double value) {
        return convertTo(MILE, value);
    }

    /**
     * Converts the value from mile to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromMile(double value) {
        return convertFrom(MILE, value);
    }

    /**
     * Converts from the current unit into to the given unit
     * @param unit  the unit to convert to
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertTo(DistanceUnit unit, double value) {
        if (this == unit) return value;

        double inBase = value * this.toBaseFactor;

        // if are from same system
        if (unit.isSI() == this.isSI()) {
            return inBase / unit.toBaseFactor;
        }

        // if are from different systems
        return (unit.isSI() ? inBase / METER_TO_FOOT : inBase * METER_TO_FOOT) / unit.toBaseFactor;
    }

    /**
     * Converts from the given unit to the current unit
     * @param unit  the unit to convert from
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertFrom(DistanceUnit unit, double value) {
        return unit.convertTo(this, value);
    }
}
