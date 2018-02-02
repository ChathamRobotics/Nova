package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */


import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A external event loop that operates in it's own thread
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class EventLoop {
    public static final String TAG = EventLoop.class.getSimpleName();

    private final ConcurrentHashMap<Object, List<Listener>> listeners = new ConcurrentHashMap<>();

    private final Runnable pollingLoop = new Runnable() {
        @Override
        public void run() {
            while(true) {
                if (Thread.interrupted()) break;

                for (Map.Entry<Object, List<Listener>> listenersEntry : listeners.entrySet()) {
                    for (Listener listener : listenersEntry.getValue())
                        synchronized (listenersEntry.getKey()) {
                            listener.run(listenersEntry.getKey());
                        }

                    if (listenersEntry.getValue().size() == 0)
                        listeners.remove(listenersEntry.getKey());
                }

                if (listeners.isEmpty()) break;
            }
        }
    };

    private Thread pollingThread;

    /**
     * Adds a listener to the event loop
     * @see Listener
     * @param key       the listener key. This is was is used to test the condition
     * @param condition the condition to test
     * @param handler   the handler for when the condition is met
     * @param <T>       the type of the key
     * @return          whether or not the add was successful
     */
    public <T> boolean addListener(T key, Listener.Condition<T> condition, Runnable handler) {
        return addListener(key, new Listener<>(condition, handler));
    }

    /**
     * Adds a listener to te event loop
     * @param key       the listener key. This is was is used to test the condition
     * @param listener  the listener
     * @param <T>       the type of the key
     * @return          whether or not the add was successful
     */
    public <T> boolean addListener(T key, Listener<T> listener) {
        List<Listener> tListeners = listeners.get(key);
        boolean result;

        if (tListeners == null) {
            tListeners = Collections.synchronizedList(new LinkedList<Listener>());
            listeners.put(key, tListeners);
        }

        result = tListeners.add(listener);
        if (result) Log.d(TAG, "Added listener on " + key);

        startPolling();

        return result;
    }

    /**
     * Removes all of the listeners
     */
    public void removeAllListeners() {
        listeners.clear();
        Log.d(TAG, "removed all listeners");
    }

    /**
     * Removes all of the listeners for the given key
     * @param key   the key whose listeners should be removed
     * @param <T>   the type of the key
     * @return      the removed listeners. Null if unsuccessful
     */
    public <T> List<Listener> removeAllListeners(T key) {
        List<Listener> result = listeners.remove(key);
        Log.d(TAG, "removed all listeners for " + key);
        return result;
    }

    /**
     * Removes the listener
     * @param key       the listener's key
     * @param listener  the listener
     * @param <T>       the type of the key
     * @return          the removed listener. Null if unsuccessful.
     */
    public <T> Listener<T> removeListener(T key, Listener<T> listener) {
       boolean result = listeners.get(key).remove(listener);
       Log.d(TAG, "Removed " + listener + " from " + key);
       return result ? listener : null;
    }

    private void startPolling() {
        //noinspection ConstantConditions
        if (pollingLoop != null && pollingThread.isAlive()) return;

        pollingThread = new Thread(pollingThread, TAG);
        pollingThread.setDaemon(true);

        Log.d(TAG, "Starting polling loop thread");

        pollingThread.start();
    }
}
