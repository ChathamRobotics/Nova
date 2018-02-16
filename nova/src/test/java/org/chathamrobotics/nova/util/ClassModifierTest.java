package org.chathamrobotics.nova.util;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ClassModifierTest {
    public static class SetPrivateFieldTest {
        interface Tester {
            int getA();
        }

        @Test
        public void shouldBeAbleToChangePrivateField() throws Exception {
            Tester tester = new Tester() {
                private int a = 1;

                @Override
                public int getA() {
                    return a;
                }
            };

            assertEquals(1, tester.getA());
            ClassModifier.setPrivateField(tester, "a", 2);
            assertEquals(2, tester.getA());
        }

        @Test
        public void shouldBeAbleToChangePrivateFinalField() throws Exception {
            Tester tester = new Tester() {
                private final int a = 1;

                @Override
                public int getA() {
                    return a;
                }
            };

            assertEquals(1, tester.getA());
            ClassModifier.setPrivateField(tester, "a", 2);
            assertEquals(2, tester.getA());
        }
    }
}