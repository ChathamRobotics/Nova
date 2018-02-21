package org.chathamrobotics.nova.util.units;


import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

public enum AngularVelocityUnit implements Unit<AngularVelocityUnit> {
    REVOLUTIONS_PER_MINUTE,
    REVOLUTIONS_PER_SECOND,
    RADIANS_PER_SECOND,
    DEGREES_PER_SECOND;

    public double toRPM(double value) {
        return toRevolutionsPerMinute(value);
    }

    public double toRevolutionsPerMinute(double value) {
        return convertTo(REVOLUTIONS_PER_MINUTE, value);
    }

    public double toHertz(double value) {
        return toRevolutionsPerSecond(value);
    }

    public double toRevolutionsPerSecond(double value) {
        return convertTo(REVOLUTIONS_PER_SECOND, value);
    }

    public double toRadiansPerSecond(double value) {
        return convertTo(RADIANS_PER_SECOND, value);
    }

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
