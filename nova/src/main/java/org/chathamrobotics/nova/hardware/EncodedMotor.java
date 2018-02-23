package org.chathamrobotics.nova.hardware;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;
import com.qualcomm.robotcore.util.Range;

import org.chathamrobotics.nova.util.units.AngleUnit;
import org.chathamrobotics.nova.util.units.AngularVelocityUnit;

@SuppressWarnings({"unused", "WeakerAccess"})
public class EncodedMotor implements DcMotor {

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
        setVelocity(velocity, AngularVelocityUnit.REVOLUTIONS_PER_MINUTE);
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
        return getVelocity(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE);
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

    public double getHeading(@NonNull AngleUnit unit) {
        return unit.fromDegrees(motor.getCurrentPosition() / type.getTicksPerRev() * 360);
    }

    ////// BEHAVIOUR ///////

    /**
     * Resets the encoders
     */
    public void reset() {
        RunMode cache = getMode();

        setMode(RunMode.STOP_AND_RESET_ENCODER);
        setMode(cache);
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
