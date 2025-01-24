package uk.firedev.daisylib.api.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ObjectUtils {

    /**
     * Checks if a String is a valid Integer.
     * @param str The String to check.
     * @return Is the String an Integer?
     */
    public static boolean isInt(@NotNull String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Gets an Integer from a String
     * @param str The string to use.
     * @return The Integer, or null if it isn't an Integer.
     */
    public static @Nullable Integer getInt(@NotNull String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Checks if a String is a valid Long.
     * @param str The String to check.
     * @return Is the String a Long?
     */
    public static boolean isLong(@NotNull String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Gets a Long from a String
     * @param str The string to use.
     * @return The Long, or null if it isn't a Long.
     */
    public static @Nullable Long getLong(@NotNull String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Checks if a String is a valid Double.
     * @param str The String to check.
     * @return Is the String a Double?
     */
    public static boolean isDouble(@NotNull String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Gets a Double from a String
     * @param str The string to use.
     * @return The Double, or null if it isn't a Double.
     */
    public static @Nullable Double getDouble(@NotNull String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Checks if a String is a valid Float.
     * @param str The String to check.
     * @return Is the String a Float?
     */
    public static boolean isFloat(@NotNull String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Gets a Float from a String
     * @param str The string to use.
     * @return The Float, or null if it isn't a Float.
     */
    public static @Nullable Float getFloat(@NotNull String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Gets the provided index from an array, or returns the default value.
     * @param array The array to use.
     * @param index The index to return.
     * @param def The default value.
     * @return The provided index, or the default value.
     */
    public static <T> T getOrDefault(@NotNull T[] array, int index, T def) {
        try {
            return array[index];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return def;
        }
    }

    /**
     * Gets the provided index from a list, or returns the default value.
     * @param list The list to use.
     * @param index The index to return.
     * @param def The default value.
     * @return The provided index, or the default value.
     */
    public static <T> T getOrDefault(@NotNull List<T> list, int index, T def) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ex) {
            return def;
        }
    }

}
