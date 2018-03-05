package org.chathamrobotics.nova.hardware;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;
import com.qualcomm.robotcore.util.Range;

import org.chathamrobotics.nova.async.AsyncCallback;
import org.chathamrobotics.nova.async.NovaEventLoop;
import org.chathamrobotics.nova.async.ObjectListener;
import org.chathamrobotics.nova.util.units.AngleUnit;
import org.chathamrobotics.nova.util.units.AngularVelocityUnit;

import javax.sql.RowSetEvent;

@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public class EncodedMotor implements DcMotor {
    ////// CONSTANTS ///////
    private static final NovaEventLoop EVENT_LOOP = NovaEventLoop.getInstance();
    private static final AngleUnit DEFAULT_HEADING_UNIT = AngleUnit.REVOLUTIONS;
    private static final AngularVelocityUnit DEFAULT_VELOCITY_UNIT = AngularVelocityUnit.REVOLUTIONS_PER_MINUTE;

    ////// FIELDS ///////
    private final DcMotor motor;
    private MotorConfigurationType type;

    ////// CONSTRUCTORS ///////
    public EncodedMotor(@NonNull DcMotor motor) {
        this.motor = motor;
        this.type = motor.getMotorType();
    }

    ////// ACCESSORS ///////

    /**
     * @see DcMotor
     */
    @Override
    public void setMotorType(MotorConfigurationType motorType) {
        type = motorType;
        motor.setMotorType(motorType);
    }

    /**
     * Sets the motor's angular velocity. This will change the run mode to RUN_USING_ENCODERS
     * @param velocity  the velocity to set in revolutions per minute
     */
    public void setVelocity(double velocity) {
        setVelocity(velocity, DEFAULT_VELOCITY_UNIT);
    }

    /**
     * Sets the motor's angular velocity. This will change the run mode to RUN_USING_ENCODERS
     * @param velocity  the velocity to set
     * @param unit      the unit of measure for velocity
     */
    public void setVelocity(double velocity, @NonNull AngularVelocityUnit unit) {
        velocity = unit.toRPM(velocity);
        Range.throwIfRangeIsInvalid(velocity, -type.getMaxRPM(), type.getMaxRPM());

        setMode(RunMode.RUN_USING_ENCODER);
        setPower(velocity / type.getMaxRPM());
    }

    /**
     * Gets the motor's angular velocity
     * @return  the motor's angular velocity in revolutions per minute
     */
    public double getVelocity() {
        return getVelocity(DEFAULT_VELOCITY_UNIT);
    }

    /**
     * Gets the motor's angular velocity
     *
     * @param unit  the unit of measure to measure the velocity with
     * @return      the motor's angular velocity
     */
    public double getVelocity(@NonNull AngularVelocityUnit unit) {
        RunMode runMode = getMode();

        setMode(RunMode.RUN_USING_ENCODER);
        double velocity = unit.fromRPM(motor.getPower() / type.getMaxRPM());

        setMode(runMode);

        return velocity;
    }

    /**
     * Gets the heading of motor shaft (is position relative to 0) measured in revolutions
     * @return  the heading of motor shaft
     */
    public double getHeading() {
        return getHeading(DEFAULT_HEADING_UNIT);
    }

    /**
     * Gets the heading of motor shaft (is position relative to 0)
     * @param unit  the unit to measure heading with
     * @return      the heading of motor shaft
     */
    public double getHeading(@NonNull AngleUnit unit) {
        return unit.fromRevolutions(motor.getCurrentPosition() / type.getTicksPerRev());
    }

    /**
     * Gets the target heading for motor shaft (is position relative to 0) measured in revolutions
     * @return  the target heading for motor shaft
     */
    public double getTargetHeading() {
        return  getTargetHeading(DEFAULT_HEADING_UNIT);
    }

    /**
     * Gets the target heading for motor shaft (is position relative to 0)
     * @param unit  the unit to measure heading with
     * @return      the target heading for motor shaft
     */
    public double getTargetHeading(@NonNull AngleUnit unit) {
        return unit.fromRevolutions(motor.getTargetPosition() / type.getTicksPerRev());
    }

    /**
     * Sets the target heading for the motor shaft
     * @param heading   the heading to target measured in revolutions
     */
    public void setTargetHeading(double heading) {
        setTargetHeading(heading, DEFAULT_HEADING_UNIT);
    }

    /**
     * Sets the target heading for the motor shaft
     * @param heading   the heading to target
     * @param unit      the unit of the given heading
     */
    public void setTargetHeading(double heading, @NonNull AngleUnit unit) {
        motor.setTargetPosition((int) (unit.toRevolutions(heading) * type.getTicksPerRev()));
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
     * @param delta     the difference in the motors position and the given position to allow for
     * @return          whether or not the motor is at the given position
     */
    public boolean isAtPosition(int position, int delta) {
        return Math.abs(motor.getCurrentPosition() - position) <= delta;
    }

    /**
     * Checks whether or not the motor's shaft is at the given heading
     * @param heading   the heading to check for measured in revolutions
     * @return          whether or not the motor's shaft is at the given heading
     */
    public boolean isAtHeading(double heading) {
        return isAtHeading(heading, 0, DEFAULT_HEADING_UNIT);
    }

    /**
     * Checks whether or not the motor's shaft is at the given heading
     * @param heading   the heading to check for measured in revolutions
     * @param delta     the difference in the motor's heading and the given heading to allow for measured in revolutions
     * @return          whether or not the motor's shaft is at the given heading
     */
    public boolean isAtHeading(double heading, double delta) {
        return isAtHeading(heading, delta, DEFAULT_HEADING_UNIT);
    }

    /**
     * Checks whether or not the motor's shaft is at the given heading
     * @param heading   the heading to check for
     * @param unit      the unit of measure for the heading and delta
     * @return          whether or not the motor's shaft is at the given heading
     */
    public boolean isAtHeading(double heading, @NonNull AngleUnit unit) {
        return isAtHeading(heading, 0, unit);
    }

    /**
     * Checks whether or not the motor's shaft is at the given heading
     * @param heading   the heading to check for
     * @param delta     the difference in the motor's heading and the given heading to allow for
     * @param unit      the unit of measure for the heading and delta
     * @return          whether or not the motor's shaft is at the given heading
     */
    public boolean isAtHeading(double heading, double delta, @NonNull AngleUnit unit) {
        return Math.abs(getHeading() - unit.toRevolutions(heading)) <= unit.toRevolutions(delta);
    }

    ////// BEHAVIOUR ///////

    /**
     * Resets the encoders
     */
    public void reset() {
        RunMode cached = getMode();

        setMode(RunMode.STOP_AND_RESET_ENCODER);
        setMode(cached);
    }

    /**
     * Goes to the targeted heading and calls the given callback when the heading is reached
     * @param heading   the heading to target
     * @param unit      the unit of measure for the heading
     * @param callback  called when the heading is reached
     */
    public void goToHeading(double heading, @NonNull AngleUnit unit, AsyncCallback callback) {
        try {
            // TODO: change mode and set power
            setTargetHeading(heading, unit);

            if (callback != null) onceAtHeading(heading, unit, callback);
        } catch (Exception e) {
            if (callback != null) callback.run(e);
        }
    }

    /**
     * Registers a callback for when the motor reaches the given heading
     * @param heading   the heading to listen for
     * @param unit      the unit of measure for the heading
     * @param callback  called when the heading is reached
     */
    public void onceAtHeading(final double heading, @NonNull final AngleUnit unit, @NonNull AsyncCallback callback) {
        EVENT_LOOP.once(motor, new ObjectListener.Condition<DcMotor>() {
            @Override
            public boolean test(DcMotor value) throws Exception {
                return isAtHeading(heading, unit);
            }
        }, callback);
    }

    ////// PASS ///////

    /**
     * @see DcMotor
     */
    @Override
    public void setTargetPosition(int position) {
        motor.setTargetPosition(position);
    }

    /**
     * @see DcMotor
     */
    @Override
    public MotorConfigurationType getMotorType() {
        return motor.getMotorType();
    }

    /**
     * @see DcMotor
     */
    @Override
    public RunMode getMode() {
        return motor.getMode();
    }

    /**
     * @see DcMotor
     */
    @Override
    public void setMode(RunMode mode) {
        motor.setMode(mode);
    }

    /**
     * @see DcMotor
     */
    @Override
    public void setPower(double power) {
        motor.setPower(power);
    }

    /**
     * @see DcMotor
     */
    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        motor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    /**
     * @see DcMotor
     */
    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return motor.getZeroPowerBehavior();
    }

    /**
     * @see DcMotor
     */
    @Override
    public void setDirection(Direction direction) {
        motor.setDirection(direction);
    }

    /**
     * @see DcMotor
     */
    @Override
    public void close() {
        motor.close();
    }

    /**
     * @see DcMotor
     */
    @Override
    public void resetDeviceConfigurationForOpMode() {
        motor.resetDeviceConfigurationForOpMode();
    }

    /**
     * @see DcMotor
     */
    @SuppressWarnings("deprecation")
    @Override
    public void setPowerFloat() {
        motor.setPowerFloat();
    }

    /**
     * @see DcMotor
     */
    @Override
    public boolean isBusy() {
        return motor.isBusy();
    }

    /**
     * @see DcMotor
     */
    @Override
    public int getTargetPosition() {
        return motor.getTargetPosition();
    }

    /**
     * @see DcMotor
     */
    @Override
    public int getPortNumber() {
        return motor.getPortNumber();
    }

    /**
     * @see DcMotor
     */
    @Override
    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    /**
     * @see DcMotor
     */
    @Override
    public double getPower() {
        return motor.getPower();
    }

    /**
     * @see DcMotor
     */
    @Override
    public Direction getDirection() {
        return motor.getDirection();
    }

    /**
     * @see DcMotor
     */
    @Override
    public boolean getPowerFloat() {
        return motor.getPowerFloat();
    }

    /**
     * @see DcMotor
     */
    @Override
    public Manufacturer getManufacturer() {
        return motor.getManufacturer();
    }

    /**
     * @see DcMotor
     */
    @Override
    public DcMotorController getController() {
        return motor.getController();
    }

    /**
     * @see DcMotor
     */
    @Override
    public int getVersion() {
        return motor.getVersion();
    }

    /**
     * @see DcMotor
     */
    @Override
    public String getDeviceName() {
        return motor.getDeviceName();
    }

    /**
     * @see DcMotor
     */
    @Override
    public String getConnectionInfo() {
        return motor.getConnectionInfo();
    }
}
