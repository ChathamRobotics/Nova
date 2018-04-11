package org.chathamrobotics.nova.system;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/30/2018
 */

import com.qualcomm.robotcore.hardware.DcMotor;

import org.chathamrobotics.nova.mocks.MockMotor;
import org.chathamrobotics.nova.util.RobotLogger;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class HolonomicDriveTest {
    private static final double DELTA = 1e-6;

    private static DcMotor mockMotor() {
        return spy(new MockMotor());
    }

    private static RobotLogger mockLogger() {
        RobotLogger logger = new RobotLogger("TEST", mock(TelemetryImpl.class));
        logger.setLevel(RobotLogger.Level.FATAL);
        return logger;
    }

    private static HolonomicDrive makeDrive() {
        HolonomicDrive drive = new HolonomicDrive(
                mockMotor(),
                mockMotor(),
                mockMotor(),
                mockMotor(),
                mockLogger()
        );

        drive.init();
        drive.start();

        return drive;
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class InitTest {
        @Test
        public void shouldSetStateToInitialized() {
            HolonomicDrive drive = new HolonomicDrive(mockMotor(), mockMotor(), mockMotor(), mockMotor(), mockLogger());
            drive.init();
            assertEquals(drive.getState(), RobotSystem.State.INITIALIZED);
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class StartTest {
        @Test(expected = IllegalStateException.class)
        public void shouldThrowIfNotInitializedFirst() {
            HolonomicDrive drive = new HolonomicDrive(mockMotor(), mockMotor(), mockMotor(), mockMotor(), mockLogger());
            drive.start();
        }

        @Test
        public void shouldSetStateToRunning() {
            HolonomicDrive drive = new HolonomicDrive(mockMotor(), mockMotor(), mockMotor(), mockMotor(), mockLogger());
            drive.init();
            drive.start();
            assertEquals(drive.getState(), RobotSystem.State.RUNNING);
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class StopTest {
        @Test
        public void shouldSetStateToStopped() {
            HolonomicDrive drive = new HolonomicDrive(mockMotor(), mockMotor(), mockMotor(), mockMotor(), mockLogger());
            drive.init();
            drive.start();
            drive.stop();
            assertEquals(drive.getState(), RobotSystem.State.STOPPED);
        }

        @Test
        public void shouldSetPowerToZero() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();
            drive.stop();

            verify(frontLeft).setPower(AdditionalMatchers.eq(0, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(0, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(0, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(0, DELTA));
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class OffsetAngleTest {
        @Test
        public void setAndGetOffsetAngle() {
            HolonomicDrive drive = makeDrive();
            drive.setOffsetAngle(1);
            assertEquals(drive.getOffsetAngle(), 1, DELTA);
        }

        @Test
        public void shouldRotateMotorValues() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();
            drive.setOffsetAngle(HolonomicDrive.LEFT_OFFSET);

            double inverseRootTwo = 1 / Math.sqrt(2);

            drive.setPower(2, 0, 0);
            verify(frontLeft).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class GetPowerTest {
        @Test
        public void shouldReturnSameValueSet() {
            HolonomicDrive drive = makeDrive();

            drive.setPower(1);
            assertEquals(1, drive.getPower(), DELTA);
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class SetPowerTest {
        @Test(expected = IllegalStateException.class)
        public void shouldThrowIfNotStartedYet() {
            HolonomicDrive drive = new HolonomicDrive(mockMotor(), mockMotor(), mockMotor(), mockMotor(), mockLogger());
            drive.setPower(1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void shouldThrowIfMagnitudeIsOutOfRange() {
            makeDrive().setPower(HolonomicDrive.MAX_POWER + 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void shouldThrowIfRotationIsOutOfRange() {
            makeDrive().setPower(1, 0, 2);
        }

        @Test
        public void shouldSetCorrectMotorValuesForGoingRight() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();

            double inverseRootTwo = 1 / Math.sqrt(2);

            drive.setPower(2, 0, 0);
            verify(frontLeft).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
        }

        @Test
        public void shouldSetCorrectMotorValuesForGoingForward() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();

            double inverseRootTwo = 1 / Math.sqrt(2);

            drive.setPower(2, Math.PI / 2, 0);
            verify(frontLeft).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
        }

        @Test
        public void shouldSetCorrectMotorValuesForGoingLeft() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();

            double inverseRootTwo = 1 / Math.sqrt(2);

            drive.setPower(2, Math.PI, 0);
            verify(frontLeft).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
        }

        @Test
        public void shouldSetCorrectMotorValuesForGoingBack() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();

            double inverseRootTwo = 1 / Math.sqrt(2);

            drive.setPower(2, - Math.PI / 2, 0);
            verify(frontLeft).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class DriveTest {
        @Test(expected = IllegalStateException.class)
        public void shouldThrowIfNotStartedYet() {
            HolonomicDrive drive = new HolonomicDrive(mockMotor(), mockMotor(), mockMotor(), mockMotor(), mockLogger());
            drive.drive(1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void shouldThrowIfPowerIsOutOfRange() {
            makeDrive().drive(2);
        }

        @Test(expected = IllegalArgumentException.class)
        public void shouldThrowIfRotationIsOutOfRange() {
            makeDrive().drive(1, 0, 2);
        }

        @Test
        public void shouldScaleToMaxPower() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();

            double inverseRootTwo = 1 / Math.sqrt(2);

            drive.drive(1, 0, 0);
            verify(frontLeft).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(-inverseRootTwo, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(inverseRootTwo, DELTA));
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class RotateTest {
        @Test
        public void shouldSetCorrectMotorValuesForRotatingRight() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();

            drive.drive(0, 0, 1);
            verify(frontLeft).setPower(AdditionalMatchers.eq(-1, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(-1, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(-1, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(-1, DELTA));
        }

        @Test
        public void shouldSetCorrectMotorValuesForRotatingLeft() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();

            drive.drive(0, 0, -1);
            verify(frontLeft).setPower(AdditionalMatchers.eq(1, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(1, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(1, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(1, DELTA));
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public static class HaltTest {
        @Test
        public void shouldStopAllMotors() {
            DcMotor frontLeft = mockMotor(), frontRight = mockMotor(),
                    backLeft = mockMotor(),  backRight = mockMotor();

            HolonomicDrive drive = new HolonomicDrive(frontLeft, frontRight, backLeft, backRight, mockLogger());
            drive.init();
            drive.start();

            drive.halt();
            verify(frontLeft).setPower(AdditionalMatchers.eq(0, DELTA));
            verify(frontRight).setPower(AdditionalMatchers.eq(0, DELTA));
            verify(backRight).setPower(AdditionalMatchers.eq(0, DELTA));
            verify(backLeft).setPower(AdditionalMatchers.eq(0, DELTA));
        }
    }
}
