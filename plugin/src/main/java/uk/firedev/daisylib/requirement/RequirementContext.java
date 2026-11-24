package uk.firedev.daisylib.requirement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class RequirementContext {

    private NamespacedKey worldKey;
    private Location location;
    private UUID player;

    private RequirementContext(@Nullable World world, @Nullable Location location, @Nullable UUID player) {
        this.worldKey = world == null ? null : world.getKey();
        this.location = location;
        this.player = player;
    }

    public static @NonNull RequirementContext requirementContext(@Nullable World world, @Nullable Location location, @Nullable UUID player) {
        return new RequirementContext(world, location, player);
    }

    public static @NonNull RequirementContext empty() {
        return new RequirementContext(null, null, null);
    }

    public static @NonNull RequirementContext ofPlayer(@NonNull OfflinePlayer player) {
        Location location = player.getLocation();
        return new RequirementContext(
            location == null ? null : location.getWorld(),
            location,
            player.getUniqueId()
        );
    }

    public @Nullable World world() {
        return Bukkit.getWorld(worldKey);
    }

    public void world(@Nullable World world) {
        this.worldKey = world == null ? null : world.getKey();
    }

    public @Nullable Location location() {
        return location;
    }

    public void location(@Nullable Location location) {
        this.location = location;
    }

    public @Nullable OfflinePlayer player() {
        return player == null ? null : Bukkit.getOfflinePlayer(player);
    }

    public @Nullable UUID rawPlayer() {
        return player;
    }

    public void player(@Nullable OfflinePlayer player) {
        this.player = player == null ? null : player.getUniqueId();
    }

}
