package uk.firedev.daisylib.reward;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public interface RewardType {

    void doReward(@NotNull Player player, @NotNull String key, @NotNull String value, int quantity);

    @NotNull String getIdentifier();

    @NotNull String getAuthor();

    @NotNull JavaPlugin getPlugin();

    default boolean register() {
        return RewardManager.getInstance().registerRewardType(this);
    }

}