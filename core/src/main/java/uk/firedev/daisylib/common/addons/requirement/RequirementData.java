package uk.firedev.daisylib.common.addons.requirement;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record RequirementData(@Nullable Player player, @Nullable World world, @Nullable Location location, @Nullable ConfigurationSection config) {

    public RequirementData(@NonNull Player player) {
        this(
            player,
            player.getWorld(),
            player.getLocation(),
            null
        );
    }

    public RequirementData(@NonNull ConfigurationSection config) {
        this(
            null,
            null,
            null,
            config
        );
    }

    public RequirementData(@NonNull Player player, @NonNull ConfigurationSection config) {
        this(
            player,
            player.getWorld(),
            player.getLocation(),
            config
        );
    }

}
