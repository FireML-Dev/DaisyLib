package uk.firedev.daisylib.config.serializer;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class SoundSerializer implements ConfigSerializer<Sound> {

    private static final SoundSerializer INSTANCE = new SoundSerializer();
    private static final Method VALUE_OF_METHOD;

    static {
        Class<?> soundClass = ReflectionUtils.getClassOrNull("org.bukkit.Sound");
        if (soundClass == null) {
            throw new IllegalStateException("Bukkit Sound class is not available?");
        }
        Method valueOf = ReflectionUtils.getMethodOrNull(soundClass, "valueOf", String.class);
        if (valueOf == null) {
            throw new IllegalStateException("Bukkit Sound#valueOf method is not available?");
        }
        VALUE_OF_METHOD = valueOf;
    }

    private SoundSerializer() {}

    public static @NonNull SoundSerializer get() {
        return INSTANCE;
    }

    public @NonNull String serialize(@NonNull Sound element) {
        return element.name().asString() + "," + element.volume() + "," + element.pitch();
    }

    public @Nullable Sound deserialize(@Nullable String element) {
        return deserialize(element, 1, 1);
    }

    public @Nullable Sound deserialize(@Nullable String element, float defaultVolume, float defaultPitch) {
        if (element == null) {
            return null;
        }
        String[] split = element.split(",");
        if (split.length == 0) {
            return null;
        }
        Sound.Builder sound = resolveSoundType(split[0]);
        if (sound == null) {
            DaisyLib.get().getLogging().warn(split[0] + " is not a valid sound.");
            return null;
        }
        try {
            sound.volume(Float.parseFloat(split[1]));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            sound.volume(defaultVolume);
        }
        try {
            sound.pitch(Float.parseFloat(split[2]));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            sound.pitch(defaultPitch);
        }
        return sound.build();
    }

    private static Sound.@Nullable Builder resolveSoundType(@Nullable String type) {
        if (type == null) {
            return null;
        }
        Key soundKey = type.contains(":") ? NamespacedKey.fromString(type) : null;
        if (soundKey != null) {
            return Sound.sound().type(soundKey);
        } else {
            return resolveBukkitSound(type);
        }
    }

    private static Sound.Builder resolveBukkitSound(String type) {
        try {
            Sound.Type soundType = (Sound.Type) VALUE_OF_METHOD.invoke(null, type.toLowerCase(Locale.ROOT));
            return Sound.sound().type(soundType);
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException exception) {
            DaisyLib.get().getLogging().debug("Failed to fetch Bukkit Sound", exception);
            return null;
        }
    }

}
