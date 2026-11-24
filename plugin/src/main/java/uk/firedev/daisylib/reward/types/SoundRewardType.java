package uk.firedev.daisylib.reward.types;

import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.config.serializer.SoundSerializer;
import uk.firedev.daisylib.reward.RewardType;

public class SoundRewardType extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        Sound sound = SoundSerializer.get().deserialize(value);
        if (sound != null) {
            player.playSound(sound, Sound.Emitter.self());
        }
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Sound";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return true;
    }
}
