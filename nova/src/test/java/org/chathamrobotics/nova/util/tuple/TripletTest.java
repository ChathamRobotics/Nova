package org.chathamrobotics.nova.util.tuple;

import org.chathamrobotics.nova.util.tuple.Triplet;
import org.chathamrobotics.nova.util.tuple.Tuple;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class TripletTest {
   public static class WithTest {
       @Test
       public void shouldCreateNewTriple() {
           Triplet a = Triplet.with(1, 2, 3);
           assertTrue(a != null);
       }

       @Test
       public void shouldBeEquivalentToInstantiating() {
           Triplet a = Triplet.with(1, 2, 3);
           Triplet b = new Triplet<>(1, 2, 3);

           assertEquals(a, b);
       }
   }

   public static class ConstructorTest {
       @Test
       public void shouldCreate3Tuple() {
           Triplet a = Triplet.with(1, 2, 3);
           Tuple b = Tuple.with(1, 2, 3);

           assertEquals(a, b);
       }
   }

   public static class FirstTest {
       @Test
       public void shouldReturnFistValue() {
           Triplet a = Triplet.with(1, 2, 3);
           assertEquals(1, a.first());
       }
   }

   public static class SecondTest {
       @Test
       public void shouldReturnSecondValue() {
           Triplet a = Triplet.with(1, 2, 3);
           assertEquals(2, a.second());
       }
   }

   public static class ThirdTest {
       @Test
       public void shouldReturnThirdValue() {
           Triplet a = Triplet.with(1, 2, 3);
           assertEquals(3, a.third());
       }
   }

   public static class CloneTest {
       @Test
       public void shouldReturnNewTriplet() {
           Triplet a = Triplet.with(1, 2, 3);
           Triplet b = a.clone();

           assertFalse(a == b);
           assertEquals(a, b);
       }
   }
}