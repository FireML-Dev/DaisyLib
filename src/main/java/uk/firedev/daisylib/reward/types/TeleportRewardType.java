package uk.firedev.daisylib.reward.types;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;

public class TeleportRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        
        String[] split = value.split(",");
        double x;
        double y;
        double z;
        World world;
        try {
            x = Double.parseDouble(split[0]);
            y = Double.parseDouble(split[1]);
            z = Double.parseDouble(split[2]);
        } catch (IndexOutOfBoundsException ex) {
            Loggers.warn(getComponentLogger(), "Invalid location specified for RewardType " + getIdentifier() + ": " + value + ".");
            Loggers.warn(getComponentLogger(), "Format: x,y,z,world");
            return;
        }
        try {
            world = Bukkit.getWorld(split[3]);
        } catch (IndexOutOfBoundsException ex) {
            world = null;
        }
        Location location = new Location(player.getWorld(), x, y, z);
        if (world != null) {
            location.setWorld(world);
        }
        location.setYaw(player.getYaw());
        location.setPitch(player.getPitch());
        player.teleportAsync(location);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "TELEPORT";
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
