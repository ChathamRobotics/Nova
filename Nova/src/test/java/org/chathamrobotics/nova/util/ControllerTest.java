package org.chathamrobotics.nova.util;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/18/2018
 */

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robocol.RobocolParsable;

import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ControllerTest {
    // TODO: find way to suppress logs during test

    public static class ConstructorTest {
        @SuppressWarnings("unused")
        @Test
        public void shouldCreateNewController() {
            Controller controller = new Controller(new Gamepad());
        }
    }

    public static class UpdateTest {
        @Test public void shouldCopyButtonValuesFromGamepad() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.left_stick_x = 0.5f;

            controller.update();

            assertEquals(controller.left_stick_x, gp.left_stick_x, 1e-10);
        }

        @Test public void shouldUpdateButtonStates() {
            Gamepad gp = new Gamepad();
            Controller controller = new Controller(gp);

            gp.right_bumper = true;

            controller.update();

            assertEquals(controller.rightBumperState, Controller.ButtonState.TAPPED);
        }

//        @Test public void shouldNotThrowOnFailedCopy() {
//            class Test extends Gamepad {
//                @Override
//                public byte[] toByteArray() throws RobotCoreException {
//                    throw  new RobotCoreException("THIS IS NOT A REAL ERROR");
//                }
//            }
//
//            Test gp = new Test();
//            Controller controller = new Controller(gp);
//
//            controller.update();
//        }
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

    public static class PassThroughTest {
//        @Test
//        public void shouldCallUpdateKeyEventOnGamepad() {
//            @SuppressWarnings("WeakerAccess")
//            class Test extends Gamepad {
//                public boolean called;
//
//                @Override
//                public void update(KeyEvent event) {
//                    called = true;
//                }
//            }
//
//            Test gpTester = new Test();
//            Controller controller = new Controller(gpTester);
//
//            controller.update(new KeyEvent(0, 0));
//            assertTrue(gpTester.called);
//        }

//        @Test
//        public void shouldCallUpdateMotionEventOnGamepad() {
//            @SuppressWarnings("WeakerAccess")
//            class Test extends Gamepad {
//                public boolean called;
//
//                @Override
//                public void update(MotionEvent event) {
//                    called = true;
//                }
//            }
//
//            Test gpTester = new Test();
//            Controller controller = new Controller(gpTester);
//
//            controller.update(MotionEvent.obtain(1, 1, 1, 1, 1, 1));
//        }

        @Test
        public void shouldCallSetUserOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public void setUser(GamepadUser user) {
                    called = true;
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.setUser(GamepadUser.ONE);
            assertTrue(gpTester.called);
        }

        @Test
        public void shouldCallGetUserOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public GamepadUser getUser() {
                    called = true;
                    return super.getUser();
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.getUser();
            assertTrue(gpTester.called);
        }

        @Test
        public void shouldCallSetGamepadIdOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public void setGamepadId(int id) {
                    called = true;
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.setGamepadId(1);
            assertTrue(gpTester.called);
        }

        @Test
        public void shouldCallGetGamepadIdOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public int getGamepadId() {
                    called = true;
                    return super.getGamepadId();
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.getGamepadId();
            assertTrue(gpTester.called);
        }

        @Test
        public void shouldCallSetTimestampOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public void setTimestamp(long timestamp) {
                    called = true;
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.setTimestamp(1000);
            assertTrue(gpTester.called);
        }

        @Test
        public void shouldCallRefreshTimestampOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public void refreshTimestamp() {
                    called = true;
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.refreshTimestamp();
            assertTrue(gpTester.called);
        }

        // TODO: test that reset updates

        @Test
        public void shouldCallResetOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public void reset() {
                    called = true;
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.reset();
            assertTrue(gpTester.called);
        }

        @Test
        public void shouldCallSetJoystickDeadzoneOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public void setJoystickDeadzone(float deadzone) {
                    called = true;
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.setJoystickDeadzone(1);
            assertTrue(gpTester.called);
        }

        @Test
        public void shouldCallGetRobocolMsgTypeOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public MsgType getRobocolMsgType() {
                    called = true;
                    return super.getRobocolMsgType();
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.getRobocolMsgType();
            assertTrue(gpTester.called);
        }

        @Test
        public void shouldCallTypeOnGamepad() {
            @SuppressWarnings("WeakerAccess")
            class Test extends Gamepad {
                public boolean called;

                @Override
                public String type() {
                    called = true;
                    return super.type();
                }
            }

            Test gpTester = new Test();
            Controller controller = new Controller(gpTester);

            controller.type();
            assertTrue(gpTester.called);
        }
    }
}
