package uk.firedev.daisylib.reward;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NullMarked
public class Reward {

    // A cache for rewards that could not be given because a player was offline.
    private static final Map<UUID, List<RewardData>> rewardCache = new HashMap<>();

    private final String key;
    private final String value;
    private final RewardType rewardType;

    private Reward(String identifier) throws IllegalArgumentException {
        String[] split = identifier.split(":", 2);
        if (split.length < 2) {
            throw new IllegalArgumentException(identifier + " is not formatted correctly.");
        }
        this.key = split[0];
        this.value = split[1];
        if (this.key.isBlank()) {
            throw new IllegalArgumentException(identifier + " has a blank RewardType key.");
        }
        if (this.value.isBlank()) {
            throw new IllegalArgumentException(identifier + " has a blank value.");
        }
        RewardType rewardType = Registry.REWARD_TYPE.get(this.key);
        if (rewardType == null) {
            throw new IllegalArgumentException(this.key + " is not a valid RewardType.");
        }
        this.rewardType = rewardType;
    }

    /// @return A new Reward with the given identifier (type:value), or null if invalid.
    public static @Nullable Reward reward(String identifier) {
        try {
            return new Reward(identifier);
        } catch (IllegalArgumentException exception) {
            DaisyLib.get().getLogging().debug("Could not create a reward.", exception);
            return null;
        }
    }

    public RewardType getRewardType() {
        return this.rewardType;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public void give(Player player, @Nullable Location location) {
        rewardType.give(player, getValue(), location);
    }

    public void give(OfflinePlayer player, @Nullable Location location) {
        give(player, location, true);
    }

    public void give(OfflinePlayer player, @Nullable Location location, boolean shouldAddToCache) {
        Player online = player.getPlayer();
        if (online != null) {
            give(online, location);
            return;
        }
        if (shouldAddToCache) {
            DaisyLib.get().getLogging().debug("Player " + player.getUniqueId() + " was not online. Caching their rewards.");
            addToCache(player.getUniqueId(), location);
        }
    }

    private void addToCache(UUID uuid, @Nullable Location location) {
        List<RewardData> cached = rewardCache.computeIfAbsent(uuid, _ -> new ArrayList<>());
        cached.add(new RewardData(this, location));
    }

    public static void checkCache(UUID uuid) {
        if (!rewardCache.containsKey(uuid)) {
            DaisyLib.get().getLogging().debug("Player has no cached rewards.");
            return;
        }
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            DaisyLib.get().getLogging().debug("Player was not online. Cannot give rewards.");
            return;
        }
        DaisyLib.get().getLogging().debug("Found cached rewards for " + uuid + ". Giving them all now.");
        List<RewardData> cached = rewardCache.remove(uuid);
        cached.forEach(data -> data.reward().give(player, data.location()));
    }

    record RewardData(Reward reward, @Nullable Location location) {}

}
