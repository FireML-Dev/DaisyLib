package uk.firedev.daisylib.common.addons.reward.defaults;

import me.clip.placeholderapi.replacer.Replacer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.DaisyLib;
import uk.firedev.daisylib.common.addons.reward.RewardAddon;

import java.util.Map;

public class CommandRewardAddon extends RewardAddon {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        Location loc = location == null ? player.getLocation() : location;
        String command = value
            .replace("{player}", player.getName())
            .replace("{x}", String.valueOf(loc.getX()))
            .replace("{y}", String.valueOf(loc.getY()))
            .replace("{z}", String.valueOf(loc.getZ()));
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            command
        );
    }

    @Override
    public @NonNull String getKey() {
        return "Command";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLib.get().getPlugin();
    }

}
