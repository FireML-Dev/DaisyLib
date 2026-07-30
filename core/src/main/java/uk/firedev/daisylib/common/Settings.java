package uk.firedev.daisylib.common;

import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public class Settings {

    // Should debug messages be shown? Defaults to false.
    public static @NonNull Supplier<@NonNull Boolean> ENABLE_DEBUG = () -> false;

    // Should Minecraft messages support legacy characters? Defaults to false.
    public static @NonNull Supplier<@NonNull Boolean> ALLOW_LEGACY_MESSAGES = () -> false;

}
