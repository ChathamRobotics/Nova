package org.chathamrobotics.nova.util.units;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/23/2018
 */

/**
 * Units for angle measure. This does not normalize angles like {@link org.firstinspires.ftc.robotcore.external.navigation.AngleUnit}
 */
public enum AngleUnit implements Unit<AngleUnit> {
    RADIANS,
    DEGREES,
    REVOLUTIONS;

    private static final double TWO_PI = 2 * Math.PI;

    /**
     * Converts the given ftc unit angle to a nova unit angle
     * @param unit  the ftc unit angle
     * @return      the nova unit angle
     */
    public static AngleUnit fromFTCAngleUnit(org.firstinspires.ftc.robotcore.external.navigation.AngleUnit unit) {
        switch (unit) {
            case DEGREES:
                return AngleUnit.DEGREES;
            case RADIANS:
                return AngleUnit.RADIANS;
        }

        throw new RuntimeException("Unreachable statement");
    }

    /**
     * Converts the given value from the current unit to radians
     * @param value the value to convert
     * @return      the converted value
     */
    public double toRadians(double value) {
        return convertTo(RADIANS, value);
    }

    /**
     * Converts the given value from radians to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromRadians(double value) {
        return convertFrom(RADIANS, value);
    }

    /**
     * Converts the given value from the current unit to degrees
     * @param value the value to convert
     * @return      the converted value
     */
    public double toDegrees(double value) {
        return convertTo(DEGREES, value);
    }

    /**
     * Converts the given value from degrees to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromDegrees(double value) {
        return convertFrom(DEGREES, value);
    }

    /**
     * Converts the given value from the current unit to revolutions
     * @param value the value to convert
     * @return      the converted value
     */
    public double toRevolutions(double value) {
        return convertTo(REVOLUTIONS, value);
    }

    /**
     * Converts the given value from revolutions to the current unit
     * @param value the value to convert
     * @return      the converted value
     */
    public double fromRevolutions(double value) {
        return convertFrom(REVOLUTIONS, value);
    }

    /**
     * Converts a angular value from the current unit to the given unit
     * @param unit  the unit to convert to
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertTo(AngleUnit unit, double value) {
        if (this == unit) return value;

        switch (this) {
            case RADIANS:
                if (unit == DEGREES) return Math.toDegrees(value);
                else return value / TWO_PI;
            case DEGREES:
                if (unit == RADIANS) return Math.toRadians(value);
                else return value / 360;
            case REVOLUTIONS:
                if (unit == DEGREES) return value * 360;
                else return value * TWO_PI;
        }

        throw new RuntimeException("Unreachable");
    }

    /**
     * Converts a angular value from the given unit to the current unit
     * @param unit  the unit to convert from
     * @param value the value to convert
     * @return      the converted value
     */
    @Override
    public double convertFrom(AngleUnit unit, double value) {
        return unit.convertTo(this, value);
    }
}
