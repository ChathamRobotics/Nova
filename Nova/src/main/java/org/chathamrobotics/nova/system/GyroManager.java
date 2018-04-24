package org.chathamrobotics.nova.system;

/*!
 * nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 */

import com.qualcomm.robotcore.hardware.GyroSensor;

import org.chathamrobotics.nova.async.AsyncCallback;
import org.chathamrobotics.nova.async.NovaEventLoop;
import org.chathamrobotics.nova.async.ObjectListener;
import org.chathamrobotics.nova.util.RobotLogger;
import org.chathamrobotics.nova.util.units.AngleUnit;

public class GyroManager extends RobotSystemImpl {
    public final static AngleUnit DEFAULT_ANGLE_UNIT = AngleUnit.DEGREES;

    private final static NovaEventLoop EVENT_LOOP = NovaEventLoop.getInstance();
    private final static ObjectListener.Condition<GyroSensor> GYRO_DONE_CALIBRATING = new ObjectListener.Condition<GyroSensor>() {
        @Override
        public boolean test(GyroSensor value) throws Exception {
            return ! value.isCalibrating();
        }
    };

    public enum Orientation {
        RIGHT_SIDE_UP,
        UPSIDE_DOWN
    }

    private final GyroSensor gyro;
    private final AsyncCallback setInitState = new AsyncCallback() {
        @Override
        public void run(Throwable thr) {
            if (thr != null) {
                logger.error.logr(thr, "Error initializing gyro");
                return;
            }

            setState(State.INITIALIZED);
        }
    };

    private Orientation orientation = Orientation.RIGHT_SIDE_UP;

    /**
     * Creates a new instance of {@link  GyroManager}
     * @param gyro      the gyro sensor
     * @param logger    the robot logger
     */
    public GyroManager(GyroSensor gyro, RobotLogger logger) {
        super(logger);

        this.gyro = gyro;
    }

    // ACCESSORS

    /**
     * Sets the orientation of the gyro
     * @param orientation   the orientation of the gyro
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Gets the orientation of the gyro
     * @return  the orientation of the gyro
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Gets the heading
     * @return  the heading in degrees
     */
    public double getHeading() {
        return getHeading(AngleUnit.DEGREES);
    }

    /**
     * Gets the heading
     * @param unit  the unit to measure the heading in
     * @return      the heading
     */
    public double getHeading(AngleUnit unit) {
        if (orientation == Orientation.RIGHT_SIDE_UP)
            return unit.fromDegrees(gyro.getHeading());

        return unit.fromDegrees(360 - gyro.getHeading());
    }

    // BEHAVIOR

    /**
     * Initializes the gyro manager
     */
    @Override
    public void init() {
        logger.info.log("Initializing");

        gyro.calibrate();
        EVENT_LOOP.on(gyro, GYRO_DONE_CALIBRATING, setInitState);
    }

    /**
     * Starts the gyro manager
     */
    @Override
    public void start() {
        preStart();
        logger.info.log("Starting");
        setState(State.RUNNING);
    }

    /**
     * Stops the gyro manger
     */
    @Override
    public void stop() {
        logger.info.log("Stopping");
        removeListeners();
        setState(State.STOPPED);
    }
}
