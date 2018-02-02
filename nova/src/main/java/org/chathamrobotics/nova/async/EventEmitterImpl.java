package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */


import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of event emitter
 * @param <E>   the type used for events
 */
@SuppressWarnings("unused")
public class EventEmitterImpl<E> implements EventEmitter<E> {
    private final HashMap<E, List<AsyncCallback>> eventListeners = new HashMap<>();

    /**
     * Registers a listener on the given event that will only be called once
     * @param event     the event to listen for
     * @param callback  the callback to register
     */
    @Override
    public void once(final E event, final AsyncCallback callback) {
        on(event, new AsyncCallback() {
            private int count;

            @Override
            public void run(Throwable thr) {
                count++;

                callback.run(thr);

                if (count >= 1) removeListener(event, this);
            }
        });
    }

    /**
     * Registers a listener on the given event
     * @param event     the event to listen for
     * @param callback  the callback to register
     */
    @Override
    public void on(@NonNull E event, @NonNull AsyncCallback callback) {
        addListener(event, callback);
    }

    /**
     * Registers a listener on the given event
     * @param event     the event to listen for
     * @param callback  the callback to register
     */
    @Override
    public void addListener(@NonNull E event, @NonNull AsyncCallback callback) {
        List<AsyncCallback> listeners = eventListeners.get(event);

        if (listeners == null) {
            listeners = new LinkedList<>();
            eventListeners.put(event, listeners);
        }

        listeners.add(callback);
    }

    /**
     * Removes a listener
     * @param event     the event
     * @param callback  the callback to remove
     */
    @Override
    public void removeListener(@NonNull E event, @NonNull AsyncCallback callback) {
        List<AsyncCallback> listeners = eventListeners.get(event);
        if (listeners != null) listeners.remove(callback);
    }

    /**
     * Removes all listeners for the event
     * @param event     the event
     */
    @Override
    public void removeListeners(E event) {
        eventListeners.remove(event);
    }

    /**
     * Removes all listeners
     */
    @Override
    public void removeListeners() {
        eventListeners.clear();
    }
}
