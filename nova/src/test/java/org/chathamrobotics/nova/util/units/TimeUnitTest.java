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
public class TimeUnitTest {
    private static final double DELTA = 1e-6;

    public static class ConvertToTest {
        @Test
        public void shouldReturnSameValueForSameUnit() {
            assertEquals(1, TimeUnit.SECOND.convertTo(TimeUnit.SECOND, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToSeconds() {
            assertEquals(1, TimeUnit.SECOND.convertTo(TimeUnit.SECOND, 1), DELTA);
            assertEquals(1e-12, TimeUnit.PICOSECONDS.convertTo(TimeUnit.SECOND, 1), DELTA);
            assertEquals(1e-9, TimeUnit.NANOSECOND.convertTo(TimeUnit.SECOND, 1), DELTA);
            assertEquals(1e-6, TimeUnit.MICROSECOND.convertTo(TimeUnit.SECOND, 1), DELTA);
            assertEquals(1e-3, TimeUnit.MILLISECOND.convertTo(TimeUnit.SECOND, 1), DELTA);
            assertEquals(60, TimeUnit.MINUTE.convertTo(TimeUnit.SECOND, 1), DELTA);
            assertEquals(60*60, TimeUnit.HOUR.convertTo(TimeUnit.SECOND, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToPicoseconds() {
            assertEquals(1e12, TimeUnit.SECOND.convertTo(TimeUnit.PICOSECONDS, 1), DELTA);
            assertEquals(1, TimeUnit.PICOSECONDS.convertTo(TimeUnit.PICOSECONDS, 1), DELTA);
            assertEquals(1e3, TimeUnit.NANOSECOND.convertTo(TimeUnit.PICOSECONDS, 1), DELTA);
            assertEquals(1e6, TimeUnit.MICROSECOND.convertTo(TimeUnit.PICOSECONDS, 1), DELTA);
            assertEquals(1e9, TimeUnit.MILLISECOND.convertTo(TimeUnit.PICOSECONDS, 1), DELTA);
            assertEquals(6e13, TimeUnit.MINUTE.convertTo(TimeUnit.PICOSECONDS, 1), DELTA);
            assertEquals(3.6e15, TimeUnit.HOUR.convertTo(TimeUnit.PICOSECONDS, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToNanoseconds() {
            assertEquals(1e9, TimeUnit.SECOND.convertTo(TimeUnit.NANOSECOND, 1), DELTA);
            assertEquals(1e-3, TimeUnit.PICOSECONDS.convertTo(TimeUnit.NANOSECOND, 1), DELTA);
            assertEquals(1, TimeUnit.NANOSECOND.convertTo(TimeUnit.NANOSECOND, 1), DELTA);
            assertEquals(1e3, TimeUnit.MICROSECOND.convertTo(TimeUnit.NANOSECOND, 1), DELTA);
            assertEquals(1e6, TimeUnit.MILLISECOND.convertTo(TimeUnit.NANOSECOND, 1), DELTA);
            assertEquals(6e10, TimeUnit.MINUTE.convertTo(TimeUnit.NANOSECOND, 1), DELTA);
            assertEquals(3.6e12, TimeUnit.HOUR.convertTo(TimeUnit.NANOSECOND, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToMicroseconds() {
            assertEquals(1e6, TimeUnit.SECOND.convertTo(TimeUnit.MICROSECOND, 1), DELTA);
            assertEquals(1e-6, TimeUnit.PICOSECONDS.convertTo(TimeUnit.MICROSECOND, 1), DELTA);
            assertEquals(1e-3, TimeUnit.NANOSECOND.convertTo(TimeUnit.MICROSECOND, 1), DELTA);
            assertEquals(1, TimeUnit.MICROSECOND.convertTo(TimeUnit.MICROSECOND, 1), DELTA);
            assertEquals(1e3, TimeUnit.MILLISECOND.convertTo(TimeUnit.MICROSECOND, 1), DELTA);
            assertEquals(6e7, TimeUnit.MINUTE.convertTo(TimeUnit.MICROSECOND, 1), DELTA);
            assertEquals(3.6e9, TimeUnit.HOUR.convertTo(TimeUnit.MICROSECOND, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToMilliseconds() {
            assertEquals(1e3, TimeUnit.SECOND.convertTo(TimeUnit.MILLISECOND, 1), DELTA);
            assertEquals(1e-9, TimeUnit.PICOSECONDS.convertTo(TimeUnit.MILLISECOND, 1), DELTA);
            assertEquals(1e-6, TimeUnit.NANOSECOND.convertTo(TimeUnit.MILLISECOND, 1), DELTA);
            assertEquals(1e-3, TimeUnit.MICROSECOND.convertTo(TimeUnit.MILLISECOND, 1), DELTA);
            assertEquals(1, TimeUnit.MILLISECOND.convertTo(TimeUnit.MILLISECOND, 1), DELTA);
            assertEquals(6e4, TimeUnit.MINUTE.convertTo(TimeUnit.MILLISECOND, 1), DELTA);
            assertEquals(3.6e6, TimeUnit.HOUR.convertTo(TimeUnit.MILLISECOND, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToMinutes() {
            assertEquals(1./60, TimeUnit.SECOND.convertTo(TimeUnit.MINUTE, 1), DELTA);
            assertEquals(1./60 * 1e-12, TimeUnit.PICOSECONDS.convertTo(TimeUnit.MINUTE, 1), DELTA);
            assertEquals(1./60 * 1e-9, TimeUnit.NANOSECOND.convertTo(TimeUnit.MINUTE, 1), DELTA);
            assertEquals(1./60 * 1e-6, TimeUnit.MICROSECOND.convertTo(TimeUnit.MINUTE, 1), DELTA);
            assertEquals(1./60 * 1e-3, TimeUnit.MILLISECOND.convertTo(TimeUnit.MINUTE, 1), DELTA);
            assertEquals(1, TimeUnit.MINUTE.convertTo(TimeUnit.MINUTE, 1), DELTA);
            assertEquals(60, TimeUnit.HOUR.convertTo(TimeUnit.MINUTE, 1), DELTA);
        }

        @Test
        public void shouldBeAbleToConvertToHour() {
            assertEquals(1./3600, TimeUnit.SECOND.convertTo(TimeUnit.HOUR, 1), DELTA);
            assertEquals(1./3600 * 1e-12, TimeUnit.PICOSECONDS.convertTo(TimeUnit.HOUR, 1), DELTA);
            assertEquals(1./3600 * 1e-9, TimeUnit.NANOSECOND.convertTo(TimeUnit.HOUR, 1), DELTA);
            assertEquals(1./3600 * 1e-6, TimeUnit.MICROSECOND.convertTo(TimeUnit.HOUR, 1), DELTA);
            assertEquals(1./3600 * 1e-3, TimeUnit.MILLISECOND.convertTo(TimeUnit.HOUR, 1), DELTA);
            assertEquals(1/60., TimeUnit.MINUTE.convertTo(TimeUnit.HOUR, 1), DELTA);
            assertEquals(1, TimeUnit.HOUR.convertTo(TimeUnit.HOUR, 1), DELTA);
        }
    }

    public static class ConvertFromTest {
        @Test
        public void shouldBeInverseOfConvertTo() {
            assertEquals(
                    TimeUnit.MINUTE.convertTo(TimeUnit.MILLISECOND, 1),
                    TimeUnit.MILLISECOND.convertFrom(TimeUnit.MINUTE, 1),
                    DELTA
            );
        }
    }

    public static class SecondTest {
        @Test
        public void shouldConvertToSeconds() {
            assertEquals(60, TimeUnit.MINUTE.toSeconds(1), DELTA);
        }

        @Test
        public void shouldConvertFromSeconds() {
            assertEquals(1, TimeUnit.MINUTE.fromSeconds(60), DELTA);
        }
    }

    public static class PicosecondTest {
        @Test
        public void shouldConvertToPicoseconds() {
            assertEquals(1e12, TimeUnit.SECOND.toPicoseconds(1), DELTA);
        }

        @Test
        public void shouldConvertFromPicoseconds() {
            assertEquals(1, TimeUnit.SECOND.fromPicoseconds(1e12), DELTA);
        }
    }

    public static class NanosecondTest {
        @Test
        public void shouldConvertToNanoseconds() {
            assertEquals(1e9, TimeUnit.SECOND.toNanoseconds(1), DELTA);
        }

        @Test
        public void shouldConvertFromNanoseconds() {
            assertEquals(1, TimeUnit.SECOND.fromNanoseconds(1e9), DELTA);
        }
    }

    public static class MicrosecondTest {
        @Test
        public void shouldConvertToMicrosecond() {
            assertEquals(1e6, TimeUnit.SECOND.toMicroseconds(1), DELTA);
        }

        @Test
        public void shouldConvertFromMicroseconds() {
            assertEquals(1, TimeUnit.SECOND.fromMicroseconds(1e6), DELTA);
        }
    }

    public static class MillisecondTest {
        @Test
        public void shouldConvertToMillisecond() {
            assertEquals(1e3, TimeUnit.SECOND.toMillisecond(1), DELTA);
        }

        @Test
        public void shouldConvertFromMillisecond() {
            assertEquals(1, TimeUnit.SECOND.fromMilliseconds(1e3), DELTA);
        }
    }

    public static class MinuteTest {
        @Test
        public void shouldConvertToMinutes() {
            assertEquals(1, TimeUnit.SECOND.toMinutes(60), DELTA);
        }

        @Test
        public void shouldConvertFromMinutes() {
            assertEquals(60, TimeUnit.SECOND.fromMinutes(1), DELTA);
        }
    }

    public static class HourTest {
        @Test
        public void shouldConvertToHours() {
            assertEquals(1, TimeUnit.SECOND.toHours(60 * 60), DELTA);
        }

        @Test
        public void shouldConvertFromHours() {
            assertEquals(60 * 60, TimeUnit.SECOND.fromHours(1), DELTA);
        }
    }
}
