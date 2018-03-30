package org.chathamrobotics.nova.mocks;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/30/2018
 */


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.configuration.MotorConfigurationType;

public class MockMotor implements DcMotor{
    private boolean busy;
    private double power;
    private int target;
    private RunMode mode;
    private ZeroPowerBehavior behavior;
    private Direction direction;
    private MotorConfigurationType type;

    @Override
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public boolean isBusy() {
        return busy;
    }

    @Override
    public void setTargetPosition(int position) {
        this.target = position;
    }

    @Override
    public int getTargetPosition() {
        return target;
    }

    @Override
    public void setMode(RunMode mode) {
        this.mode = mode;
    }

    @Override
    public RunMode getMode() {
        return mode;
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        this.behavior = zeroPowerBehavior;
    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return behavior;
    }

    @Override
    public void setPowerFloat() {
        setZeroPowerBehavior(ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setMotorType(MotorConfigurationType motorType) {
        this.type = motorType;
    }

    @Override
    public MotorConfigurationType getMotorType() {
        return type;
    }

    @Override
    public boolean getPowerFloat() {
        return getZeroPowerBehavior() == ZeroPowerBehavior.FLOAT;
    }

    // unimplemented
    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public Manufacturer getManufacturer() {
        return null;
    }

    @Override
    public DcMotorController getController() {
        return null;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {}

    @Override
    public void close() {}

    @Override
    public int getPortNumber() {
        return 0;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public String getConnectionInfo() {
        return null;
    }

    @Override
    public String getDeviceName() {
        return null;
    }
}
