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
public class DistanceUnitTest {
    private static final double DELTA = 1e-6;
    private static final double METER_TO_FOOT = 3.28084;

    public static class ConvertToTest {
        @Test
        public void shouldReturnSameValueForSameUnit() {
            assertEquals(1, DistanceUnit.METER.convertTo(DistanceUnit.METER, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToMeter() {
            assertEquals(1, DistanceUnit.METER.convertTo(DistanceUnit.METER, 1), DELTA);
            assertEquals(1e-3, DistanceUnit.MILLIMETER.convertTo(DistanceUnit.METER, 1), DELTA);
            assertEquals(1e-2, DistanceUnit.CENTIMETER.convertTo(DistanceUnit.METER, 1), DELTA);
            assertEquals(1e3, DistanceUnit.KILOMETER.convertTo(DistanceUnit.METER, 1), DELTA);

            assertEquals(1 / METER_TO_FOOT, DistanceUnit.FEET.convertTo(DistanceUnit.METER, 1), DELTA);
            assertEquals(1./12 * 1 / METER_TO_FOOT, DistanceUnit.INCH.convertTo(DistanceUnit.METER, 1), DELTA);
            assertEquals(3 / METER_TO_FOOT, DistanceUnit.YARD.convertTo(DistanceUnit.METER, 1), DELTA);
            assertEquals(5280 / METER_TO_FOOT, DistanceUnit.MILE.convertTo(DistanceUnit.METER, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToMillimeter() {
            assertEquals(1e3, DistanceUnit.METER.convertTo(DistanceUnit.MILLIMETER, 1), DELTA);
            assertEquals(1, DistanceUnit.MILLIMETER.convertTo(DistanceUnit.MILLIMETER, 1), DELTA);
            assertEquals(10, DistanceUnit.CENTIMETER.convertTo(DistanceUnit.MILLIMETER, 1), DELTA);
            assertEquals(1e6, DistanceUnit.KILOMETER.convertTo(DistanceUnit.MILLIMETER, 1), DELTA);

            assertEquals(1 / METER_TO_FOOT * 1e3, DistanceUnit.FEET.convertTo(DistanceUnit.MILLIMETER, 1), DELTA);
            assertEquals(1./12 * 1 / METER_TO_FOOT * 1e3, DistanceUnit.INCH.convertTo(DistanceUnit.MILLIMETER, 1), DELTA);
            assertEquals(3 / METER_TO_FOOT * 1e3, DistanceUnit.YARD.convertTo(DistanceUnit.MILLIMETER, 1), DELTA);
            assertEquals(5280 / METER_TO_FOOT * 1e3, DistanceUnit.MILE.convertTo(DistanceUnit.MILLIMETER, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToCentimeter() {
            assertEquals(1e2, DistanceUnit.METER.convertTo(DistanceUnit.CENTIMETER, 1), DELTA);
            assertEquals(1e-1, DistanceUnit.MILLIMETER.convertTo(DistanceUnit.CENTIMETER, 1), DELTA);
            assertEquals(1, DistanceUnit.CENTIMETER.convertTo(DistanceUnit.CENTIMETER, 1), DELTA);
            assertEquals(1e5, DistanceUnit.KILOMETER.convertTo(DistanceUnit.CENTIMETER, 1), DELTA);

            assertEquals(1 / METER_TO_FOOT * 1e2, DistanceUnit.FEET.convertTo(DistanceUnit.CENTIMETER, 1), DELTA);
            assertEquals(1./12 * 1 / METER_TO_FOOT * 1e2, DistanceUnit.INCH.convertTo(DistanceUnit.CENTIMETER, 1), DELTA);
            assertEquals(3 / METER_TO_FOOT * 1e2, DistanceUnit.YARD.convertTo(DistanceUnit.CENTIMETER, 1), DELTA);
            assertEquals(5280 / METER_TO_FOOT * 1e2, DistanceUnit.MILE.convertTo(DistanceUnit.CENTIMETER, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToKilometer() {
            assertEquals(1e-3, DistanceUnit.METER.convertTo(DistanceUnit.KILOMETER, 1), DELTA);
            assertEquals(1e-6, DistanceUnit.MILLIMETER.convertTo(DistanceUnit.KILOMETER, 1), DELTA);
            assertEquals(1e-5, DistanceUnit.CENTIMETER.convertTo(DistanceUnit.KILOMETER, 1), DELTA);
            assertEquals(1, DistanceUnit.KILOMETER.convertTo(DistanceUnit.KILOMETER, 1), DELTA);

            assertEquals(1 / METER_TO_FOOT * 1e-3, DistanceUnit.FEET.convertTo(DistanceUnit.KILOMETER, 1), DELTA);
            assertEquals(1./12 * 1 / METER_TO_FOOT * 1e-3, DistanceUnit.INCH.convertTo(DistanceUnit.KILOMETER, 1), DELTA);
            assertEquals(3 / METER_TO_FOOT * 1e-3, DistanceUnit.YARD.convertTo(DistanceUnit.KILOMETER, 1), DELTA);
            assertEquals(5280 / METER_TO_FOOT * 1e-3, DistanceUnit.MILE.convertTo(DistanceUnit.KILOMETER, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToFeet() {
            assertEquals(1 * METER_TO_FOOT, DistanceUnit.METER.convertTo(DistanceUnit.FEET, 1), DELTA);
            assertEquals(1e-3 * METER_TO_FOOT, DistanceUnit.MILLIMETER.convertTo(DistanceUnit.FEET, 1), DELTA);
            assertEquals(1e-2 * METER_TO_FOOT, DistanceUnit.CENTIMETER.convertTo(DistanceUnit.FEET, 1), DELTA);
            assertEquals(1e3 * METER_TO_FOOT, DistanceUnit.KILOMETER.convertTo(DistanceUnit.FEET, 1), DELTA);

            assertEquals(1, DistanceUnit.FEET.convertTo(DistanceUnit.FEET, 1), DELTA);
            assertEquals(1./12, DistanceUnit.INCH.convertTo(DistanceUnit.FEET, 1), DELTA);
            assertEquals(3, DistanceUnit.YARD.convertTo(DistanceUnit.FEET, 1), DELTA);
            assertEquals(5280, DistanceUnit.MILE.convertTo(DistanceUnit.FEET, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToYard() {
            assertEquals(1 * METER_TO_FOOT / 3, DistanceUnit.METER.convertTo(DistanceUnit.YARD, 1), DELTA);
            assertEquals(1e-3 * METER_TO_FOOT / 3, DistanceUnit.MILLIMETER.convertTo(DistanceUnit.YARD, 1), DELTA);
            assertEquals(1e-2 * METER_TO_FOOT / 3, DistanceUnit.CENTIMETER.convertTo(DistanceUnit.YARD, 1), DELTA);
            assertEquals(1e3 * METER_TO_FOOT / 3, DistanceUnit.KILOMETER.convertTo(DistanceUnit.YARD, 1), DELTA);

            assertEquals(1. / 3, DistanceUnit.FEET.convertTo(DistanceUnit.YARD, 1), DELTA);
            assertEquals(1./12 / 3, DistanceUnit.INCH.convertTo(DistanceUnit.YARD, 1), DELTA);
            assertEquals(1, DistanceUnit.YARD.convertTo(DistanceUnit.YARD, 1), DELTA);
            assertEquals(5280. / 3, DistanceUnit.MILE.convertTo(DistanceUnit.YARD, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToMile() {
            assertEquals(1 * METER_TO_FOOT / 5280, DistanceUnit.METER.convertTo(DistanceUnit.MILE, 1), DELTA);
            assertEquals(1e-3 * METER_TO_FOOT / 5280, DistanceUnit.MILLIMETER.convertTo(DistanceUnit.MILE, 1), DELTA);
            assertEquals(1e-2 * METER_TO_FOOT / 5280, DistanceUnit.CENTIMETER.convertTo(DistanceUnit.MILE, 1), DELTA);
            assertEquals(1e3 * METER_TO_FOOT / 5280, DistanceUnit.KILOMETER.convertTo(DistanceUnit.MILE, 1), DELTA);

            assertEquals(1. / 5280, DistanceUnit.FEET.convertTo(DistanceUnit.MILE, 1), DELTA);
            assertEquals(1./12 / 5280, DistanceUnit.INCH.convertTo(DistanceUnit.MILE, 1), DELTA);
            assertEquals(3. / 5280, DistanceUnit.YARD.convertTo(DistanceUnit.MILE, 1), DELTA);
            assertEquals(1, DistanceUnit.MILE.convertTo(DistanceUnit.MILE, 1), DELTA);
        }
    }

    public static class ConvertFromTest {
        @Test
        public void shouldBeInverseOfConvertTo() {
            assertEquals(
                    DistanceUnit.METER.convertTo(DistanceUnit.MILLIMETER, 1),
                    DistanceUnit.MILLIMETER.convertFrom(DistanceUnit.METER, 1),
                    DELTA
            );
        }
    }

    public static class MeterTest {
        @Test
        public void shouldConvertToMeters() {
            assertEquals(1e3, DistanceUnit.KILOMETER.toMeters(1), DELTA);
        }

        @Test
        public void shouldConvertFromMeters() {
            assertEquals(1, DistanceUnit.KILOMETER.fromMeters(1e3), DELTA);
        }
    }

    public static class MillimeterTest {
        @Test
        public void shouldConvertToMillimeters() {
            assertEquals(1e3, DistanceUnit.METER.toMillimeters(1), DELTA);
        }

        @Test
        public void shouldConvertFromMillimeters() {
            assertEquals(1, DistanceUnit.METER.fromMillimeters(1e3), DELTA);
        }
    }

    public static class CentimeterTest {
        @Test
        public void shouldConvertToCentimeters() {
            assertEquals(1e2, DistanceUnit.METER.toCentimeters(1), DELTA);
        }

        @Test
        public void shouldConvertFromCentimeters() {
            assertEquals(1, DistanceUnit.METER.fromCentimeters(1e2), DELTA);
        }
    }

    public static class KilometerTest {
        @Test
        public void shouldConvertToKilometers() {
            assertEquals(1, DistanceUnit.METER.toKilometers(1e3), DELTA);
        }

        @Test
        public void shouldConvertFromKilometers() {
            assertEquals(1e3, DistanceUnit.METER.fromKilometers(1), DELTA);
        }
    }

    public static class FeetTest {
        @Test
        public void shouldConvertToFeet() {
            assertEquals(3, DistanceUnit.YARD.toFeet(1), DELTA);
        }

        @Test
        public void shouldConvertFromFeet() {
            assertEquals(1, DistanceUnit.YARD.fromFeet(3), DELTA);
        }
    }

    public static class YardTest {
        @Test
        public void shouldConvertToYards() {
            assertEquals(1, DistanceUnit.FEET.toYard(3), DELTA);
        }

        @Test
        public void shouldConvertFromYards() {
            assertEquals(3, DistanceUnit.FEET.fromYard(1), DELTA);
        }
    }

    public static class MileTest {
        @Test
        public void shouldConvertToYards() {
            assertEquals(1, DistanceUnit.FEET.toMile(5280), DELTA);
        }

        @Test
        public void shouldConvertFromYards() {
            assertEquals(5280, DistanceUnit.FEET.fromMile(1), DELTA);
        }
    }
}
