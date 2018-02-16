package org.chathamrobotics.nova.util;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/15/2018
 */

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class TupleTest {
    public static class WithTest {
        @Test
        public void shouldCreateNewTuple() {
            Tuple a = new Tuple(1, 2, 3);
            Tuple b = Tuple.with(1, 2, 3);

            assertTrue(a.equals(b));
        }
    }

    public static class ConstructorTest {
        @Test
        public void shouldBeAbleToInstantiate() {
            Tuple a = new Tuple(1, 2, 3);
        }
    }

    public static class CloneTest {
        @Test
        public void shouldClone() throws Exception {
            Tuple a = new Tuple(1, 1.0, 2d);
            Tuple b = a.clone();

            assertFalse(a == b);
            assertTrue(a.equals(b));
        }

        @Test
        public void shouldNotCloneDeep() throws Exception {
            Tuple a = new Tuple(1, 1.0, 2d);
            Tuple b = a.clone();

            for (int i = 0; i < b.size(); i++) assertTrue(b.get(i) == a.get(i));
        }
    }

    public static class SizeTest {
        @Test
        public void shouldReturnNumberOfElements() throws Exception {
            Tuple a = new Tuple("foo", "bar");

            assertEquals(a.size(), 2);
        }
    }

    public static class GetTest {
        @Test
        public void shouldReturnIthElement() {
            Object[] vals = {1, "foo", true};
            Tuple a = new Tuple(vals);

            assertEquals(a.get(1), vals[1]);
        }

        @Test(expected = IndexOutOfBoundsException.class)
        public void shouldThrowIfOutOfBounds() {
            Tuple a = new Tuple(1, 2, 3);
            a.get(3);
        }
    }

    public static class GetTypeTest {
        @Test
        public void shouldReturnTypeOfIthElement() throws Exception {
            Tuple a = new Tuple(1, "foo", true);

            assertEquals(a.getType(0), Integer.class);
            assertEquals(a.getType(1), String.class);
            assertEquals(a.getType(2), Boolean.class);
        }

        @Test(expected = IndexOutOfBoundsException.class)
        public void shouldThrowIfOutOfBounds() throws Exception {
            Tuple a = new Tuple(1, 2, 3);
            a.getType(3);
        }
    }

    public static class ContainsTest {
        @Test
        public void shouldReturnTrueIfContainsValue() throws Exception {
            Tuple a = new Tuple(1, true, "yes");
            assertTrue(a.contains("yes"));
        }

        @Test
        public void shouldReturnFalseIfDoesNotContainValue() throws Exception {
            Tuple a = new Tuple(1, true, "yes");
            assertFalse(a.contains("foo"));
        }
    }

    public static class IteratorTest {
        @Test
        public void shouldReturnIteratorForList() {
            Object[] vals = {1, true, "foo"};
            Tuple a = new Tuple(vals);

            int i = 0;
            Iterator<Object> iter = a.iterator();

            while (iter.hasNext()) {
                assertEquals(iter.next(), vals[i]);
                i++;
            }
        }
    }

    public static class ToStringTest {
        @Test
        public void shouldReturnArrayString() throws Exception {
            Object[] vals = {1, true, "foo"};
            Tuple a = new Tuple(vals);

            assertEquals(a.toString(), Arrays.toString(vals));
        }
    }

    public static class ToArrayTest {
        @Test
        public void shouldReturnCopyOfArray() {
            Object[] vals = {1, true, "foo"};
            Tuple a = new Tuple(vals);

            assertNotEquals(a.toArray(), vals);
            assertArrayEquals(a.toArray(), vals);
        }
    }

    public static class ToListTest {
        @Test
        public void shouldReturnEquivalentList() throws Exception {
            Object[] vals = {1, true, "foo"};
            Tuple a = new Tuple(vals);

            assertTrue(a.toList().equals(Arrays.asList(vals)));
        }
    }

    public static class EqualsTest {
        @Test
        public void shouldReturnTrueWhenEqual() throws Exception {
            Tuple a = new Tuple("foo", 2);
            Tuple b = new Tuple("foo", 2);

            assertTrue(a.equals(b));
        }

        @Test
        public void shouldReturnFalseWhenNotEqual() throws Exception {
            Tuple a = new Tuple("foo", "bar");
            Tuple b = new Tuple("bar", "foo");

            assertFalse(a.equals(b));
        }

        @Test
        public void shouldReturnFalseIfNotTuple() throws Exception {
            Tuple a = new Tuple(1, 2);
            assertFalse(a.equals("test"));
        }
    }
}