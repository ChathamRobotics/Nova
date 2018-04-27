package org.chathamrobotics.nova.system;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/24/2018
 */


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.chathamrobotics.nova.robot.Robot;
import org.chathamrobotics.nova.robot.RobotConfiguration;
import org.chathamrobotics.nova.util.RobotLogger;
import org.chathamrobotics.nova.util.units.AngleUnit;

/**
 * A holonomic driver setup in the following configuration:
 * <p>
 *     __    __
 * FL |//|  |\\| FR
 *    |__|  |__|
 *     __    __
 * BL |\\|  |//| BR
 *    |__|  |__|
 * </p>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class HolonomicDrive extends RobotSystemImpl implements DriveSystem {
    /////////// CONSTANTS //////////////////
    public final static double MAX_POWER = 2;
    public final static AngleUnit DEFAULT_DIRECTION_UNIT = AngleUnit.RADIANS;

    public final static double FRONT_OFFSET = 0;
    public final static double LEFT_OFFSET = Math.PI / 2;
    public final static double BACK_OFFSET = Math.PI;
    public final static double RIGHT_OFFSET = 3 * Math.PI / 2;

    private final static double ROOT_TWO_OVER_TWO = 1 / Math.sqrt(2);
    private final static double ROOT_TWO_OVER_FOUR = ROOT_TWO_OVER_TWO / 2;
    private final static String TAG = HolonomicDrive.class.getSimpleName();
    private final static Configuration DEFAULT_CONF = new Configuration() {
        @Override
        public String getFLDriveMotorName() {
            return "FrontLeftDrive";
        }

        @Override
        public String getFRDriveMotorName() {
            return "FrontRightDrive";
        }

        @Override
        public String getBRDriveMotorName() {
            return "BackRightDrive";
        }

        @Override
        public String getBLDriveMotorName() {
            return "BackLeftDrive";
        }
    };

    /////////// INNER CLASSES ////////////////

    /**
     * The configuration for a holonomic drive system
     */
    public interface Configuration extends RobotConfiguration {
        /**
         * Gets the name of the front left drive motor
         * @return  the name of the front left drive motor
         */
        String getFLDriveMotorName();

        /**
         * Gets the name of the front right drive motor
         * @return  the name of the front right drive motor
         */
        String getFRDriveMotorName();

        /**
         * Gets the name of the back right drive motor
         * @return  the name of the back right drive motor
         */
        String getBRDriveMotorName();

        /**
         * Gets the name of the back left drive motor
         * @return  the name of the back left drive motor
         */
        String getBLDriveMotorName();
    }

    /////////// CLASS METHODS //////////////

    /**
     * Builds a new instance of {@link HolonomicDrive} using the default names
     * @param hardwareMap   the hardware map for the robot
     * @param logger        the logger for the robot
     * @return              the built {@link HolonomicDrive}
     */
    public static HolonomicDrive build(HardwareMap hardwareMap, RobotLogger logger) {
        return build(hardwareMap, logger, DEFAULT_CONF);
    }

    /**
     * Builds a new instance of {@link HolonomicDrive}
     * @param hardwareMap   the hardware map for the robot
     * @param logger        the logger for the robot
     * @param conf          the robot's configuration
     * @return              the built {@link HolonomicDrive}
     */
    public static HolonomicDrive build(HardwareMap hardwareMap, RobotLogger logger, Configuration conf) {
        return new HolonomicDrive(
                hardwareMap.dcMotor.get(conf.getFLDriveMotorName()),
                hardwareMap.dcMotor.get(conf.getFRDriveMotorName()),
                hardwareMap.dcMotor.get(conf.getBRDriveMotorName()),
                hardwareMap.dcMotor.get(conf.getBLDriveMotorName()),
                logger.child(TAG)
        );
    }

    /**
     * Builds a new instance of {@link HolonomicDrive}
     * @param robot         the robot
     * @param configuration the configuration for the system
     * @return              the built instance
     */
    public static HolonomicDrive build(Robot robot, Configuration configuration) {
        HardwareMap hardwareMap = robot.getHardwareMap();

        return new HolonomicDrive(
                hardwareMap.dcMotor.get(configuration.getFLDriveMotorName()),
                hardwareMap.dcMotor.get(configuration.getFRDriveMotorName()),
                hardwareMap.dcMotor.get(configuration.getBRDriveMotorName()),
                hardwareMap.dcMotor.get(configuration.getBLDriveMotorName()),
                robot.logger
        );
    }

    /////////// FIELDS /////////////////////
    protected final DcMotor frontLeft, frontRight, backRight, backLeft;
    private double offsetAngle = 0;

    /////////// CONSTRUCTORS ///////////////
    /**
     * Creates a new instance of {@link HolonomicDrive}
     * @param frontLeft     the front left motor
     * @param frontRight    the front right motor
     * @param backRight     the back right motor
     * @param backLeft      the back left motor
     * @param logger        the logger
     */
    public HolonomicDrive(
            DcMotor frontLeft,
            DcMotor frontRight,
            DcMotor backRight,
            DcMotor backLeft,
            RobotLogger logger
    ) {
        super(logger);

        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backRight = backRight;
        this.backLeft = backLeft;
    }

    /////////// ACCESSORS //////////////////
    /**
     * Gets the offset angle
     * @return  the offset angle
     */
    public double getOffsetAngle() {
        return offsetAngle;
    }

    /**
     * Sets the offset angle. This is used to make another side of the robot seem like the front
     * @param offsetAngle   the offset angle in radians
     */
    public void setOffsetAngle(double offsetAngle) {
        setOffsetAngle(offsetAngle, DEFAULT_DIRECTION_UNIT);
    }

    /**
     * Sets the offset angle. This is used to make another side of the robot seem like the front
     * @param offsetAngle   the offset angle
     * @param unit          the unit of measure for the offset angle
     */
    public void setOffsetAngle(double offsetAngle, AngleUnit unit) {
        this.offsetAngle = unit.toRadians(offsetAngle);
    }

    /**
     * Gets the magnitude of all the motor's power. This is the vector sum of all the motor's powers
     * pointed in the direction of the their holonomic wheel
     * @return  the magnitude of all the motor's power
     */
    public double getPower() {
        confirmRunning("getPower");

        return calcMagnitude(frontLeft.getPower(), frontRight.getPower(), backRight.getPower(), backLeft.getPower());
    }

    /**
     * Gets the magnitude of all the motor's power. This is the vector sum of all the motor's powers
     * pointed in the direction of the their holonomic wheel
     * @param magnitude the magnitude of the power [-2, 2]
     */
    public void setPower(double magnitude) {
        setPower(magnitude, 0, DEFAULT_DIRECTION_UNIT, 0);
    }

    /**
     * Gets the magnitude of all the motor's power. This is the vector sum of all the motor's powers
     * pointed in the direction of the their holonomic wheel
     * @param magnitude the magnitude of the power [-2, 2]
     * @param direction the direction of the power
     * @param rotation  the rotation to perform [-1, 1] positive is to the right
     */
    public void setPower(double magnitude, double direction, double rotation) {
        setPower(magnitude, direction, DEFAULT_DIRECTION_UNIT, rotation);
    }

    /**
     * Gets the magnitude of all the motor's power. This is the vector sum of all the motor's powers
     * pointed in the direction of the their holonomic wheel
     * @param magnitude the magnitude of the power [-2, 2]
     * @param direction the direction of the power
     * @param unit      the unit of measure for the direction
     * @param rotation  the rotation to perform [-1, 1] positive is to the right
     */
    public void setPower(double magnitude, double direction, AngleUnit unit, double rotation) {
        confirmRunning("setPower");

        Range.throwIfRangeIsInvalid(magnitude, -MAX_POWER, MAX_POWER);
        Range.throwIfRangeIsInvalid(rotation, -1, 1);

        logger.debug.log("Magnitude", magnitude);
        logger.debug.log("Direction Offset (rad)", offsetAngle);

        direction = unit.toRadians(direction);
        logger.debug.log("Direction (rad)", direction);

        direction += offsetAngle;
        logger.debug.log("Adjusted direction (rad)", direction);
        logger.debug.log("Rotation", rotation);

        double[] motorPowers = calcMotorValues(magnitude, direction, rotation);

        setMotorPowers(motorPowers[0], motorPowers[1], motorPowers[2], motorPowers[3]);
    }

    /////////// BEHAVIOR ///////////////////

    /**
     * Initializes the holonomic driver
     */
    @Override
    public void init() {
        logger.debug.log("initializing");
        setState(State.INITIALIZED);
    }

    /**
     * Starts the holonomic driver
     */
    @Override
    public void start() {
        preStart();
        logger.debug.log("starting");
        setState(State.RUNNING);
    }

    /**
     * Stops the holonomic driver
     */
    @Override
    public void stop() {
        logger.debug.log("stopping");

        halt();
        setState(State.STOPPED);
    }

    /**
     * Stops the driver's movement
     */
    public void halt() {
        setMotorPowers(0,0,0,0);
    }

    /**
     * Drives the holonomic driver
     * @param power     the power to drive with [-1, 1]
     */
    public void drive(double power) {
        drive(power, 0, DEFAULT_DIRECTION_UNIT, 0);
    }

    /**
     * Drives the holonomic driver
     * @param power     the power to drive with [-1, 1]
     * @param direction the direction to drive
     * @param rotation  the rotation to perform [-1, 1] positive is to the right
     */
    public void drive(double power, double direction, double rotation) {
        drive(power, direction, DEFAULT_DIRECTION_UNIT, rotation);
    }

    /**
     * Drives the holonomic driver
     * @param power     the power to drive with [-1, 1]
     * @param direction the direction to drive
     * @param unit      the unit of measure for the direction
     * @param rotation  the rotation to perform [-1, 1] positive is to the right
     */
    public void drive(double power, double direction, AngleUnit unit, double rotation) {
        confirmRunning("drive");

        Range.throwIfRangeIsInvalid(power, -1, 1);
        Range.throwIfRangeIsInvalid(rotation, -1, 1);

        setPower(power * MAX_POWER, direction, unit, rotation);
    }

    /**
     * Rotates the robot with the given power
     * @param power the power at which to rotate the robot
     */
    public void rotate(double power) {
        setPower(0, 0, power);
    }

    /**
     * Calculates the motor values
     * @param magnitude the magnitude of the vector
     * @param direction the direction of the vector
     * @param rotation  the rotation to perform
     * @return          the motor values {fl, fr, br, bl}
     */
    protected double[] calcMotorValues(double magnitude, double direction, double rotation) {
        double[] values = new double[4];
        double a = ROOT_TWO_OVER_FOUR * magnitude;

        double br = a * (Math.sin(direction) + Math.cos(direction)), fl = -br;
        double bl = a * (Math.cos(direction) - Math.sin(direction)), fr = -bl;

        fl -= rotation;
        fr -= rotation;
        br -= rotation;
        bl -= rotation;

        values[0] = fl;
        values[1] = fr;
        values[2] = br;
        values[3] = bl;

        return values;
    }

    /**
     * Calculates the magnitude of the motors combined power
     * @param fl    the front left motor
     * @param fr    the front right motor
     * @param br    the back right motor
     * @param bl    the back left motor
     * @return      the magnitude
     */
    protected double calcMagnitude(double fl, double fr, double br, double bl) {
        return ROOT_TWO_OVER_TWO * Math.hypot(
                -fl - fr + br + bl,
                -fl + fr + br - bl
        );
    }

    private void setMotorPowers(double fl, double fr, double br, double bl) {
        logger.verbose.log("front left power", fl);
        logger.verbose.log("front right power", fr);
        logger.verbose.log("back right power", br);
        logger.verbose.log("back left power", bl);

        frontLeft.setPower(Range.clip(fl, -1, 1));
        frontRight.setPower(Range.clip(fr, -1, 1));
        backRight.setPower(Range.clip(br, -1, 1));
        backLeft.setPower(Range.clip(bl, -1, 1));
    }
}
