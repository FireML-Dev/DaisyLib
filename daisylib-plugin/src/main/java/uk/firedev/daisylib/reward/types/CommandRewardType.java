package uk.firedev.daisylib.reward.types;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.message.string.StringReplacer;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;

public class CommandRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        StringReplacer replacer = StringReplacer.create(
                "player", player.getName(),
                "x", String.valueOf(player.getLocation().getX()),
                "y", String.valueOf(player.getLocation().getY()),
                "z", String.valueOf(player.getLocation().getZ())
        );
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacer.replace(value));
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
    public @NotNull Plugin getPlugin() {
        return DaisyLib.getInstance();
    }

}
