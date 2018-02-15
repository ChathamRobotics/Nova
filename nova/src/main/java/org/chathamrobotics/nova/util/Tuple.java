package org.chathamrobotics.nova.util;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A immutable finite list
 */
@SuppressWarnings("WeakerAccess")
public abstract class Tuple implements Cloneable, Serializable, Iterable<Object> {
    protected final Object[] values;

    /**
     * Create new instance of {@link Tuple}
     * @param values the values to store in the tuple
     */
    public Tuple(Object... values) {
        this.values = values;
    }

    /**
     * Clones the tuple
     * @return  a copy of the tuple
     */
    public abstract Tuple clone();

    /**
     * Returns the size of the tuple. eg. for a {@link Pair} the size would be 2
     * @return  the size of the tuple
     */
    public int size() {
        return values.length;
    }

    /**
     * Gets the value at the i-th position in the tuple starting at 0
     * @param i the index of the value
     * @return  the value at the i-th position in the tuple
     */
    public Object get(int i) {
        return values[i];
    }

    /**
     * Gets the type of the value at the i-th position in the tuple starting at 0
     * @param i the index of the value
     * @return  the type of the value at the i-th position in the tuple
     */
    public Class getType(int i) {
        return values[i].getClass();
    }

    /**
     * Checks whether or not the tuple contains the given value
     * @param value the value to check for
     * @return      whether or not the tuple contains the value
     */
    public boolean contains(Object value) {
        for (Object val : values) if (val.equals(value)) return true;

        return false;
    }

    /**
     * Gets the iterator for the tuple
     * @return  the iterator for the tuple
     */
    @Override
    public Iterator<Object> iterator() {
        return toList().iterator();
    }

    /**
     * Gets the string representation of the tuple
     * @return  the string representation of the tuple
     */
    public String toString() {
        return Arrays.toString(values);
    }

    /**
     * Converts the tuple to a array
     * @return  the array
     */
    public Object[] toArray() {
        return Arrays.copyOf(values, size());
    }

    /**
     * Converts the tuple to a list
     * @return  the list
     */
    public List<Object> toList() {
        return Arrays.asList(values);
    }

    /**
     * Checks whether or not the given object is equal to this tuple
     * @param o the object to check
     * @return  whether or not the given object is equal to this tuple
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Tuple && Arrays.equals(values, ((Tuple) o).values);
    }
}
