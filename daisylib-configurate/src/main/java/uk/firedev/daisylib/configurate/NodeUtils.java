package uk.firedev.daisylib.configurate;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class NodeUtils {

    public static <T> T get(@NotNull ConfigurationNode root, @NotNull String path, @NotNull Class<T> type) {
        try {
            return root.node(splitPath(path)).get(type);
        } catch (SerializationException exception) {
            throw new RuntimeException("Failed to get object value for path: " + path, exception);
        }
    }

    public static <T> void set(@NotNull ConfigurationNode root, @NotNull String path, @NotNull Class<T> type, T value) {
        try {
            root.node(splitPath(path)).set(type, value);
        } catch (SerializationException exception) {
            throw new RuntimeException("Failed to set value for path: " + path, exception);
        }
    }

    private static Object[] splitPath(@NotNull String path) {
        return path.split("\\.");
    }

}
