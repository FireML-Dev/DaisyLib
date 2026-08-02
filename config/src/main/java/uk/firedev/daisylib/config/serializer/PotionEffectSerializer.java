package uk.firedev.daisylib.config.serializer;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.DaisyLib;

import java.util.Locale;

public class PotionEffectSerializer implements ConfigSerializer<PotionEffect> {

    private static final PotionEffectSerializer INSTANCE = new PotionEffectSerializer();

    private PotionEffectSerializer() {}

    public static @NonNull PotionEffectSerializer get() {
        return INSTANCE;
    }

    public @NonNull String serialize(@NonNull PotionEffect element) {
        return element.getType().toString().toLowerCase() + "," + element.getAmplifier() + "," + element.getDuration();
    }

    public @Nullable PotionEffect deserialize(@Nullable String element) {
        if (element == null) {
            return null;
        }
        if (element.contains(",")) {
            return deserialize(element, ",");
        } else if (element.contains(":")) {
            return deserialize(element, ":");
        } else {
            return null;
        }
    }

    public @Nullable PotionEffect deserialize(@Nullable String element, @NonNull String separator) {
        if (element == null) {
            return null;
        }
        String[] split = element.split(separator);
        if (split.length != 3) {
            DaisyLib.get().getLogging().error("Invalid potion effect string: " + element);
            DaisyLib.get().getLogging().error("The correct format is \"potion,amplifier,duration\".");
            return null;
        }
        NamespacedKey key = NamespacedKey.fromString(split[0].toLowerCase(Locale.ROOT));
        if (key == null) {
            DaisyLib.get().getLogging().warn("Could not fetch a key from " + split[0]);
            return null;
        }
        PotionEffectType type = Registry.POTION_EFFECT_TYPE.get(key);
        if (type == null) {
            DaisyLib.get().getLogging().error("Potion effect type " + split[0] + " is not valid.");
            return null;
        }
        int amplifier;
        try {
            amplifier = Integer.parseInt(split[1]);
        } catch (NumberFormatException exception) {
            DaisyLib.get().getLogging().error("Potion effect amplifier " + split[1] + " is not valid.");
            return null;
        }
        int duration;
        try {
            duration = Integer.parseInt(split[2]);
        } catch (NumberFormatException exception) {
            DaisyLib.get().getLogging().error("Potion effect duration " + split[2] + " is not valid.");
            return null;
        }
        return new PotionEffect(
            type,
            duration * 20,
            amplifier - 1,
            false
        );
    }

}
