package org.chathamrobotics.nova.util;


/**
 * A 2-tuple
 * @param <F>   the type of the first element
 * @param <S>   the type of the second element
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class Pair<F, S> extends Tuple {
    /**
     * Creates a new instance of {@link Pair}
     * @param first     the first value
     * @param second    the second value
     */
    public Pair(F first, S second) {
        super(first, second);
    }

    /**
     * Gets the first value
     * @return  the first value
     */
    public F first() {
        return (F) values[0];
    }

    /**
     * Gets the second value
     * @return  the second value
     */
    public S second() {
        return (S) values[1];
    }

    /**
     * Clones the pair
     * @return  the clone
     */
    @Override
    public Pair clone() {
        return new Pair(values[0], values[1]);
    }
}
