package uk.firedev.daisylib.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.replacers.StringReplacer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class StringUtils {

    public static String parsePlaceholders(@NotNull String s, String... replacements) {
        return new StringReplacer(s).replace(replacements).build();
    }

    public static String parsePlaceholders(@NotNull String s, @NotNull Map<String, String> replacements) {
        if (s.isEmpty() || replacements.isEmpty()) {
            return s;
        }
        return new StringReplacer(s).replace(replacements).build();
    }

    public static List<String> parsePlaceholders(@NotNull List<String> list, String... replacements) {
        return list.stream().map(string -> parsePlaceholders(string, replacements)).toList();
    }

    public static List<String> parsePlaceholders(@NotNull List<String> list, @NotNull Map<String, String> replacements) {
        return list.stream().map(string -> parsePlaceholders(string, replacements)).toList();
    }

    public static String parsePAPI(@NotNull String msg, @NotNull OfflinePlayer player) {
        if (DaisyLib.getInstance().papiEnabled) {
            return PlaceholderAPI.setPlaceholders(player, msg);
        }
        Loggers.log(Level.WARNING, DaisyLib.getInstance().getLogger(), "Tried to parse PlaceholderAPI placeholders, but PlaceholderAPI is not enabled.");
        return msg;
    }

    public static List<String> parsePAPI(@NotNull List<String> list, @NotNull OfflinePlayer player) {
        if (DaisyLib.getInstance().papiEnabled) {
            return list.stream().map(string -> parsePAPI(string, player)).toList();
        }
        Loggers.log(Level.WARNING, DaisyLib.getInstance().getLogger(), "Tried to parse PlaceholderAPI placeholders, but PlaceholderAPI is not enabled.");
        return list;
    }

    public static String listToString(List<String> list, String separator, String... replacements) {
        if (separator == null) {
            separator = "\n";
        }
        list = parsePlaceholders(list, replacements);
        return String.join(separator, list);
    }

    public static List<String> stringToList(String string, String separator, String... replacements) {
        if (separator == null) {
            separator = "\n";
        }
        String[] split = string.split(separator);
        List<String> list = Arrays.stream(split).toList();
        list = parsePlaceholders(list, replacements);
        return list;
    }

    public static String listToString(List<String> list, String separator, Map<String, String> replacements) {
        if (separator == null) {
            separator = "\n";
        }
        list = parsePlaceholders(list, replacements);
        return String.join(separator, list);
    }

    public static List<String> stringToList(String string, String separator, Map<String, String> replacements) {
        if (separator == null) {
            separator = "\n";
        }
        String[] split = string.split(separator);
        List<String> list = Arrays.stream(split).toList();
        list = parsePlaceholders(list, replacements);
        return list;
    }

    public static void broadcastMessage(String msg) {
        Bukkit.broadcast(ComponentUtils.deserializeString(msg));
    }

    public static void sendActionBar(String msg, Player player) { player.sendActionBar(ComponentUtils.deserializeString(msg)); }

}
