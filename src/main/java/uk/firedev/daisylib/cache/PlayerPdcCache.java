package uk.firedev.daisylib.cache;

import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A cache for player {@link org.bukkit.persistence.PersistentDataContainer}s.
 * <p>
 * As checking an {@link OfflinePlayer}'s PDC is expensive, this caches it so the check is only done once.
 */
public final class PlayerPdcCache {

    private static final Map<UUID, PersistentDataContainerView> CACHE = new HashMap<>();

    private PlayerPdcCache() {}

    public static @NonNull PersistentDataContainerView get(OfflinePlayer player) {
        Player online = player.getPlayer();
        if (online != null) {
            CACHE.remove(player.getUniqueId());
            return online.getPersistentDataContainer();
        }
        return CACHE.computeIfAbsent(player.getUniqueId(), ignored -> player.getPersistentDataContainer());
    }

}
