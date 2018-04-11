package org.chathamrobotics.nova.util.units;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class AngleUnitTest {
    private static final double DELTA = 1e-10;

    public static class FromFTCAngleUnitTest {
        @Test
        public void shouldConvertToNovaAngleUnit() {
            assertEquals(
                    AngleUnit.fromFTCAngleUnit(org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES),
                    AngleUnit.DEGREES
            );

            assertEquals(
                    AngleUnit.fromFTCAngleUnit(org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS),
                    AngleUnit.RADIANS
            );
        }
    }

    public static class ConvertToTest {
        @Test
        public void shouldReturnSameValueIfSameUnit() {
            assertEquals(AngleUnit.RADIANS.convertTo(AngleUnit.RADIANS, 1.0), 1.0, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToRadians() {
            assertEquals(AngleUnit.RADIANS.convertTo(AngleUnit.RADIANS, 1.0), 1.0, DELTA);

            assertEquals(AngleUnit.DEGREES.convertTo(AngleUnit.RADIANS, 180), Math.PI, DELTA);

            assertEquals(AngleUnit.REVOLUTIONS.convertTo(AngleUnit.RADIANS, 0.5), Math.PI, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToDegrees() {
            assertEquals(AngleUnit.RADIANS.convertTo(AngleUnit.DEGREES, Math.PI), 180, DELTA);

            assertEquals(AngleUnit.DEGREES.convertTo(AngleUnit.DEGREES, 1.0), 1.0, DELTA);

            assertEquals(AngleUnit.REVOLUTIONS.convertTo(AngleUnit.DEGREES, 1), 360, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToRevolutions() {
            assertEquals(AngleUnit.RADIANS.convertTo(AngleUnit.REVOLUTIONS, Math.PI), 0.5, DELTA);

            assertEquals(AngleUnit.DEGREES.convertTo(AngleUnit.REVOLUTIONS, 180), 0.5, DELTA);

            assertEquals(AngleUnit.REVOLUTIONS.convertTo(AngleUnit.REVOLUTIONS, 1.0), 1.0, DELTA);
        }
    }

    public static class ConvertFromTest {
        @Test
        public void shouldReturnSameValueIfSameUnit() {
            assertEquals(AngleUnit.RADIANS.convertFrom(AngleUnit.RADIANS, 1.0), 1.0, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertFromRadians() {
            assertEquals(AngleUnit.RADIANS.convertFrom(AngleUnit.RADIANS, 1.0), 1.0, DELTA);

            assertEquals(AngleUnit.DEGREES.convertFrom(AngleUnit.RADIANS, Math.PI), 180, DELTA);

            assertEquals(AngleUnit.REVOLUTIONS.convertFrom(AngleUnit.RADIANS, Math.PI), 0.5, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertFromDegrees() {
            assertEquals(AngleUnit.RADIANS.convertFrom(AngleUnit.DEGREES, 180), Math.PI, DELTA);

            assertEquals(AngleUnit.DEGREES.convertFrom(AngleUnit.DEGREES, 1.0), 1.0, DELTA);

            assertEquals(AngleUnit.REVOLUTIONS.convertFrom(AngleUnit.DEGREES, 180), 0.5, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertFromRevolutions() {
            assertEquals(AngleUnit.RADIANS.convertFrom(AngleUnit.REVOLUTIONS, 0.5), Math.PI, DELTA);

            assertEquals(AngleUnit.DEGREES.convertFrom(AngleUnit.REVOLUTIONS, 0.5), 180, DELTA);

            assertEquals(AngleUnit.REVOLUTIONS.convertFrom(AngleUnit.REVOLUTIONS, 1.0), 1.0, DELTA);
        }
    }

    public static class RadianConversionTest {
        @Test
        public void shouldConvertToRadians() {
            assertEquals(AngleUnit.DEGREES.toRadians(180), Math.PI, DELTA);
        }

        @Test
        public void shouldConvertFromRadians() {
            assertEquals(AngleUnit.DEGREES.fromRadians(Math.PI), 180, DELTA);
        }
    }

    public static class DegreeConversionTest {
        @Test
        public void shouldConvertToDegrees() {
            assertEquals(AngleUnit.RADIANS.toDegrees(Math.PI), 180, DELTA);
        }

        @Test
        public void shouldConvertFromDegrees() {
            assertEquals(AngleUnit.RADIANS.fromDegrees(180), Math.PI, DELTA);
        }
    }

    public static class RevolutionConversionTest {
        @Test
        public void shouldConvertToRevolutions() {
            assertEquals(AngleUnit.DEGREES.toRevolutions(180), 0.5, DELTA);
        }

        @Test
        public void shouldConvertFromRevolutions() {
            assertEquals(AngleUnit.DEGREES.fromRevolutions(0.5), 180, DELTA);
        }
    }
}
