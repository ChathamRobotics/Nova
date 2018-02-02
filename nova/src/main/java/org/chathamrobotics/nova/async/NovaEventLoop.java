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

    /**
     * Creates a listener on the given object
     * @param object    the object to listen to
     * @param condition the condition for the listener to test
     * @param handler   called when the condition is met
     * @param <T>       the type of the object
     * @return          the listener. Null if unsuccessful
     */
    public <T> ObjectListener<T> on(
            @NonNull T object,
            @NonNull ObjectListener.Condition<T> condition,
            @NonNull AsyncCallback handler
    ) {
        ObjectListener<T> listener = new ObjectListener<>(object, condition, handler);
        return addListener(listener) == null ? null : listener;
    }

    /**
     * Creates a listener on the given object
     * @param object    the object to listen to
     * @param condition the condition for the listener to test
     * @param handler   called when the condition is met
     * @param timeout   the timeout for the listener
     * @param <T>       the type of the object
     * @return          the listener. Null if unsuccessful
     */
    public <T> ObjectListener<T> on(
            @NonNull T object,
            @NonNull ObjectListener.Condition<T> condition,
            @NonNull AsyncCallback handler,
            long timeout
    ) {
        return on(new ObjectListener<>(object, condition, handler), timeout);
    }

    /**
     * Creates a listener on the given object
     * @param listener  the listener to add
     * @param timeout   the timeout for the listener
     * @param <T>       the type of the object
     * @return          the listener. Null if unsuccessful
     */
    public <T> ObjectListener<T> on(@NonNull ObjectListener<T> listener, long timeout) {
        return addListener(Listener.timeout(listener, timeout, this)) != null ? listener : null;
    }

    /**
     * Creates a listener on the given object which will only be handled once
     * @param object    the object to test
     * @param condition the condition for the listener to test
     * @param handler   called when the condition is met
     * @param <T>       the type of the object
     * @return          the listener. Null if unsuccessful
     */
    public <T> ObjectListener<T> once(
            @NonNull T object,
            @NonNull ObjectListener.Condition<T> condition,
            @NonNull AsyncCallback handler
    ) {
        ObjectListener<T> listener = new ObjectListener<>(object, condition, handler);
        return addListener(Listener.once(listener, this)) != null ? listener : null;
    }

    /**
     * Creates a listener on the given object which will only be handled once
     * @param object    the object to test
     * @param condition the condition for the listener to test
     * @param handler   called when the condition is met
     * @param timeout   the timeout on the listener
     * @param <T>       the type of the object
     * @return          the listener. Null if unsuccessful
     */
    public <T> ObjectListener<T> once(
            @NonNull T object,
            @NonNull ObjectListener.Condition<T> condition,
            @NonNull AsyncCallback handler,
            long timeout
    ) {
        return once(new ObjectListener<>(object, condition, handler), timeout);
    }

    /**
     * Creates a listener on the given object which will only be handled once
     * @param listener  the listener to add
     * @param timeout   the timeout on the listener
     * @param <T>       the type of the object
     * @return          the listener. Null if unsuccessful
     */
    public <T> ObjectListener<T> once(@NonNull ObjectListener<T> listener, long timeout) {
        return addListener(
                Listener.once(Listener.timeout(listener, timeout, this), this)
        ) != null ? listener : null;
    }
}
