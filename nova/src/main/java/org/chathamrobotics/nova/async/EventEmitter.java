package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */


@SuppressWarnings("unused")
public interface EventEmitter<E> {
    /**
     * Registers a listener on the given event
     * @param event     the event to listen for
     * @param callback  the callback to register
     */
    void on(E event, AsyncCallback callback);

    /**
     * Registers a listener on the given event that will only be called once
     * @param event     the event to listen for
     * @param callback  the callback to register
     */
    void once(E event, AsyncCallback callback);

    /**
     * Registers a listener on the given event
     * @param event     the event to listen for
     * @param callback  the callback to register
     */
    void addListener(E event, AsyncCallback callback);

    /**
     * Removes a listener
     * @param event     the event
     * @param callback  the callback to remove
     */
    void removeListener(E event, AsyncCallback callback);

    /**
     * Removes all listeners for the event
     * @param event     the event
     */
    void removeListeners(E event);

    /**
     * Removes all listeners
     */
    void removeListeners();
}
