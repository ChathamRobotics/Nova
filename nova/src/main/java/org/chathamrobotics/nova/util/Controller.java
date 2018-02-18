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

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

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
        STATIONARY
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

        // Set callback
//        try {
//            ClassModifier.setPrivateField(gamepad, "callback", new GamepadCallback() {
//                @Override
//                public void gamepadChanged(Gamepad gamepad) {
//                    update();
//                }
//            });
//        } catch (ClassModifier.FieldModificationException e) {
//            Log.w(TAG, "Failed to set callback on gamepad", e);
//            e.printStackTrace();
//        }

    }

    /**
     * Updates all of the controllers values to match the gamepad
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
     * Check whether or not the button is pressed
     * @param state the button state
     * @return      whether or not the button is pressed
     */
    public boolean isPressed(ButtonState state) {
        return state == ButtonState.TAPPED || state == ButtonState.HELD;
    }

    /**
     * Check whether or not the button is pressed
     * @param button    the button
     * @return          whether or not the button is pressed
     */
    public boolean isPressed(boolean button) {
        return button;
    }

    /**
     * Check whether or not the button is pressed
     * @param button    the button
     * @return          whether or not the button is pressed
     */
    public boolean isPressed(float button) {
        return Math.abs(button) < 1e-8;
    }

    /**
     * Checks whether or not the button is tapped
     * @see ButtonState
     * @param state the button's state
     * @return      whether or not the button is tapped
     */
    public boolean isTapped(ButtonState state) {
        return state == ButtonState.TAPPED;
    }

    /**
     * Checks whether or not the button is held
     * @see ButtonState
     * @param state the button's state
     * @return      whether or not the button is held
     */
    public boolean isHeld(ButtonState state) {
        return state == ButtonState.HELD;
    }

    /**
     * Checks whether or not the button is stationary
     * @see ButtonState
     * @param state the button's state
     * @return      whether or not the button is stationary
     */
    public boolean isStationary(ButtonState state) {
        return state == ButtonState.STATIONARY;
    }

    /**
     * Checks whether or not the button is released
     * @see ButtonState
     * @param state the button's state
     * @return      whether or not the button is released
     */
    public boolean isReleased(ButtonState state) {
        return state == ButtonState.RELEASED;
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
