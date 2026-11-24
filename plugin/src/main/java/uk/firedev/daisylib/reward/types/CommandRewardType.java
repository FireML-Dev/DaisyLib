package uk.firedev.daisylib.reward.types;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.MessageUtils;
import uk.firedev.daisylib.utils.Scheduling;

public class CommandRewardType extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        String inputCommand = value.replace("{player}", player.getName());
        if (MessageUtils.PAPI_AVAILABLE) {
            inputCommand = PlaceholderAPI.setPlaceholders(player, inputCommand);
        }
        if (location != null) {
            final String worldName = location.getWorld() == null ? "N/A" : location.getWorld().getName();
            inputCommand = inputCommand
                    .replace("{x}", Double.toString(location.getX()))
                    .replace("{y}", Double.toString(location.getY()))
                    .replace("{z}", Double.toString(location.getZ()))
                    .replace("{world}", worldName);
        }
        final String finalCommand = inputCommand;
        Scheduling.runTask(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand));
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Command";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull JavaPlugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return true;
    }
}
