package org.chathamrobotics.nova.hardware;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.chathamrobotics.nova.util.units.AngleUnit;
import org.chathamrobotics.nova.util.units.AngularVelocityUnit;

/**
 * A utility for working with encoders
 */
@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public class Encoder {
    ////////// CONSTANTS ///////////
    public final static AngularVelocityUnit DEFAULT_VELOCITY_UNIT = AngularVelocityUnit.REVOLUTIONS_PER_MINUTE;
    public final static AngleUnit DEFAULT_HEADING_UNIT = AngleUnit.REVOLUTIONS;

    ////////// FIELDS ///////////
    private final DcMotor motor;


    ////////// CONSTRUCTORS ///////////
    /**
     * Creates a new instance of {@link Encoder}
     * @param motor the encoder's motor
     */
    public Encoder(DcMotor motor) {
        this.motor = motor;
    }

    ////////// ACCESSORS ///////////
    /**
     * Sets the motor's angular velocity. WARNING: this will change the motor's run mode to RUN_USING_ENCODERS
     * @param velocity  the velocity to set in rpms
     */
    public void setVelocity(double velocity) {
        setVelocity(velocity, AngularVelocityUnit.REVOLUTIONS_PER_MINUTE);
    }

    /**
     * Sets the motor's angular velocity. WARNING: this will change the motor's run mode to RUN_USING_ENCODERS
     * @param velocity  the velocity to set
     * @param unit      the unit to measure the angular velocity with
     */
    public void setVelocity(double velocity, @NonNull AngularVelocityUnit unit) {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double max = getMaxVelocity(unit);

        Range.throwIfRangeIsInvalid(velocity, -max, max);

        motor.setPower(Range.clip(velocity / max, -1, 1));
    }

    /**
     * Gets the motor's angular velocity. WARNING: this will change the motor's run mode to RUN_USING_ENCODERS
     * @return  the motor's angular velocity in rpms
     */
    public double getVelocity() {
        return getVelocity(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE);
    }

    /**
     * Gets the motor's angular velocity. WARNING: this will change the motor's run mode to RUN_USING_ENCODERS
     * @param unit  the unit to measure the angular velocity with
     * @return      the motor's angular velocity
     */
    public double getVelocity(@NonNull AngularVelocityUnit unit) {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double max = getMaxVelocity(unit);
        return motor.getPower() * max;
    }

    /**
     * Gets the motor's current position
     * @return  the motor's current position measured in ticks
     */
    public int getPosition() {
        return motor.getCurrentPosition();
    }

    /**
     * Gets the motor's targeted position
     * @return  the motor's targeted position measured in ticks
     */
    public int getTargetPosition() {
        return motor.getTargetPosition();
    }

    /**
     * Sets the motor's target position. WARNING: this will change the motor's run mode to RUN_TO_POSITION
     * @param position  the position to target
     */
    public void setTargetPosition(int position) {
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setTargetPosition(position);
    }

    /**
     * Gets the motor shafts heading
     * @return  the motor shafts heading measured in revolutions
     */
    public double getHeading() {
        return getHeading(AngleUnit.REVOLUTIONS);
    }

    /**
     * Gets the motor shafts heading
     * @param unit  the unit to measure the heading with
     * @return      the motor shafts heading
     */
    public double getHeading(@NonNull AngleUnit unit) {
        return unit.fromRevolutions(getPosition() / getTicksPerRev());
    }

    /**
     * Gets the motor shaft's targeted heading
     * @return  the motor shaft's targeted heading measured in revolutions
     */
    public double getTargetHeading() {
        return getTargetHeading(AngleUnit.REVOLUTIONS);
    }

    /**
     * Gets the motor shaft's targeted heading
     * @param unit  the unit to measure heading with
     * @return      the motor shafts targeted heading
     */
    public double getTargetHeading(@NonNull AngleUnit unit) {
        return unit.fromRevolutions(getTargetPosition() / getTicksPerRev());
    }

    /**
     * Sets the motor shaft's targeted heading
     * @param heading   the motor shaft's targeted heading in revolutions
     */
    public void setTargetHeading(double heading) {
        setTargetHeading(heading, AngleUnit.REVOLUTIONS);
    }

    /**
     * Sets the motor shaft's targeted heading
     * @param heading   the motor shaft's targeted heading
     * @param unit      the unit of measure for the heading
     */
    public void setTargetHeading(double heading, @NonNull AngleUnit unit) {
        setTargetPosition((int) (unit.toRevolutions(heading) * getTicksPerRev()));
    }

    /**
     * Gets the motor that uses this encoder
     * @return  the motor
     */
    public DcMotor getMotor() {
        return this.motor;
    }

    /**
     * Gets the motor's maximum angular velocity
     * @return  the motor's maximum angular velocity in rpms
     */
    public double getMaxVelocity() {
        return getMaxVelocity(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE);
    }

    /**
     * Gets the motor's maximum angular velocity
     * @param unit  the unit to measure the velocity with
     * @return      the motor's maximum angular velocity
     */
    public double getMaxVelocity(@NonNull AngularVelocityUnit unit) {
        return unit.fromRPM(motor.getMotorType().getMaxRPM());
    }

    /**
     * Gets the number of ticks per revolution for the encoder
     * @return  the number of ticks per revolution for the encoder
     */
    public double getTicksPerRev() {
        return motor.getMotorType().getTicksPerRev();
    }

    ////////// BEHAVIOR ///////////

    /**
     * Resets the encoder
     */
    public void reset() {
        DcMotor.RunMode cache = motor.getMode();

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor.setMode(cache);
    }

    /**
     * Checks whether or not the motor is at the given position
     * @param position  the position to check for
     * @return          whether or not the motor is at the given position
     */
    public boolean isAtPosition(int position) {
        return isAtPosition(position, 0);
    }

    /**
     * Checks whether or not the motor is at the given position
     * @param position  the position to check for
     * @param delta     the difference to allow for between the actual position and the given position
     * @return          whether or not the motor is at the given position
     */
    public boolean isAtPosition(int position, int delta) {
        return Math.abs(getPosition() - position) <= delta;
    }

    /**
     * Checks whether or not the motor shaft is at the given heading
     * @param heading   the heading to check for
     * @return          whether or not the motor shaft is at the given heading
     */
    public boolean isAtHeading(double heading) {
        return isAtHeading(heading, 0, AngleUnit.REVOLUTIONS);
    }

    /**
     * Checks whether or not the motor shaft is at the given heading
     * @param heading   the heading to check for
     * @param delta     the difference in the actual heading and the given heading to allow for
     * @return          whether or not the motor shaft is at the given heading
     */
    public boolean isAtHeading(double heading, double delta) {
        return isAtHeading(heading, delta, AngleUnit.REVOLUTIONS);
    }

    /**
     * Checks whether or not the motor shaft is at the given heading
     * @param heading   the heading to check for
     * @param unit      the unit of measure for the heading
     * @return          whether or not the motor shaft is at the given heading
     */
    public boolean isAtHeading(double heading, @NonNull AngleUnit unit) {
        return  isAtHeading(heading, 0, unit);
    }

    /**
     * Checks whether or not the motor shaft is at the given heading
     * @param heading   the heading to check for
     * @param delta     the difference in the actual heading and the given heading to allow for
     * @param unit      the unit of measure for the heading and delta
     * @return          whether or not the motor shaft is at the given heading
     */
    public boolean isAtHeading(double heading, double delta, @NonNull AngleUnit unit) {
        return Math.abs(getHeading(unit) - heading) <= delta;
    }
}
