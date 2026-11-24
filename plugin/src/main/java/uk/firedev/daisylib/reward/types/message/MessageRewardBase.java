package uk.firedev.daisylib.reward.types.message;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.messages.message.Message;
import uk.firedev.daisylib.messages.message.MessageType;
import uk.firedev.daisylib.reward.RewardType;

/**
 * Base for all Message-based reward types.
 * Handles {@link #give(Player, String, Location)} and {@link #getPlugin()}
 */
abstract class MessageRewardBase extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        Message.message(value, getMessageType())
            .replace("{player}", player.name())
            .parsePlaceholderAPI(player)
            .send(player);
    }

    @Override
    public @NonNull JavaPlugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    public abstract @NonNull MessageType getMessageType();

    @Override
    public boolean checkDependencies() {
        return true;
    }

}
