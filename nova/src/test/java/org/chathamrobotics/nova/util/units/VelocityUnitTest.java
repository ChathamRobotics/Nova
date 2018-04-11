package org.chathamrobotics.nova.util.units;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 3/30/2018
 */

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class VelocityUnitTest {
    private static final double DELTA = 1e-6;
    private static final double METER_TO_FOOT = 3.28084;

    public static class ConvertToTest {
        @Test
        public void shouldReturnSameValueForSameUnit() {
            assertEquals(1, VelocityUnit.METER_PER_SECOND.convertTo(VelocityUnit.METER_PER_SECOND, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToMeterPerSecond() {
            assertEquals(1, VelocityUnit.METER_PER_SECOND.convertTo(VelocityUnit.METER_PER_SECOND, 1), DELTA);
            assertEquals(1e3 / 3600, VelocityUnit.KILOMETER_PER_HOUR.convertTo(VelocityUnit.METER_PER_SECOND, 1), DELTA);
            assertEquals( 1 / METER_TO_FOOT, VelocityUnit.FEET_PER_SECOND.convertTo(VelocityUnit.METER_PER_SECOND, 1), DELTA);
            assertEquals(5280 / METER_TO_FOOT / 3600, VelocityUnit.MILE_PER_HOUR.convertTo(VelocityUnit.METER_PER_SECOND, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToKilometerPerHour() {
            assertEquals(3600 * 1e-3, VelocityUnit.METER_PER_SECOND.convertTo(VelocityUnit.KILOMETER_PER_HOUR, 1), DELTA);
            assertEquals(1, VelocityUnit.KILOMETER_PER_HOUR.convertTo(VelocityUnit.KILOMETER_PER_HOUR, 1), DELTA);
            assertEquals( 3600 * 1e-3 / METER_TO_FOOT, VelocityUnit.FEET_PER_SECOND.convertTo(VelocityUnit.KILOMETER_PER_HOUR, 1), DELTA);
            assertEquals(5280 / METER_TO_FOOT * 1e-3, VelocityUnit.MILE_PER_HOUR.convertTo(VelocityUnit.KILOMETER_PER_HOUR, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToFeetPerSecond() {
            assertEquals(METER_TO_FOOT, VelocityUnit.METER_PER_SECOND.convertTo(VelocityUnit.FEET_PER_SECOND, 1), DELTA);
            assertEquals(1e3 / 3600 * METER_TO_FOOT, VelocityUnit.KILOMETER_PER_HOUR.convertTo(VelocityUnit.FEET_PER_SECOND, 1), DELTA);
            assertEquals( 1, VelocityUnit.FEET_PER_SECOND.convertTo(VelocityUnit.FEET_PER_SECOND, 1), DELTA);
            assertEquals(5280. / 3600, VelocityUnit.MILE_PER_HOUR.convertTo(VelocityUnit.FEET_PER_SECOND, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToMilePerHour() {
            assertEquals(METER_TO_FOOT * 3600 / 5280, VelocityUnit.METER_PER_SECOND.convertTo(VelocityUnit.MILE_PER_HOUR, 1), DELTA);
            assertEquals(1e3 * METER_TO_FOOT / 5280, VelocityUnit.KILOMETER_PER_HOUR.convertTo(VelocityUnit.MILE_PER_HOUR, 1), DELTA);
            assertEquals( 3600. / 5280, VelocityUnit.FEET_PER_SECOND.convertTo(VelocityUnit.MILE_PER_HOUR, 1), DELTA);
            assertEquals(1, VelocityUnit.MILE_PER_HOUR.convertTo(VelocityUnit.MILE_PER_HOUR, 1), DELTA);
        }
    }

    public static class ConvertFromTest {
        @Test
        public void shouldBeInverseOfConvertTo() {
            assertEquals(
                    VelocityUnit.METER_PER_SECOND.convertTo(VelocityUnit.MILE_PER_HOUR, 1),
                    VelocityUnit.MILE_PER_HOUR.convertFrom(VelocityUnit.METER_PER_SECOND, 1),
                    DELTA
            );
        }
    }

    public static class MeterPerSecondTest {
        @Test
        public void shouldConvertToMeterPerSecond() {
            assertEquals(1e3 / 3600, VelocityUnit.KILOMETER_PER_HOUR.toMetersPerSecond(1), DELTA);
        }

        @Test
        public void shouldConvertFromMeterPerSecond() {
            assertEquals(1, VelocityUnit.KILOMETER_PER_HOUR.fromMetersPerSecond(1e3 / 3600), DELTA);
        }
    }

    public static class KilometerPerHourTest {
        @Test
        public void shouldConvertToMeterPerSecond() {
            assertEquals(1, VelocityUnit.METER_PER_SECOND.toKilometersPerHour(1e3 / 3600), DELTA);
        }

        @Test
        public void shouldConvertFromMeterPerSecond() {
            assertEquals(1e3 / 3600, VelocityUnit.METER_PER_SECOND.fromKilometerPerHour(1), DELTA);
        }
    }

    public static class FeetPerSecondTest {
        @Test
        public void shouldConvertToFeetPerSecond() {
            assertEquals(5280. / 3600, VelocityUnit.MILE_PER_HOUR.toFeetPerSecond(1), DELTA);
        }

        @Test
        public void shouldConvertFromMeterPerSecond() {
            assertEquals(1, VelocityUnit.MILE_PER_HOUR.fromFeetPerSecond(5280. / 3600), DELTA);
        }
    }

    public static class MilesPerHourTest {
        @Test
        public void shouldConvertToMilesPerHour() {
            assertEquals(1, VelocityUnit.FEET_PER_SECOND.toMilesPerHour(5280. / 3600), DELTA);
        }

        @Test
        public void shouldConvertFromMilesPerHour() {
            assertEquals(5280. / 3600, VelocityUnit.FEET_PER_SECOND.fromMilesPerHour(1), DELTA);
        }
    }
}
