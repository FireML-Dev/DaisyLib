package uk.firedev.daisylib.addons.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.message.string.StringReplacer;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.api.addons.reward.RewardAddon;

public class CommandRewardAddon extends RewardAddon {

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
        return "Command";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getOwningPlugin() {
        return DaisyLib.getInstance();
    }

}
