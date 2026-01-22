package uk.firedev.daisylib.util;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * An easy way to manage cooldowns.
 */
public class CooldownHelper {

    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    private CooldownHelper() {}

    public static CooldownHelper cooldownHelper() { return new CooldownHelper(); }

    public void apply(@NonNull UUID uuid, @NonNull Duration duration) {
        cooldownMap.put(uuid, Instant.now().plus(duration));
    }

    public boolean has(@NonNull UUID uuid) {
        Instant cooldown = cooldownMap.get(uuid);
        if (cooldown == null) {
            return false;
        }
        if (Instant.now().isBefore(cooldown)) {
            return true;
        }
        remove(uuid);
        return false;
    }

    public @Nullable Instant remove(@NonNull UUID uuid) {
        return cooldownMap.remove(uuid);
    }

    public Duration getRemaining(@NonNull UUID uuid) {
        Instant cooldown = cooldownMap.get(uuid);
        Instant now = Instant.now();
        if (cooldown != null && now.isBefore(cooldown)) {
            return Duration.between(now, cooldown);
        } else {
            return Duration.ZERO;
        }
    }

}
