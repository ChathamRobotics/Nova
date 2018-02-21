package org.chathamrobotics.nova.util.units;

/**
 * Unit of a type of measurement. ie velocity
 * @param <U>   the units
 */
public interface Unit<U extends Unit> {
    /**
     * Converts a value from the current unit to the given unit
     * @param unit  the unit to convert to
     * @param value the value to convert
     * @return      the converted value
     */
    double convertTo(U unit, double value);

    /**
     * Converts a value from the given unit to the current unit
     * @param unit  the unit to convert from
     * @param value the value to convert
     * @return      the converted value
     */
    double convertFrom(U unit, double value);
}
