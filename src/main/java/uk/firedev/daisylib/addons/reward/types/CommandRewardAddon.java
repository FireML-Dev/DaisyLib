package uk.firedev.daisylib.addons.reward.types;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.Map;

public class CommandRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        Replacer replacer = Replacer.replacer().addReplacements(Map.of(
                "{player}", player.getName(),
                "{x}", player.getLocation().getX(),
                "{y}", player.getLocation().getY(),
                "{z}", player.getLocation().getZ()
        ));
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(),
            ComponentMessage.componentMessage(Component.text(value))
                .replace(replacer).getAsPlainText()
        );
    }

    @Override
    public @NotNull String getKey() {
        return "Command";
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
