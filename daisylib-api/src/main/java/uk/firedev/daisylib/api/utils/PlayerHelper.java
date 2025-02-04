package uk.firedev.daisylib.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerHelper {

    /**
     * Gets an OfflinePlayer object if they have joined the server before.
     * Unlike Spigot, this method will return null if the player has never joined the server.
     * @param uuid The OfflinePlayer to get.
     * @return The OfflinePlayer, or null if they haven't played before
     */
    public static @Nullable OfflinePlayer getOfflinePlayer(@NotNull UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        return hasPlayerBeenOnServer(offlinePlayer) ? offlinePlayer : null;
    }

    /**
     * Gets an OfflinePlayer object if they have joined the server before.
     * Unlike Spigot, this method will return null if the player has never joined the server.
     * @param name The OfflinePlayer to get.
     * @return The OfflinePlayer, or null if they haven't played before
     */
    public static @Nullable OfflinePlayer getOfflinePlayer(@NotNull String name) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        return hasPlayerBeenOnServer(offlinePlayer) ? offlinePlayer : null;
    }

    /**
     * Checks if an OfflinePlayer has ever joined this server.
     * @param offlinePlayer The OfflinePlayer to check.
     * @return Whether the OfflinePlayer has ever joined this server.
     */
    public static boolean hasPlayerBeenOnServer(@Nullable OfflinePlayer offlinePlayer) {
        if (offlinePlayer == null) {
            return false;
        }
        return offlinePlayer.hasPlayedBefore() || offlinePlayer.isOnline();
    }

    /**
     * Gets the block the provided player is standing on.
     * @param player The player to check.
     * @return The block this player is standing on.
     */
    public static @NotNull Block getPlayerStandingOn(@NotNull Player player) {
        return player.getLocation().clone().subtract(0, 0.05, 0).getBlock();
    }

}
