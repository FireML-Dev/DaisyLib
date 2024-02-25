package uk.firedev.daisylib.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.replacers.StringReplacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class StringUtils {

    public static String parseColors(String s, String... replacements) {
        if (s == null) {
            return "";
        }
        s = new StringReplacer(s).replace(replacements).build();
        s = ColorUtils.convertColors(s, true);
        s = ColorUtils.hexToLegacy(s);
        return s;
    }

    public static String stripColors(String s) {
        if (s == null) {
            return "";
        }
        return ColorUtils.removeColors(s, true);
    }

    public static String parsePlaceholders(String s, String... replacements) {
        if (s == null) {
            return "";
        }
        return new StringReplacer(s).replace(replacements).build();
    }

    public static String parsePlaceholders(String s, Map<String, String> replacements) {
        if (s == null) {
            return "";
        }
        if (s.isEmpty() || replacements == null || replacements.isEmpty()) {
            return s;
        }
        return new StringReplacer(s).replace(replacements).build();
    }

    public static List<String> parsePlaceholders(List<String> sl, String... replacements) {
        if (sl == null) {
            return new ArrayList<>();
        }
        List<String> returnList = new ArrayList<>();
        sl.forEach(string -> returnList.add(parsePlaceholders(string, replacements)));
        return returnList;
    }

    public static List<String> parsePlaceholders(List<String> sl, Map<String, String> replacements) {
        if (sl == null) {
            return new ArrayList<>();
        }
        if (sl.isEmpty() || replacements == null || replacements.isEmpty()) {
            return sl;
        }
        List<String> returnList = new ArrayList<>();
        sl.forEach(string -> returnList.add(parsePlaceholders(string, replacements)));
        return returnList;
    }

    public static String parsePAPI(@NotNull String msg, OfflinePlayer player) {
        if (DaisyLib.getInstance().papiEnabled) {
            return PlaceholderAPI.setPlaceholders(player, msg);
        }
        Loggers.log(Level.WARNING, DaisyLib.getInstance().getLogger(), "Tried to parse PlaceholderAPI placeholders, but PlaceholderAPI is not enabled.");
        return msg;
    }

    public static String stringFromList(List<String> list, String... replacements) {
        list = parsePlaceholders(list, replacements);
        return String.join("\n", list);
    }

    public static String stringFromList(List<String> list, Map<String, String> replacements) {
        list = parsePlaceholders(list, replacements);
        return String.join("\n", list);
    }

    public static void broadcastMessage(String msg) {
        Bukkit.broadcast(ComponentUtils.parseComponent(msg));
    }

    public static void sendActionBar(String msg, Player player) { player.sendActionBar(ComponentUtils.parseComponent(msg)); }

}
