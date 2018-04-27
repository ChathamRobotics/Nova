package org.chathamrobotics.nova.math;

import org.chathamrobotics.nova.util.units.AngleUnit;

public final class Angles {
    public final static AngleUnit DEFAULT_ANGLE_UNIT = AngleUnit.RADIANS;
    private final static double DOUBLE_DELTA = 1e-6;

    /**
     * Calculates the angle relative to the reference angle
     * @param reference the reference angle
     * @param angle     the angle to transform into a relative angle
     * @return          the relative angle
     */
    public static double relativeAngle(double reference, double angle) {
        return  relativeAngle(reference, angle, DEFAULT_ANGLE_UNIT);
    }

    /**
     * Calculates the angle relative to the reference angle
     * @param reference the reference angle
     * @param angle     the angle to transform into a relative angle
     * @param angleUnit the unit of the angles
     * @return          the relative angle
     */
    public static double relativeAngle(double reference, double angle, AngleUnit angleUnit) {
        return Math.abs(angle - reference + angleUnit.getFull()) % angleUnit.getFull();
    }

    /**
     * Calculates the shortest distance between the initial and final angles. Negative means clockwise
     * @param ini       the starting angle
     * @param fin       the final/target angle
     * @return          the distance
     */
    public static double shortestDistance(double ini, double fin) {
        return shortestDistance(ini, fin, DEFAULT_ANGLE_UNIT);
    }

    /**
     * Calculates the shortest distance between the initial and final angles. Negative means clockwise
     * @param ini       the starting angle
     * @param fin       the final/target angle
     * @param angleUnit the unit of the angles
     * @return          the distance
     */
    public static double shortestDistance(double ini, double fin, AngleUnit angleUnit) {
        double dis = Math.abs(fin - ini + angleUnit.getFull() / 2) % angleUnit.getFull() - angleUnit.getFull() / 2;

        // if dis == -180
        if (Math.abs(dis + angleUnit.getFull() / 2) < DOUBLE_DELTA) dis = angleUnit.getFull() / 2;

        return dis;
    }

    private Angles() {}
}
