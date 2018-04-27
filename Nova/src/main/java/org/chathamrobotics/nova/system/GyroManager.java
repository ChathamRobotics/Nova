package org.chathamrobotics.nova.system;

import com.qualcomm.robotcore.hardware.GyroSensor;

import org.chathamrobotics.nova.async.AsyncCallback;
import org.chathamrobotics.nova.async.NovaEventLoop;
import org.chathamrobotics.nova.async.ObjectListener;
import org.chathamrobotics.nova.math.Angles;
import org.chathamrobotics.nova.robot.Robot;
import org.chathamrobotics.nova.robot.RobotConfiguration;
import org.chathamrobotics.nova.util.RobotLogger;
import org.chathamrobotics.nova.util.units.AngleUnit;

/**
 * A manager for the gyroscope
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class GyroManager extends RobotSystemImpl {
    private final static String TAG = GyroManager.class.getSimpleName();
    private final static NovaEventLoop EVENT_LOOP = NovaEventLoop.getInstance();
    private final static ObjectListener.Condition<GyroSensor> GYRO_NOT_CALIBRATING = new ObjectListener.Condition<GyroSensor>() {
        @Override
        public boolean test(GyroSensor value) throws Exception {
            return ! value.isCalibrating();
        }
    };

    /**
     * The configuration required for building a gyro manager
     */
    public interface Configuration extends RobotConfiguration {
        /**
         * Gets the name of the gyro in the hardware configuration
         * @return  the name of the gyro
         */
        String getGyroName();
    }

    /**
     * Orientation of the gyro
     */
    public enum Orientation {
        UPSIDE_UP,
        UPSIDE_DOWN
    }

    /**
     * Builds a new instance of {@link GyroManager}
     * @param robot         the robot
     * @param configuration the configuration for the gyro manager
     * @return              the built instance of {@link GyroManager}
     */
    public static GyroManager build(Robot robot, Configuration configuration) {
        return new GyroManager(
                robot.getHardwareMap().gyroSensor.get(configuration.getGyroName()),
                robot.logger
        );
    }

    private final GyroSensor gyro;

    private Orientation orientation = Orientation.UPSIDE_UP;
    private int initialHeading;

    /**
     * Creates a new instance of {@link GyroManager}
     * @param gyro      the gyro
     * @param logger    the robot logger
     */
    public GyroManager(GyroSensor gyro, RobotLogger logger) {
        super(logger);

        this.gyro = gyro;
    }

    // ACCESSOR

    /**
     * Gets the gyro used by the {@link GyroManager}
     * @return      the gyro used by the {@link GyroManager}
     */
    public GyroSensor getGyro() {
        return gyro;
    }

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
     * Gets the heading of the gyro and corrects for upside down gyros
     * @param angleUnit the unit to return the heading in
     * @return          the heading
     */
    public double getHeading(AngleUnit angleUnit) {
        return angleUnit.fromDegrees(getHeading());
    }

    /**
     * Gets the heading of the gyro in degrees. This factors in the gyro's orientation and
     * corrects upside down gyro's heading
     * @return  the heading [0-360]
     */
    public int getHeading() {
        if (orientation == Orientation.UPSIDE_UP) return gyro.getHeading();

        return 360 - gyro.getHeading();
    }

    /**
     * Gets the heading relative to the initial heading
     * @return  the heading relative to the initial heading
     */
    public double getRelativeHeading() {
        return getRelativeHeading(getInitialHeading(), AngleUnit.DEGREES);
    }

    /**
     * Gets the heading relative to the initial heading
     * @param angleUnit the heading unit
     * @return          the heading relative to the initial heading
     */
    public double getRelativeHeading(AngleUnit angleUnit) {
        return getRelativeHeading(getInitialHeading(angleUnit), angleUnit);
    }

    /**
     * Gets the heading relative to the reference heading
     * @param reference the reference angle
     * @return          the heading relative to the reference heading
     */
    public double getRelativeHeading(double reference) {
        return getRelativeHeading(reference, AngleUnit.DEGREES);
    }

    /**
     * Gets the heading relative to the reference heading
     * @param reference the reference angle
     * @param angleUnit the angle unit
     * @return          the heading relative to the reference heading
     */
    public double getRelativeHeading(double reference, AngleUnit angleUnit) {
        return Angles.relativeAngle(reference, getHeading(angleUnit), angleUnit);
    }

    /**
     * Gets the initial heading in degrees
     * @return  the initial heading
     */
    public int getInitialHeading() {
        return initialHeading;
    }

    /**
     * Gets the initial heading
     * @param angleUnit the unit to get the heading in
     * @return          the initial heading
     */
    public double getInitialHeading(AngleUnit angleUnit) {
        return angleUnit.fromDegrees(getInitialHeading());
    }

    /**
     * Checks whether or not the gyro heading is equal to the given heading
     * @param heading   the heading to check for
     * @return          whether or not the heading gyro is equal to the given heading
     */
    public boolean isAtHeading(int heading) {
        return isAtHeading(heading, 0);
    }

    /**
     * Checks whether or not the gyro heading is equal to the given heading
     * @param heading   the heading to check for
     * @param delta     the tolerance in heading to allow for
     * @return          whether or not the heading gyro is equal to the given heading
     */
    public boolean isAtHeading(int heading, int delta) {
        return Angles.shortestDistance(getHeading(), initialHeading, AngleUnit.DEGREES) <= delta;
    }

    /**
     * Checks whether or not the gyro heading is equal to the given heading
     * @param heading   the heading to check for
     * @param delta     the tolerance in heading to allow for
     * @param angleUnit the heading unit
     * @return          whether or not the heading gyro is equal to the given heading
     */
    public boolean isAtHeading(double heading, double delta, AngleUnit angleUnit) {
        return Angles.shortestDistance(getHeading(angleUnit), heading, angleUnit) <= delta;
    }

    /**
     * Checks whether or not the gyro heading is equal to the given heading
     * @param heading   the heading to check for
     * @param angleUnit the heading unit
     * @return          whether or not the heading gyro is equal to the given heading
     */
    public boolean isAtHeading(double heading, AngleUnit angleUnit) {
        return isAtHeading(heading, 0, angleUnit);
    }

    // BEHAVIOR

    /**
     * Initializes the {@link GyroManager}
     */
    @Override
    public void init() {
        logger.info.log("Initializing");

        calibrate(new AsyncCallback() {
            @Override
            public void run(Throwable thr) {
                if (thr == null)
                    logger.error.log(thr, "Error initializing gyro");
                else {
                    setState(State.INITIALIZED);
                    initialHeading = gyro.getHeading();
                }
            }
        });
    }

    /**
     * Starts the {@link GyroManager}
     */
    @Override
    public void start() {
        preStart();

        logger.info.log("Starts the gyro manager");

        setState(State.RUNNING);
    }

    /**
     * Stops the {@link GyroManager}
     */
    @Override
    public void stop() {
        logger.info.log("Stopping");

        removeOpenListeners();

        setState(State.STOPPED);
    }

    /**
     * Calibrates the gyro
     */
    public void calibrate() {
        calibrate(null, -1);
    }

    /**
     * Calibrates the gyro
     * @param callback  called when the calibration is finished
     */
    public void calibrate(AsyncCallback callback) {
        calibrate(callback, -1);
    }

    /**
     * Calibrates the gyro
     * @param callback  called when the calibration is finished
     * @param timeout   the timeout for the calibration
     */
    public void calibrate(AsyncCallback callback, long timeout) {
        if (! gyro.isCalibrating()) gyro.calibrate();

        if (callback != null) {
            if (timeout > 0) openListeners.add(EVENT_LOOP.on(gyro, GYRO_NOT_CALIBRATING, callback, timeout));
            else openListeners.add(EVENT_LOOP.on(gyro, GYRO_NOT_CALIBRATING, callback));
        }
    }
}
