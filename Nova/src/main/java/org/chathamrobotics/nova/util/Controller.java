package org.chathamrobotics.nova.util;


/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robocol.RobocolParsable;

import org.firstinspires.ftc.robotcore.internal.ui.GamepadUser;

import java.lang.reflect.Field;

/**
 * A utility wrapper for gamepad
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Controller extends Gamepad {
    public static final String TAG = Controller.class.getSimpleName();

    /**
     * The state of a push button
     */
    public enum ButtonState {
        /**
         * When the button is pressed
         */
        TAPPED,
        /**
         * When the button is released
         */
        RELEASED,
        /**
         * When the button is held
         */
        HELD,
        /**
         * When the button is not pressed
         */
        STATIONARY;

        /**
         * Checks whether or not the button state is {@link ButtonState#TAPPED}
         * @return  whether or not the button state is {@link ButtonState#TAPPED}
         */
        public boolean isTapped() {
            return this == TAPPED;
        }


        /**
         * Checks whether or not the button state is {@link ButtonState#HELD}
         * @return  whether or not the button state is {@link ButtonState#HELD}
         */
        public boolean isHeld() {
            return this == HELD;
        }

        /**
         * Checks whether or not the button state is {@link ButtonState#RELEASED}
         * @return  whether or not the button state is {@link ButtonState#RELEASED}
         */
        public boolean isReleased() {
            return this == RELEASED;
        }

        /**
         * Checks whether or not the button state is {@link ButtonState#STATIONARY}
         * @return  whether or not the button state is {@link ButtonState#STATIONARY}
         */
        public boolean isStationary() {
            return this == STATIONARY;
        }

        /**
         * Checks whether or not the button state is {@link ButtonState#TAPPED} or {@link ButtonState#HELD}
         * @return  whether or not the button state is {@link ButtonState#TAPPED} or {@link ButtonState#HELD}
         */
        public boolean isPressed() {
            return isTapped() || isHeld();
        }
    }

    /**
     * Check whether or not the button is pressed
     * @param state the button state
     * @return      whether or not the button is pressed
     */
    public static boolean isPressed(ButtonState state) {
        return state.isPressed();
    }

    /**
     * Check whether or not the button is pressed
     * @param button    the button
     * @return          whether or not the button is pressed
     */
    public static boolean isPressed(boolean button) {
        return button;
    }

    /**
     * Check whether or not the button is pressed
     * @param button    the button
     * @return          whether or not the button is pressed
     */
    public static boolean isPressed(float button) {
        return Math.abs(button) > 1e-8;
    }

    private final Gamepad gamepad;

    /**
     * The state of the dpad up button
     */
    public ButtonState dpadUpState = ButtonState.STATIONARY;

    /**
     * The state of the dpad down button
     */
    public ButtonState dpadDownState = ButtonState.STATIONARY;

    /**
     * The state of the dpad left button
     */
    public ButtonState dpadLeftState = ButtonState.STATIONARY;

    /**
     * The state of the dpad right button
     */
    public ButtonState dpadRightState = ButtonState.STATIONARY;

    /**
     * The state of the a button
     */
    public ButtonState aState = ButtonState.STATIONARY;

    /**
     * The state of the b button
     */
    public ButtonState bState = ButtonState.STATIONARY;

    /**
     * The state of the x button
     */
    public ButtonState xState = ButtonState.STATIONARY;

    /**
     * The state of the y button
     */
    public ButtonState yState = ButtonState.STATIONARY;

    /**
     * The state of the guide button
     */
    public ButtonState guideState = ButtonState.STATIONARY;

    /**
     * The state of the start button
     */
    public ButtonState startState = ButtonState.STATIONARY;

    /**
     * The state of the back button
     */
    public ButtonState backState = ButtonState.STATIONARY;

    /**
     * The state of the left bumper
     */
    public ButtonState leftBumperState = ButtonState.STATIONARY;

    /**
     * The state of the right bumber
     */
    public ButtonState rightBumperState = ButtonState.STATIONARY;

    /**
     * The state of the left stick button
     */
    public ButtonState leftStickButtonState = ButtonState.STATIONARY;

    /**
     * The state of the right stick button
     */
    public ButtonState rightStickButtonState = ButtonState.STATIONARY;

    /**
     * Creates a instance of {@link Controller}
     * @param gamepad   the gamepad to wrap
     */
    public Controller(@NonNull Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    /**
     * Updates all of the controllers values to match the gamepad. This should be called at the
     * beginning of the loop
     */
    public void update() {
        try {
            this.copy(gamepad);
        } catch (RobotCoreException e) {
            Log.w(TAG, "Failed to copy gamepad");
            e.printStackTrace();
        }

        // Button states
        dpadUpState = updateButtonState(dpad_up, dpadUpState);
        dpadDownState = updateButtonState(dpad_down, dpadDownState);
        dpadLeftState = updateButtonState(dpad_left, dpadLeftState);
        dpadRightState = updateButtonState(dpad_right, dpadRightState);

        aState = updateButtonState(a, aState);
        bState = updateButtonState(b, bState);
        xState = updateButtonState(x, xState);
        yState = updateButtonState(y, yState);

        guideState = updateButtonState(guide, guideState);
        startState = updateButtonState(start, startState);
        backState = updateButtonState(back, backState);

        leftBumperState = updateButtonState(left_bumper, leftBumperState);
        rightBumperState = updateButtonState(right_bumper, rightBumperState);

        leftStickButtonState = updateButtonState(left_stick_button, leftStickButtonState);
        rightStickButtonState = updateButtonState(right_stick_button, rightStickButtonState);
    }

    /**
     * @see Gamepad#update(KeyEvent)
     */
    @Override
    public void update(KeyEvent event) {
        gamepad.update(event);
    }

    /**
     * @see Gamepad#update(MotionEvent)
     */
    @Override
    public void update(MotionEvent event) {
        gamepad.update(event);
    }

    /**
     * @see Gamepad#setUser(GamepadUser)
     */
    @Override
    public void setUser(GamepadUser user) {
        gamepad.setUser(user);
    }

    /**
     * @see Gamepad#getUser()
     */
    @Override
    public GamepadUser getUser() {
        return gamepad.getUser();
    }

    /**
     * @see Gamepad#setGamepadId(int)
     */
    @Override
    public void setGamepadId(int id) {
        gamepad.setGamepadId(id);
    }

    /**
     * @see Gamepad#getGamepadId()
     */
    @Override
    public int getGamepadId() {
        return gamepad.getGamepadId();
    }

    /**
     * @see Gamepad#setTimestamp(long)
     */
    @Override
    public void setTimestamp(long timestamp) {
        gamepad.setTimestamp(timestamp);
    }

    /**
     * @see Gamepad#refreshTimestamp()
     */
    @Override
    public void refreshTimestamp() {
        gamepad.refreshTimestamp();
    }

    /**
     * @see Gamepad#reset()
     */
    @Override
    public void reset() {
        gamepad.reset();
        update();
    }

    /**
     * @see Gamepad#setJoystickDeadzone(float)
     */
    @Override
    public void setJoystickDeadzone(float deadzone) {
        gamepad.setJoystickDeadzone(deadzone);
    }

    /**
     * @see Gamepad#getRobocolMsgType()
     */
    @Override
    public MsgType getRobocolMsgType() {
        return gamepad.getRobocolMsgType();
    }

    /**
     * @see Gamepad#type()
     */
    @Override
    public String type() {
        return gamepad.type();
    }

    private ButtonState updateButtonState(boolean isPressed, ButtonState currentState) {
        if (isPressed) {
            if (currentState == ButtonState.TAPPED || currentState == ButtonState.HELD)
                return ButtonState.HELD;

            return ButtonState.TAPPED;
        }

        // unless the button was tapped or held before it is stationary
        if (currentState == ButtonState.STATIONARY || currentState == ButtonState.RELEASED)
            return ButtonState.STATIONARY;

        return ButtonState.RELEASED;
    }
}
