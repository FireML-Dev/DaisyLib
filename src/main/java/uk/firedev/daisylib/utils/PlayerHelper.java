package uk.firedev.daisylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerHelper {

    public static @Nullable OfflinePlayer getOfflinePlayer(@NotNull UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.hasPlayedBefore() || Bukkit.getPlayer(uuid) != null) {
            return offlinePlayer;
        }
        return null;
    }

    public static @Nullable OfflinePlayer getOfflinePlayer(@NotNull String name) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        if (offlinePlayer.hasPlayedBefore() || Bukkit.getPlayer(name) != null) {
            return offlinePlayer;
        }
        return null;
    }

}
