package org.chathamrobotics.nova.util.tuple;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

/**
 * A 2-tuple
 *
 * Usage:
 * <pre>{@code
 *      Pair p = new Pair("key", 100);
 *
 *      p.first(); // returns "key"
 *      p.second(); // returns 100
 * }</pre>
 *
 * @see Tuple
 * @param <A>   the type of the first element
 * @param <B>   the type of the second element
 */
@SuppressWarnings({"unchecked", "WeakerAccess", "unused"})
public class Pair<A, B> extends Tuple {

    /**
     * Creates a new {@link Pair}. This method is equivalent to {@code new Pair(foo, bar)}, but
     * can make to make code more readable.
     *
     * Example:
     * <pre>{@code
     *      myList.add(Pair.with(foo, bar));
     * }</pre>
     *
     * @param first     the first value
     * @param second    the second value
     * @return          the pair
     */
    public static <A, B> Pair<A, B> with(A first, B second) {
        return new Pair(first, second);
    }

    /**
     * Creates a new instance of {@link Pair}
     * @param first     the first value
     * @param second    the second value
     */
    public Pair(A first, B second) {
        super(first, second);
    }

    /**
     * Gets the first value
     * @return  the first value
     */
    public A first() {
        return (A) values[0];
    }

    /**
     * Gets the second value
     * @return  the second value
     */
    public B second() {
        return (B) values[1];
    }

    /**
     * Clones the pair. Note this does not clone the values inside the pair
     * @return  the clone
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Pair clone() {
        return new Pair(first(), second());
    }
}
