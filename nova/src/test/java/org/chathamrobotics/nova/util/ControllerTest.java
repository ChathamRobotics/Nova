package org.chathamrobotics.nova.util;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/18/2018
 */

import com.qualcomm.robotcore.hardware.Gamepad;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ControllerTest {
    // TODO: write tests for passes to gamepad

    public static class ConstructorTest {
        @SuppressWarnings("unused")
        @Test
        public void shouldCreateNewController() {
            Controller controller = new Controller(new Gamepad());
        }
    }

    public static class UpdateTest {
        @Test
        public void shouldCopyButtonValuesFromGamepad() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.left_stick_x = 0.5f;

            controller.update();

            assertEquals(controller.left_stick_x, gp.left_stick_x, 1e-10);
        }

        @Test
        public void shouldUpdateButtonStates() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.right_bumper = true;

            controller.update();

            assertEquals(controller.rightBumperState, Controller.ButtonState.TAPPED);
        }
    }

    public static class ButtonStateTest {
        @Test
        public void shouldChangeButtonStateAccordingToLastStateAndGamepad() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = true;
            controller.update();

            assertEquals(controller.xState, Controller.ButtonState.TAPPED);

            controller.update();

            assertEquals(controller.xState, Controller.ButtonState.HELD);

            gp.x = false;
            controller.update();

            assertEquals(controller.xState, Controller.ButtonState.RELEASED);

            controller.update();
            assertEquals(controller.xState, Controller.ButtonState.STATIONARY);
        }
    }

    public static class IsPressedTest {
        @Test
        public void shouldReturnTrueIfStateIsTappedOrHeld() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.y = true;
            controller.update();

            assertTrue(Controller.isPressed(controller.yState));
        }

        @Test
        public void shouldReturnFalseIfStateIsReleasedOrStationary() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.a = false;
            controller.update();

            assertFalse(Controller.isPressed(controller.aState));
        }

        @Test
        public void shouldReturnTrueIfValueIsTrue() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.b = true;
            controller.update();

            assertTrue(Controller.isPressed(controller.b));
        }

        @Test
        public void shouldReturnFalseIfValueIsFalse() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.left_bumper = false;
            controller.update();

            assertFalse(Controller.isPressed(controller.left_bumper));
        }

        @Test
        public void shouldReturnTrueIfValueIsNonZero() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.right_stick_y = 1.0f;
            controller.update();

            assertTrue(Controller.isPressed(controller.right_stick_y));

            gp.right_stick_y = 0.1f;
            controller.update();

            assertTrue(Controller.isPressed(controller.right_stick_y));
        }

        @Test
        public void shouldReturnFalseIfValueIsZero() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.right_stick_y = 0;
            controller.update();

            assertFalse(Controller.isPressed(controller.right_stick_y));
        }
    }

    public static class IsTappedTest {
        @Test
        public void shouldReturnTrueIfStateIsTapped() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = true;
            controller.update();
            assertTrue(Controller.isTapped(controller.xState));
        }

        @Test
        public void shouldReturnFalseIfStateIsNotTapped() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = false;
            controller.update();
            assertFalse(Controller.isTapped(controller.xState));
        }
    }

    public static class IsHeldTest {
        @Test
        public void shouldReturnTrueIfStateIsHeld() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = true;
            controller.update();
            controller.update();
            assertTrue(Controller.isHeld(controller.xState));
        }

        @Test
        public void shouldReturnFalseIfStateIsNotHeld() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = false;
            controller.update();
            gp.x = true;
            controller.update();
            assertFalse(Controller.isHeld(controller.xState));
        }
    }

    public static class IsStationaryTest {
        @Test
        public void shouldReturnTrueIfStateIsStationary() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = false;
            controller.update();
            controller.update();
            assertTrue(Controller.isStationary(controller.xState));
        }

        @Test
        public void shouldReturnFalseIfStateIsNotStationary() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = true;
            controller.update();
            gp.x = false;
            controller.update();
            assertFalse(Controller.isHeld(controller.xState));
        }
    }

    public static class IsReleasedTest {
        @Test
        public void shouldReturnTrueIfStateIsReleased() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = true;
            controller.update();
            gp.x = false;
            controller.update();
            assertTrue(Controller.isReleased(controller.xState));
        }

        @Test
        public void shouldReturnFalseIfStateIsNotReleased() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.x = false;
            controller.update();
            gp.x = true;
            controller.update();
            assertFalse(Controller.isHeld(controller.xState));
        }
    }
}
