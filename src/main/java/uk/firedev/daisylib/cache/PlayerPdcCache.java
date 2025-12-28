package uk.firedev.daisylib.cache;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.util.PlayerHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A cache for player PDC values.
 * <p>
 * As checking an {@link OfflinePlayer}'s PDC is expensive, this caches the value so it's only done once per key.
 */
public class PlayerPdcCache {

    private final UUID player;

    private final Map<NamespacedKey, CachedValue<?>> cache = new HashMap<>();

    private PlayerPdcCache(@NotNull UUID player) {
        this.player = player;
    }

    public static @NotNull PlayerPdcCache playerPdcCache(@NotNull UUID player) {
        return new PlayerPdcCache(player);
    }

    public static @NotNull PlayerPdcCache playerPdcCache(@NotNull OfflinePlayer player) {
        return new PlayerPdcCache(player.getUniqueId());
    }

    public <T> @Nullable T get(@NotNull NamespacedKey key, @NotNull PersistentDataType<?, T> type) {
        Player online = Bukkit.getPlayer(player);
        if (online != null) {
            // Remove cached data as the player is online.
            cache.remove(key);
            return online.getPersistentDataContainer().get(key, type);
        }
        return checkCache(key, type);
    }

    public <T> @Nullable T getOrDefault(@NotNull NamespacedKey key, @NotNull PersistentDataType<?, T> type, @NotNull T def) {
        T val = get(key, type);
        return val == null ? def : val;
    }

    private <T> @Nullable T checkCache(@NotNull NamespacedKey key, @NotNull PersistentDataType<?, T> type) {
        CachedValue<?> cachedValue = cache.get(key);
        if (cachedValue == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
            if (!PlayerHelper.hasPlayerBeenOnServer(offlinePlayer)) {
                return null;
            }
            T dataVal = offlinePlayer.getPersistentDataContainer().get(key, type);
            cache.put(key, new CachedValue<>(type, dataVal));
            return dataVal;
        }
        // If the type matches, we can safely cast
        if (cachedValue.type().equals(type)) {
            return type.getComplexType().cast(cachedValue.value());
        } else {
            // If the type does not match, the key is now invalid.
            cache.remove(key);
            return null;
        }
    }

    private record CachedValue<T>(PersistentDataType<?, T> type, T value) {}

}
