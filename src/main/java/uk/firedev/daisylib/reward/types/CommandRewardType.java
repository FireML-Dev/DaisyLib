package uk.firedev.daisylib.reward.types;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.StringUtils;

public class CommandRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value) {
        value = StringUtils.parsePlaceholders(value,
                "player", player.getName(),
                "x", String.valueOf(player.getLocation().getX()),
                "y", String.valueOf(player.getLocation().getY()),
                "z", String.valueOf(player.getLocation().getZ())
        );
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), value);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "COMMAND";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull JavaPlugin getPlugin() {
        return DaisyLib.getInstance();
    }

}
