package org.chathamrobotics.nova.hardware;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

public class EncodedMotor implements DcMotor {
    private final DcMotor motor;
    private MotorConfigurationType type;

    public EncodedMotor(@NonNull DcMotor motor) {
        this.motor = motor;
        this.type = motor.getMotorType();
    }

    /**
     * @see DcMotor
     */
    @Override
    public void setMotorType(MotorConfigurationType motorType) {
        type = motorType;
        motor.setMotorType(motorType);
    }

    // pass through
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
