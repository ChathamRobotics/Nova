package org.chathamrobotics.nova.hardware;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.chathamrobotics.nova.async.AsyncCallback;
import org.chathamrobotics.nova.async.NovaEventLoop;
import org.chathamrobotics.nova.async.ObjectListener;
import org.chathamrobotics.nova.util.TimeoutChecker;
import org.chathamrobotics.nova.util.units.AngleUnit;
import org.chathamrobotics.nova.util.units.AngularVelocityUnit;

import java.util.concurrent.TimeoutException;

/**
 * A utility for working with encoders
 */
@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue", "EmptyCatchBlock"})
public class MotorEncoder {
    ////////// CONSTANTS ///////////
    public final static AngularVelocityUnit DEFAULT_VELOCITY_UNIT = AngularVelocityUnit.REVOLUTIONS_PER_MINUTE;
    public final static AngleUnit DEFAULT_HEADING_UNIT = AngleUnit.REVOLUTIONS;
    public final static AngleUnit DEFAULT_ROTATION_UNIT = AngleUnit.DEGREES;

    private final static long NO_TIMEOUT = -1;
    private final static NovaEventLoop EVENT_LOOP = NovaEventLoop.getInstance();
    private final static ObjectListener.Condition<DcMotor> IS_NOT_BUSY = new ObjectListener.Condition<DcMotor>() {
        @SuppressWarnings("RedundantThrows")
        @Override
        public boolean test(DcMotor motor) throws Exception {
            return ! motor.isBusy();
        }
    };

    ////////// FIELDS ///////////
    private final DcMotor motor;


