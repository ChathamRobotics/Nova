package org.chathamrobotics.nova.system;

/*!
 * nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 4/16/2018
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.chathamrobotics.nova.hardware.MotorEncoder;
import org.chathamrobotics.nova.util.RobotLogger;
import org.chathamrobotics.nova.util.units.AngleUnit;
import org.chathamrobotics.nova.util.units.DistanceUnit;
import org.chathamrobotics.nova.util.units.VelocityUnit;

/**
 * A encoded version of holonomic driver
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class EncodedHolonomicDrive extends HolonomicDrive {
    public static final VelocityUnit DEFAULT_VELOCITY_UNIT = VelocityUnit.METER_PER_SECOND;
    public static final AngleUnit DEFAULT_DIRECTION_UNIT = AngleUnit.RADIANS;
    public static final DistanceUnit DEFAULT_DISTANCE_UNIT = DistanceUnit.METER;

    private final double wheelRadius;
    private final GyroSensor gyro;
    private final MotorEncoder frontLeftEncoder, frontRightEncoder, backRightEncoder, backLeftEncoder;

    /**
     * Creates a new instance of {@link EncodedHolonomicDrive}
     * @param wheelDiameter the diameter of the wheel
     * @param diameterUnit  the unit of the diameter
     * @param gyro          the gyro sensor
     * @param frontLeft     the front left drive motor
     * @param frontRight    the front right drive motor
     * @param backRight     the back right drive motor
     * @param backLeft      the back left drive motor
     * @param logger        the robot logger
     */
    public EncodedHolonomicDrive(
            double wheelDiameter,
            DistanceUnit diameterUnit,
            GyroSensor gyro,
            DcMotor frontLeft,
            DcMotor frontRight,
            DcMotor backRight,
            DcMotor backLeft,
            RobotLogger logger
    ) {
        super(frontLeft, frontRight, backRight, backLeft, logger);

        wheelRadius = diameterUnit.toMeters(wheelDiameter);
        this.gyro = gyro;

        if (frontLeft.getMotorType() != frontRight.getMotorType()
                || backLeft.getMotorType() != backRight.getMotorType()
                || frontLeft.getMotorType() != backLeft.getMotorType())
            throw new IllegalArgumentException("All of the motors must be of the same type");

        frontLeftEncoder = new MotorEncoder(frontLeft);
        frontRightEncoder = new MotorEncoder(frontRight);
        backRightEncoder = new MotorEncoder(backRight);
        backLeftEncoder = new MotorEncoder(backLeft);
    }

    /**
     * Sets the velocity of the robot
     * @param velocity      the velocity to set in meters per second
     * @param direction     the direction to apply the velocity in radians
     */
    public void setVelocity(double velocity, double direction) {
        setVelocity(velocity, DEFAULT_VELOCITY_UNIT, direction, DEFAULT_DIRECTION_UNIT);
    }

    /**
     * Sets the velocity of the robot
     * @param velocity      the velocity to set
     * @param velocityUnit  the unit of the velocity
     * @param direction     the direction to apply the velocity in radians
     */
    public void setVelocity(double velocity, VelocityUnit velocityUnit, double direction) {
        setVelocity(velocity, velocityUnit, direction, DEFAULT_DIRECTION_UNIT);
    }

    /**
     * Sets the velocity of the robot
     * @param velocity      the velocity to set in meters per second
     * @param direction     the direction to apply the velocity
     * @param directionUnit the unit of the direction
     */
    public void setVelocity(double velocity, double direction, AngleUnit directionUnit) {
        setVelocity(velocity, DEFAULT_VELOCITY_UNIT, direction, directionUnit);
    }

    /**
     * Sets the velocity of the robot
     * @param velocity      the velocity to set
     * @param velocityUnit  the unit of the velocity
     * @param direction     the direction to apply the velocity
     * @param directionUnit the unit of the direction
     */
    public void setVelocity(double velocity, VelocityUnit velocityUnit, double direction, AngleUnit directionUnit) {
        velocity = velocityUnit.getTimeUnit().convertFrom(
                MotorEncoder.DEFAULT_VELOCITY_UNIT.getTimeUnit(),
                linearToAngular(velocity, velocityUnit.getDistanceUnit(), MotorEncoder.DEFAULT_VELOCITY_UNIT.getAngleUnit())
        );
        double[] motorVelocities = calcMotorValues(velocity, directionUnit.toRadians(direction), 0);

        frontLeftEncoder.setVelocity(motorVelocities[0]);
        frontRightEncoder.setVelocity(motorVelocities[1]);
        backRightEncoder.setVelocity(motorVelocities[2]);
        backLeftEncoder.setVelocity(motorVelocities[3]);
    }

    /**
     * Gets the velocity of the robot
     * @return              the velocity in meters per second
     */
    public double getVelocity() {
        return getVelocity(DEFAULT_VELOCITY_UNIT);
    }

    /**
     * Gets the velocity of the robot
     * @param velocityUnit  the unit to return the velocity in
     * @return              the velocity
     */
    public double getVelocity(VelocityUnit velocityUnit) {
        return MotorEncoder.DEFAULT_VELOCITY_UNIT.getTimeUnit().convertFrom(velocityUnit.getTimeUnit(), angularToLinear(calcMagnitude(
                frontLeftEncoder.getVelocity(),
                frontRightEncoder.getVelocity(),
                backRightEncoder.getVelocity(),
                backLeftEncoder.getVelocity()
        ), MotorEncoder.DEFAULT_VELOCITY_UNIT.getAngleUnit(), velocityUnit.getDistanceUnit()));
    }

    public void move(double distance, double delta, DistanceUnit distanceUnit, double power, double direction, AngleUnit directionUnit) {
        distance = linearToAngular(distance, distanceUnit, MotorEncoder.DEFAULT_ROTATION_UNIT);
        delta = linearToAngular(delta, distanceUnit, MotorEncoder.DEFAULT_ROTATION_UNIT);
        double[] motorRotations = calcMotorValues(distance, directionUnit.toRadians(direction), 0);

        frontLeftEncoder.rotate(motorRotations[0], delta, power);
        frontRightEncoder.rotate(motorRotations[1], delta, power);
        backRightEncoder.rotate(motorRotations[2], delta, power);
        backLeftEncoder.rotate(motorRotations[3], delta, power);
    }

    private double linearToAngular(double linear, DistanceUnit distanceUnit, AngleUnit angleUnit) {
        return distanceUnit.toMeters(linear) / (2 * Math.PI * wheelRadius) * angleUnit.getFull();
    }

    private double angularToLinear(double angular, AngleUnit angleUnit, DistanceUnit distanceUnit) {
        return angular * 2 * Math.PI * distanceUnit.fromMeters(wheelRadius) / angleUnit.getFull();
    }
}
