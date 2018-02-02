package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */


import android.support.annotation.NonNull;

/**
 * The event loop used by nova
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class NovaEventLoop extends EventLoop {
    private static final String TAG = NovaEventLoop.class.getSimpleName();

    private static class InstanceHolder {
        public static final NovaEventLoop theInstance = new NovaEventLoop();
    }

    /**
     * Gets the instance of {@link NovaEventLoop}
     * @return  the instance of {@link NovaEventLoop}
     */
    public static NovaEventLoop getInstance() {
        return InstanceHolder.theInstance;
    }

    protected String tag = TAG;

    // no make instance
    private NovaEventLoop() {}

    public <T> ObjectListener<T> on(
            @NonNull T object,
            @NonNull ObjectListener.Condition<T> condition,
            @NonNull Runnable handler
    ) {
        ObjectListener<T> listener = new ObjectListener<>(object, condition, handler);
        return addListener(listener) == null ? null : listener;
    }
}