    ////////// CONSTRUCTORS ///////////
    /**
     * Creates a new instance of {@link MotorEncoder}
     * @param motor the encoder's motor
     */
    public MotorEncoder(DcMotor motor) {
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

    /**
     * Sets the target position for the motor and goes to the target
     *
     * @param position  the position to go to
     * @param power     the power to use to go to the position
     */
    public void goToPosition(int position, double power) {
        goToPosition(position, power, null, NO_TIMEOUT);
    }

    /**
     * Sets the target position for the motor and goes to the target. If a callback is given that
     * callback will be called when the motor is no longer busy.
     *
     * @param position  the position to go to
     * @param power     the power to use to go to the position
     * @param callback  called when the position is reached
     */
    public void goToPosition(int position, double power, AsyncCallback callback) {
        goToPosition(position, power, callback, NO_TIMEOUT);
    }

    /**
     * Sets the target position for the motor and goes to the target. If a callback is given that
     * callback will be called when the motor is no longer busy.
     *
     * @param position  the position to go to
     * @param power     the power to use to go to the position
     * @param callback  called when the position is reached
     * @param timeout   the timeout for the callback. If timeout < 1 then no timeout will be set
     */
    public void goToPosition(int position, double power, AsyncCallback callback, long timeout) {
        try {
            setTargetPosition(position);

            motor.setPower(power);

            if (callback != null) {
                if (timeout > 0) EVENT_LOOP.on(motor, IS_NOT_BUSY, callback, timeout);
                else EVENT_LOOP.on(motor, IS_NOT_BUSY, callback);
            }
        } catch (Exception e) { if (callback != null) callback.run(e); }
    }

    /**
     * Goes to the position synchronously (blocking)
     * @param position              the position to go to
     * @param power                 the power to set the motor to
     * @throws InterruptedException thrown if the thread is interrupted
     */
    public void goToPositionSync(int position, double power) throws InterruptedException {
        try {
            goToPositionSync(position, power, NO_TIMEOUT);
        } catch (TimeoutException e) {}
    }

    /**
     * Goes to the position synchronously (blocking)
     * @param position              the position to go to
     * @param power                 the power to set the motor to
     * @param timeout               the timeout for the operation. If timeout < 1 then no timeout will be set
     * @throws TimeoutException     thrown if the timeout is exceeded
     * @throws InterruptedException thrown if the thread is interrupted
     */
    public void goToPositionSync(int position, double power, long timeout) throws TimeoutException, InterruptedException {
        TimeoutChecker checker = null;

        if (timeout > 0) checker = new TimeoutChecker(timeout, "goToPositionSync timed out");

        setTargetPosition(position);

        motor.setPower(power);

        while (motor.isBusy()) {
            if (checker != null) checker.check();
            Thread.sleep(10);
        }
    }

    /**
     * Goes to the heading asynchronously (nonblocking)
     * @param heading   the heading to go to in revolutions
     * @param power     the power to set the motor to
     */
    public void goToHeading(double heading, double power) {
        goToHeading(heading, DEFAULT_HEADING_UNIT, power, null, NO_TIMEOUT);
    }

    /**
     * Goes to the heading asynchronously (nonblocking)
     * @param heading   the heading to go to
     * @param unit      the unit of measure for the heading
     * @param power     the power to set the motor to
     */
    public void goToHeading(double heading, @NonNull AngleUnit unit, double power) {
        goToHeading(heading, unit, power, null, NO_TIMEOUT);
    }

    /**
     * Goes to the heading asynchronously (nonblocking)
     * @param heading   the heading to go to
     * @param unit      the unit of measure for the heading
     * @param power     the power to set the motor to
     * @param callback  called when the heading is reached
     */
    public void goToHeading(double heading, @NonNull AngleUnit unit, double power, AsyncCallback callback) {
        goToHeading(heading, unit, power, callback, NO_TIMEOUT);
    }

    /**
     * Goes to the heading asynchronously (nonblocking)
     * @param heading   the heading to go to in revolutions
     * @param power     the power to set the motor to
     * @param callback  called when the heading is reached
     */
    public void goToHeading(double heading, double power, AsyncCallback callback) {
        goToHeading(heading, DEFAULT_HEADING_UNIT, power, callback, NO_TIMEOUT);
    }

    /**
     * Goes to the heading asynchronously (nonblocking)
     * @param heading   the heading to go to in revolutions
     * @param power     the power to set the motor to
     * @param callback  called when the heading is reached
     * @param timeout   the timeout for the operation. If timeout < 1 then no timeout will be set
     */
    public void goToHeading(double heading, double power, AsyncCallback callback, long timeout) {
        goToHeading(heading, DEFAULT_HEADING_UNIT, power, callback, timeout);
    }

    /**
     * Goes to the heading asynchronously (nonblocking)
     * @param heading   the heading to go to
     * @param unit      the unit of measure for the heading
     * @param power     the power to set the motor to
     * @param callback  called when the heading is reached
     * @param timeout   the timeout for the operation. If timeout < 1 then no timeout will be set
     */
    public void goToHeading(double heading, @NonNull AngleUnit unit, double power, AsyncCallback callback, long timeout) {
        try {
            setTargetHeading(heading, unit);

            motor.setPower(power);

            if (callback != null) {
                if (timeout > 0) EVENT_LOOP.on(motor, IS_NOT_BUSY, callback, timeout);
                else EVENT_LOOP.on(motor, IS_NOT_BUSY, callback);
            }
        } catch (Exception e) { if (callback != null) callback.run(e); }
    }

    /**
     * Goes to the heading synchronously (blocking)
     * @param heading               the heading to go to in revolutions
     * @param power                 the power to set the motor to
     * @throws InterruptedException thrown if the thread is interrupted
     */
    public void goToHeadingSync(double heading, double power) throws InterruptedException {
        goToHeadingSync(heading, DEFAULT_HEADING_UNIT, power);
    }

    /**
     * Goes to the heading synchronously (blocking)
     * @param heading               the heading to go to in revolutions
     * @param power                 the power to set the motor to
     * @param timeout               the timeout fot the operation. If timeout < 1 then no timeout will be set
     * @throws InterruptedException thrown if the thread is interrupted
     * @throws TimeoutException     thrown if the operation times out
     */
    public void goToHeadingSync(double heading, double power, long timeout) throws TimeoutException, InterruptedException {
        goToHeadingSync(heading, DEFAULT_HEADING_UNIT, power, timeout);
    }

    /**
     * Goes to the heading synchronously (blocking)
     * @param heading               the heading to go to
     * @param unit                  the unit of measure for the heading
     * @param power                 the power to set the motor to
     * @throws InterruptedException thrown if the thread is interrupted
     */
    public void goToHeadingSync(double heading, @NonNull AngleUnit unit, double power) throws InterruptedException {
        try {
            goToHeadingSync(heading, unit, power, NO_TIMEOUT);
        } catch (TimeoutException e) {}
    }

    /**
     * Goes to the heading synchronously (blocking)
     * @param heading               the heading to go to
     * @param unit                  the unit of measure for the heading
     * @param power                 the power to set the motor to
     * @param timeout               the timeout fot the operation. If timeout < 1 then no timeout will be set
     * @throws InterruptedException thrown if the thread is interrupted
     * @throws TimeoutException     thrown if the operation times out
     */
    public void goToHeadingSync(double heading, @NonNull AngleUnit unit, double power, long timeout) throws InterruptedException, TimeoutException {
        TimeoutChecker checker = null;

        if (timeout > 0) checker = new TimeoutChecker(timeout, "goToHeadingSync timed out");

        setTargetHeading(heading, unit);

        motor.setPower(power);

        while (motor.isBusy()) {
            if (checker != null) checker.check();
            Thread.sleep(10);
        }
    }

    /**
     * Rotates the motor shaft by the given angle asynchronously (nonblocking)
     * @param angle     the angle through which to rotate in degrees
     * @param power     the power to set the motor to
     */
    public void rotate(double angle, double power) {
        rotate(angle, DEFAULT_ROTATION_UNIT, power, null, NO_TIMEOUT);
    }

    /**
     * Rotates the motor shaft by the given angle asynchronously (nonblocking)
     * @param angle     the angle through which to rotate
     * @param unit      the unit of measure for the angle
     * @param power     the power to set the motor to
     */
    public void rotate(double angle, @NonNull AngleUnit unit, double power) {
        rotate(angle, unit, power, null, NO_TIMEOUT);
    }

    /**
     * Rotates the motor shaft by the given angle asynchronously (nonblocking)
     * @param angle     the angle through which to rotate in degrees
     * @param power     the power to set the motor to
     * @param callback  called when the rotation is finished
     */
    public void rotate(double angle, double power, AsyncCallback callback) {
        rotate(angle, DEFAULT_ROTATION_UNIT, power, callback, NO_TIMEOUT);
    }

    /**
     * Rotates the motor shaft by the given angle asynchronously (nonblocking)
     * @param angle     the angle through which to rotate in degrees
     * @param power     the power to set the motor to
     * @param callback  called when the rotation is finished
     * @param timeout   the timeout for the operation. If timeout < 1 then no timeout is set
     */
    public void rotate(double angle, double power, AsyncCallback callback, long timeout) {
        rotate(angle, DEFAULT_ROTATION_UNIT, power, callback, timeout);
    }

    /**
     * Rotates the motor shaft by the given angle asynchronously (nonblocking)
     * @param angle     the angle through which to rotate
     * @param unit      the unit of measure for the angle
     * @param power     the power to set the motor to
     * @param callback  called when the rotation is finished
     */
    public void rotate(double angle, @NonNull AngleUnit unit, double power, AsyncCallback callback) {
        rotate(angle, unit, power, callback, NO_TIMEOUT);
    }

    /**
     * Rotates the motor shaft by the given angle asynchronously (nonblocking)
     * @param angle     the angle through which to rotate
     * @param unit      the unit of measure for the angle
     * @param power     the power to set the motor to
     * @param callback  called when the rotation is finished
     * @param timeout   the timeout for the operation. If timeout < 1 then no timeout is set
     */
    public void rotate(double angle, @NonNull AngleUnit unit, double power, AsyncCallback callback, long timeout) {
        try {
            setTargetHeading(getHeading() + unit.toRevolutions(angle));

            motor.setPower(power);

            if (callback != null) {
                if (timeout > 0) EVENT_LOOP.on(motor, IS_NOT_BUSY, callback, timeout);
                else EVENT_LOOP.on(motor, IS_NOT_BUSY, callback);
            }
        } catch (Exception e) { if (callback != null) callback.run(e); }
    }

    /**
     * Rotates the motor shaft by the given angle synchronously (blocking)
     * @param angle                 the angle through which to rotate in degrees
     * @param power                 the power to set the motor to
     * @throws InterruptedException thrown if the thread is interrupted
     */
    public void rotateSync(double angle, double power) throws InterruptedException {
        rotateSync(angle, DEFAULT_ROTATION_UNIT, power);
    }

    /**
     * Rotates the motor shaft by the given angle synchronously (blocking)
     * @param angle                 the angle through which to rotate in degrees
     * @param power                 the power to set the motor to
     * @param timeout               the timeout for the operation. If timeout < 1 then no timeout is set
     * @throws TimeoutException     thrown if the operation times out
     * @throws InterruptedException thrown if the thread is interrupted
     */
    public void rotateSync(double angle, double power, long timeout) throws TimeoutException, InterruptedException {
        rotateSync(angle, DEFAULT_ROTATION_UNIT, power, timeout);
    }

    /**
     * Rotates the motor shaft by the given angle synchronously (blocking)
     * @param angle                 the angle through which to rotate
     * @param unit                  the unit of measure for the angle
     * @param power                 the power to set the motor to
     * @throws InterruptedException thrown if the thread is interrupted
     */
    public void rotateSync(double angle, @NonNull AngleUnit unit, double power) throws InterruptedException {
        try {
            rotateSync(angle, unit, power, NO_TIMEOUT);
        } catch (TimeoutException e) {}
    }

    /**
     * Rotates the motor shaft by the given angle synchronously (blocking)
     * @param angle                 the angle through which to rotate
     * @param unit                  the unit of measure for the angle
     * @param power                 the power to set the motor to
     * @param timeout               the timeout for the operation. If timeout < 1 then no timeout is set
     * @throws TimeoutException     thrown if the operation times out
     * @throws InterruptedException thrown if the thread is interrupted
     */
    public void rotateSync(double angle, @NonNull AngleUnit unit, double power, long timeout) throws TimeoutException, InterruptedException {
        TimeoutChecker checker = null;

        if (timeout > 0) checker = new TimeoutChecker(timeout, "rotateSync timed out");

        setTargetHeading(getHeading() + unit.toRevolutions(angle));

        motor.setPower(power);

        while (motor.isBusy()) {
            if (checker != null) checker.check();
            Thread.sleep(10);
        }
    }
}
