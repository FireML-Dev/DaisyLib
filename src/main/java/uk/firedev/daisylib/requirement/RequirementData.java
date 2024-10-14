package uk.firedev.daisylib.requirement;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class RequirementData {

    private Player player;
    private World world;
    private Location location;

    public RequirementData() {}

    public RequirementData withPlayer(@Nullable Player player) {
        this.player = player;
        return this;
    }

    public @Nullable Player getPlayer() {
        return this.player;
    }

    public RequirementData withWorld(@Nullable World world) {
        this.world = world;
        return this;
    }

    public @Nullable World getWorld() {
        if (this.world != null) {
            return this.world;
        } else if (this.player != null) {
            return this.player.getWorld();
        } else {
            return null;
        }
    }

    public RequirementData withLocation(@Nullable Location location) {
        this.location = location;
        return this;
    }

    public @Nullable Location getLocation() {
        if (this.location != null) {
            return this.location;
        } else if (this.player != null) {
            return this.player.getLocation();
        } else {
            return null;
        }
    }

}
