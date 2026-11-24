package uk.firedev.daisylib.reward.types;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.config.serializer.PotionEffectSerializer;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.Scheduling;

public class EffectRewardType extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        PotionEffect effect = PotionEffectSerializer.get().deserialize(value);
        if (effect == null) {
            DaisyLib.get().getLogging().warn("Invalid effect for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        Scheduling.runTask(player, () -> player.addPotionEffect(effect));
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Effect";
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
