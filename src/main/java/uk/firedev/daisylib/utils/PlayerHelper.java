package uk.firedev.daisylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerHelper {

    /**
     * Gets an OfflinePlayer object if they have joined the server before.
     * Unlike Spigot, this method will return null if the player has never joined.
     * @param uuid The OfflinePlayer to get.
     * @return The OfflinePlayer, or null if they haven't played before
     */
    public static @Nullable OfflinePlayer getOfflinePlayer(@NotNull UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.hasPlayedBefore() || Bukkit.getPlayer(uuid) != null) {
            return offlinePlayer;
        }
        return null;
    }

    /**
     * Gets an OfflinePlayer object if they have joined the server before.
     * Unlike Spigot, this method will return null if the player has never joined.
     * @param name The OfflinePlayer to get.
     * @return The OfflinePlayer, or null if they haven't played before
     */
    public static @Nullable OfflinePlayer getOfflinePlayer(@NotNull String name) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        if (offlinePlayer.hasPlayedBefore() || Bukkit.getPlayer(name) != null) {
            return offlinePlayer;
        }
        return null;
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
        return offlinePlayer.hasPlayedBefore() || Bukkit.getPlayer(offlinePlayer.getUniqueId()) != null;
    }

}
