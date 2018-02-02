package org.chathamrobotics.nova.async;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A external event loop that operates in it's own thread. The event loop will only run if it has
 * listeners registered to it.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class EventLoop {
    public static final String TAG = EventLoop.class.getSimpleName();

    protected String tag = TAG;

    private final List<Listener> listeners = Collections.synchronizedList(new LinkedList<Listener>());

    private final Runnable pollingLoop = new Runnable() {
        @Override
        public void run() {
            while(true) {
                if (Thread.interrupted()) break;

                for (Listener listener : listeners)
                    listener.run();

                if (listeners.isEmpty()) break;
            }

            Log.d(tag, "Stopping polling thread");
        }
    };

    private Thread pollingThread;

    /**
     * Stops the event loop thread.
     * Note: it will start again if addListener() is called
     */
    public void stop() {
        Thread cache = pollingThread;
        pollingThread = null;
        cache.interrupt();
    }

    /**
     * Adds a listener to the event loop
     * @see Listener
     * @param condition the condition to test
     * @param handler   the handler for when the condition is met
     * @return          whether or not the add was successful
     */
    public boolean addListener(@NonNull Listener.Condition condition, @NonNull Runnable handler) {
        return addListener(new Listener(condition, handler));
    }

    /**
     * Adds a listener to te event loop
     * @param listener  the listener
     * @return          whether or not the add was successful
     */
    public boolean addListener(@NonNull Listener listener) {
        boolean result;

        result = listeners.add(listener);
        if (result) Log.d(tag, "Added listener (" + listener + ")");

        startPolling();

        return result;
    }

    /**
     * Removes all of the listeners
     */
    public void removeAllListeners() {
        listeners.clear();
        Log.d(tag, "removed all listeners");
    }

    /**
     * Removes the listener
     * @param listener  the listener
     * @return          the removed listener. Null if unsuccessful.
     */
    public Listener removeListener(@NonNull Listener listener) {
       boolean result = listeners.remove(listener);
       Log.d(tag, "Removed Listener (" + listener + ")");
       return result ? listener : null;
    }

    private void startPolling() {
        //noinspection ConstantConditions
        if (pollingLoop != null && pollingThread.isAlive()) return;

        pollingThread = new Thread(pollingThread, tag);
        pollingThread.setDaemon(true);

        Log.d(tag, "Starting polling loop thread");

        pollingThread.start();
    }
}
