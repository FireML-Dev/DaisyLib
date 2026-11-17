package uk.firedev.daisylib.utils;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.logging.Logging;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

    /**
     * Fetches a class, or returns null if the class was not found.
     * @param name The name of the class.
     * @see Class#forName(String)
     */
    public static @Nullable Class<?> getClassOrNull(@NonNull String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Fetches a method, or returns null if the method was not found.
     * @param clazz The class to fetch the method from.
     * @param method The method's name.
     * @param parameterTypes The parameters of the method.
     * @see Class#getDeclaredMethod(String, Class[])
     */
    public static @Nullable Method getMethodOrNull(@NonNull Class<?> clazz, @NonNull String method, @NonNull Class<?> @NonNull ... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(method, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Fetches a method, or returns null if the class or method was not found.
     * @param clazzName The name of the class to fetch the method from.
     * @param method The method's name.
     * @param parameterTypes The parameters of the method.
     * @see Class#getDeclaredMethod(String, Class[]) 
     */
    public static @Nullable Method getMethodOrNull(@NonNull String clazzName, @NonNull String method, @NonNull Class<?> @NonNull ... parameterTypes) {
        try {
            Class<?> clazz = Class.forName(clazzName);
            return clazz.getDeclaredMethod(method, parameterTypes);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Invokes a method and returns the value, or returns null if the method could not be invoked.
     * @param method The method to invoke.
     * @param object The object to invoke the method on. Can be null.
     * @param args The args of the method.
     * @see Method#invoke(Object, Object...) 
     */
    public static @Nullable Object invokeMethodOrNull(@NonNull Method method, @Nullable Object object, @NonNull Object @NonNull ... args) {
        try {
            return method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Invokes a method and returns the value, or returns null if the method could not be invoked.
     * @param method The method to invoke.
     * @param clazz The class to cast the result to.
     * @param object The object to invoke the method on. Can be null.
     * @param args The args of the method.
     * @see Method#invoke(Object, Object...)
     * @see Class#cast(Object)
     */
    public static <T> @Nullable T invokeMethodOrNull(@NonNull Method method, @NonNull Class<T> clazz, @NonNull Object object, @NonNull Object @NonNull ... args) {
        try {
            Object value = method.invoke(object, args);
            return clazz.cast(value);
        } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Fetches a field, or returns null if the field was not found.
     * @param clazz The class to fetch the method from.
     * @param field The field's name.
     * @see Class#getDeclaredField(String)
     */
    public static @Nullable Field getFieldOrNull(@NonNull Class<?> clazz, @NonNull String field) {
        try {
            return clazz.getDeclaredField(field);
        } catch (NoSuchFieldException exception) {
            return null;
        }
    }

    /**
     * Fetches a field, or returns null if the class or field was not found.
     * @param clazzName The name of the class to fetch the method from.
     * @param field The field's name.
     * @see Class#getDeclaredField(String)
     */
    public static @Nullable Field getFieldOrNull(@NonNull String clazzName, @NonNull String field) {
        try {
            Class<?> clazz = Class.forName(clazzName);
            return clazz.getDeclaredField(field);
        } catch (NoSuchFieldException | ClassNotFoundException exception) {
            return null;
        }
    }

}
