package uk.firedev.daisylib.config;

import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public final class ConfigUtils {

    public static void move(@NonNull ConfigurationSection config, @NonNull String from, @NonNull String to) {
        Object value = config.get(from);
        if (value != null) {
            config.set(from, null);
            config.set(to, value);
        }
    }

    public static @Nullable List<String> getStringList(@NonNull ConfigurationSection config, @NonNull String path, @Nullable List<String> def) {
        List<?> list = config.getList(path);
        if (list == null) {
            return def;
        }
        return list.stream().map(Object::toString).toList();
    }

}
