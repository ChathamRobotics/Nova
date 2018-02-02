package org.chathamrobotics.nova.util;

/*!
 * Nova
 * Copyright (c) 2017 Chatham Robotics
 * MIT License
 * @Last Modified by: storm
 * @Last Modified time: 2/1/2018
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Util for modifying class fields. Use at your own risk.
 */
@SuppressWarnings("unused")
public final class ClassModifier {
    /**
     * Thrown when field modification fails
     */
    @SuppressWarnings("WeakerAccess")
    static class FieldModificationException extends Exception {
        /**
         * Creates a new instance of {@link FieldModificationException}
         * @param msg   a description of the cause
         */
        public FieldModificationException(String msg) {
            super(msg);
        }

        /**
         * Creates a new instance of {@link FieldModificationException}
         * @param msg   a description of the cause
         * @param t     the cause
         */
        public FieldModificationException(String msg, Throwable t) {
            super(msg, t);
        }

        /**
         * Creates a new instance of {@link FieldModificationException}
         * @param t     the cause
         */
        public FieldModificationException(Throwable t) {
            super(t);
        }
    }

    /**
     * Sets the value of a private field. This is generally advised against. This method works with
     * final fields, but does not work for static fields.
     *
     * <pre>
     * {@code
     * public class Example {
     *      public static main(String[] args) {
     *          A a = new A();
     *
     *          a.getField(); // returns 1234
     *
     *          try {
     *              ClassModifier.setPrivateField(a, "aPrivateField", 0);
     *          } catch( FieldModificationException e ) {e.printStackTrace();}
     *
     *          a.getField(); // returns 0
     *      }
     *
     *      class A {
     *          private final int aPrivateField = 1234;
     *
     *          public int getField() {return aPrivateField;}
     *      }
     * }
     * </pre>
     *
     * @param owner                         the object who's field to change
     * @param fieldName                     the name of the field
     * @param value                         the value to set
     * @throws FieldModificationException   thrown if the modification fails
     */
    public static void setPrivateField(Object owner, String fieldName, Object value) throws FieldModificationException {
        try {
            Field field = owner.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            final int cachedModifiers = field.getModifiers();
            int newModifiers = cachedModifiers & ~Modifier.FINAL;

            try {
                Field modifierField = field.getClass().getDeclaredField("modifiers");
                modifierField.setAccessible(true);

                try {
                    modifierField.setInt(field, newModifiers);

                    try {
                        field.set(owner, value);
                    } catch (IllegalAccessException e) {
                        throw new FieldModificationException("Failed to set value", e);
                    } finally {
                        modifierField.setInt(field, newModifiers);
                    }

                } catch (IllegalAccessException e) {
                    throw new FieldModificationException("Failed to change modifiers", e);
                } finally {
                    modifierField.setAccessible(false);
                }

            } catch (NoSuchFieldException e) {
                throw new FieldModificationException("Failed to find modifiers field", e);
            } finally {
                field.setAccessible(false);
            }

        } catch (NoSuchFieldException e) {
            throw new FieldModificationException("Failed to access " + fieldName + " field", e);
        }
    }

    // no instance of me
    private ClassModifier() {}
}
