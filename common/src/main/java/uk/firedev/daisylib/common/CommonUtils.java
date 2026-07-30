package uk.firedev.daisylib.common;

import org.jspecify.annotations.NonNull;

public class CommonUtils {

    public static boolean classExists(@NonNull String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

}
