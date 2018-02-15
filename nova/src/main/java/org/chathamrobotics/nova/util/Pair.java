package org.chathamrobotics.nova.util;


/**
 * A 2-tuple
 * @param <F>   the type of the first element
 * @param <S>   the type of the second element
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class Pair<F, S> extends Tuple {
    public Pair(F first, S second) {
        super(first, second);
    }

    public F first() {
        return (F) values[0];
    }

    public S second() {
        return (S) values[1];
    }

    @Override
    public Pair clone() {
        return new Pair(values[0], values[1]);
    }
}
