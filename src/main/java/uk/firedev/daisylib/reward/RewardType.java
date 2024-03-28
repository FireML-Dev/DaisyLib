package uk.firedev.daisylib.reward;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public interface RewardType {

    default boolean isApplicable(@NotNull String key) {
        return key.equalsIgnoreCase(getIdentifier());
    }

    void doReward(@NotNull Player player, @NotNull String key, @NotNull String value);

    @NotNull String getIdentifier();

    @NotNull String getAuthor();

    @NotNull JavaPlugin getPlugin();

    default boolean register() {
        return RewardManager.getInstance().registerRewardType(this);
    }

}