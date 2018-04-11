package org.chathamrobotics.nova.util.tuple;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class PairTest {
    public static class WithTest {
        @Test
        public void shouldCreateANewPair() {
            Pair p = Pair.with(1, 2);
            assertTrue(p != null);
        }

        @Test
        public void shouldBeEquivalentToInstantiating() {
            Pair a = Pair.with(1, 2);
            Pair b = new Pair<>(1, 2);

            assertTrue(a.equals(b));
        }
    }

    public static class ConstructorTest {
        @Test
        public void shouldCreate2Tuple() {
            Pair a = new Pair<>(1, 2);
            Tuple b = new Tuple(1, 2);

            assertTrue(a instanceof Tuple);
            assertTrue(a.equals(b));
            assertTrue(b.equals(a));
        }
    }

    public static class FirstTest {
        @Test
        public void shouldReturnFirstValue() {
            Pair<Integer, Integer> a = new Pair<>(1, 2);
            assertEquals((Integer) 1, a.first());
        }
    }

    public static class SecondTest {
        @Test
        public void shouldReturnSecondValue() {
            Pair a = new Pair<>(1, 2);
            assertEquals(2, a.second());
        }
    }

    public static class CloneTest {
        @Test
        public void shouldReturnNewPair() {
            Pair a = Pair.with(1, 2);
            Pair b = a.clone();

            assertFalse(a == b);
            assertEquals(a, b);
        }
    }
}