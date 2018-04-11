package org.chathamrobotics.nova.util.units;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class AngularVelocityUnitTest {
    private static final double DELTA = 1e-10;

    public static class ConvertToTest {
        @Test
        public void shouldReturnSameValueIfSameUnit() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertTo(AngularVelocityUnit.RADIANS_PER_SECOND, 1.0), 1.0, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToRadiansPerSecond() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertTo(AngularVelocityUnit.RADIANS_PER_SECOND, 1.0), 1.0, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.convertTo(AngularVelocityUnit.RADIANS_PER_SECOND, 180), Math.PI, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_SECOND.convertTo(AngularVelocityUnit.RADIANS_PER_SECOND, 0.5), Math.PI, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE.convertTo(AngularVelocityUnit.RADIANS_PER_SECOND, 30), Math.PI, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToDegreesPerSecond() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertTo(AngularVelocityUnit.DEGREES_PER_SECOND, Math.PI), 180, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.convertTo(AngularVelocityUnit.DEGREES_PER_SECOND, 1.0), 1.0, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_SECOND.convertTo(AngularVelocityUnit.DEGREES_PER_SECOND, 0.5), 180, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE.convertTo(AngularVelocityUnit.DEGREES_PER_SECOND, 1), 6, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToRevolutionsPerSecond() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertTo(AngularVelocityUnit.REVOLUTIONS_PER_SECOND, Math.PI), 0.5, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.convertTo(AngularVelocityUnit.REVOLUTIONS_PER_SECOND, 180), 0.5, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_SECOND.convertTo(AngularVelocityUnit.REVOLUTIONS_PER_SECOND, 1.0), 1.0, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE.convertTo(AngularVelocityUnit.REVOLUTIONS_PER_SECOND, 60), 1, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToRevolutionsPerMinute() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertTo(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE, Math.PI), 30, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.convertTo(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE, 180), 30, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_SECOND.convertTo(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE, 1), 60, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE.convertTo(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE, 1.0), 1.0, DELTA);
        }
    }

    public static class ConvertFromTest {
        @Test
        public void shouldReturnSameValueIfSameUnit() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertFrom(AngularVelocityUnit.RADIANS_PER_SECOND, 1.0), 1.0, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertFromRadiansPerSecond() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertFrom(AngularVelocityUnit.RADIANS_PER_SECOND, 1.0), 1.0, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.convertFrom(AngularVelocityUnit.RADIANS_PER_SECOND, Math.PI), 180, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_SECOND.convertFrom(AngularVelocityUnit.RADIANS_PER_SECOND, Math.PI), 0.5, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE.convertFrom(AngularVelocityUnit.RADIANS_PER_SECOND, Math.PI), 30, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertFromDegreesPerSecond() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertFrom(AngularVelocityUnit.DEGREES_PER_SECOND, 180), Math.PI, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.convertFrom(AngularVelocityUnit.DEGREES_PER_SECOND, 1.0), 1.0, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_SECOND.convertFrom(AngularVelocityUnit.DEGREES_PER_SECOND, 180), 0.5, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE.convertFrom(AngularVelocityUnit.DEGREES_PER_SECOND, 6), 1, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertFromRevolutionsPerSecond() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertFrom(AngularVelocityUnit.REVOLUTIONS_PER_SECOND, 0.5), Math.PI, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.convertFrom(AngularVelocityUnit.REVOLUTIONS_PER_SECOND, 0.5), 180, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_SECOND.convertFrom(AngularVelocityUnit.REVOLUTIONS_PER_SECOND, 1.0), 1.0, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE.convertFrom(AngularVelocityUnit.REVOLUTIONS_PER_SECOND, 1), 60, DELTA);
        }

        @Test
        public void shouldBeAbleToConvertFromRevolutionsPerMinute() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.convertFrom(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE, 30), Math.PI, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.convertFrom(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE, 30), 180, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_SECOND.convertFrom(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE, 60), 1, DELTA);
            assertEquals(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE.convertFrom(AngularVelocityUnit.REVOLUTIONS_PER_MINUTE, 1.0), 1.0, DELTA);
        }
    }

    public static class RadianPerSecondConversionTest {
        @Test
        public void shouldConvertToRadiansPerSecond() {
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.toRadiansPerSecond(180), Math.PI, DELTA);
        }

        @Test
        public void shouldConvertFromRadiansPerSecond() {
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.fromRadiansPerSecond(Math.PI), 180, DELTA);
        }
    }

    public static class DegreePerSecondConversionTest {
        @Test
        public void shouldConvertToDegrees() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.toDegreesPerSecond(Math.PI), 180, DELTA);
        }

        @Test
        public void shouldConvertFromDegrees() {
            assertEquals(AngularVelocityUnit.RADIANS_PER_SECOND.fromDegreesPerSecond(180), Math.PI, DELTA);
        }
    }

    public static class RevolutionPerSecondConversionTest {
        @Test
        public void shouldConvertToRevolutions() {
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.toRevolutionsPerSecond(180), 0.5, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.toHertz(180), 0.5, DELTA);
        }

        @Test
        public void shouldConvertFromRevolutions() {
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.fromRevolutionsPerSecond(0.5), 180, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.fromHertz(0.5), 180, DELTA);
        }
    }

    public static class RevolutionPerMinuteConversionTest {
        @Test
        public void shouldConvertToRevolutions() {
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.toRevolutionsPerMinute(6), 1, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.toRPM(6), 1, DELTA);
        }

        @Test
        public void shouldConvertFromRevolutions() {
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.fromRevolutionsPerMinute(1), 6, DELTA);
            assertEquals(AngularVelocityUnit.DEGREES_PER_SECOND.fromRPM(1), 6, DELTA);
        }
    }
}
