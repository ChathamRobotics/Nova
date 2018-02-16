package org.chathamrobotics.nova.util;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/15/2018
 */

/**
 * A 3-tuple
 *
 * Usage:
 * <pre>{@code
 *      Triplet t = new Triplet("key", 100, new Foo());
 *
 *      t.first(); // returns "key"
 *      t.second(); // returns 100
 *      t.third(); // returns third
 * }</pre>
 *
 * @see Tuple
 * @param <A>   the type of the first element
 * @param <B>   the type of the second element
 * @param <C>   the type of the third element
 */
@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
public class Triplet<A, B, C> extends Tuple {

    /**
     * Creates a new {@link Triplet}. This method is equivalent to {@code new Triplet(foo, bar, baz)}, but
     * can make to make code more readable.
     *
     * Example:
     * <pre>{@code
     *      myList.add(Triplet.with(foo, bar, baz));
     * }</pre>
     *
     * @param first     the first value
     * @param second    the second value
     * @param third     the third value
     * @return          the triplet
     */
    public static <A, B, C> Triplet<A, B, C> with(A first, B second, C third) {
        return new Triplet<>(first, second, third);
    }

    /**
     * Creates a new instance of {@link Triplet}
     * @param first     the first value
     * @param second    the second value
     * @param third     the third value
     */
    public Triplet(A first, B second, C third) {
        super(first, second, third);
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
     * Gets the third value
     * @return  the third value
     */
    public C third() {
        return (C) values[2];
    }

    /**
     * Clones the triplet.
     * Note: This does not clone the values inside the triplet
     * @return  the clone
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Triplet clone() {
        return new Triplet(first(), second(), third());
    }
}
