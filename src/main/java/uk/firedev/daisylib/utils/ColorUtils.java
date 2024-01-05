package uk.firedev.daisylib.utils;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    private static final Map<TextColor, ItemStack> colorHeads = new HashMap<>();

    private static final String bracketHexRegex = "(\\{#)([0-9A-Fa-f]{6})(\\})";
    private static final String adventureHexRegex = "(&#)([0-9A-Fa-f]{6})";
    private static final String legacyColorRegex = "([&§])([0-9a-zA-Z]{6})";

    static {
        addHead(NamedTextColor.BLACK, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY3YTJmMjE4YTZlNmUzOGYyYjU0NWY2YzE3NzMzZjRlZjliYmIyODhlNzU0MDI5NDljMDUyMTg5ZWUifX19");
        addHead(NamedTextColor.DARK_BLUE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmE0NjA1MzAxMmM2OGYyODlhYmNmYjE3YWI4MDQyZDVhZmJhOTVkY2FhOTljOTljMWUwMzYwODg2ZDM1In19fQ==");
        addHead(NamedTextColor.DARK_GREEN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5YjI3ZmNjZDgwOTIxYmQyNjNjOTFkYzUxMWQwOWU5YTc0NjU1NWU2YzljYWQ1MmU4NTYyZWQwMTgyYTJmIn19fQ==");
        addHead(NamedTextColor.DARK_AQUA, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTViOWE0ODQ2N2YwMjEyYWE2ODg2NGU2MzQyMTE2ZjhmNzlhMjc1NDU0YmYyMTVmNjdmNzAxYTZmMmM4MTgifX19");
        addHead(NamedTextColor.DARK_RED, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0ZGMzYzM3NTNiZjViMGI3ZjA4MWNkYjQ5YjgzZDM3NDI4YTEyZTQxODdmNjM0NmRlYzA2ZmFjNTRjZSJ9fX0");
        addHead(NamedTextColor.DARK_PURPLE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMyYWUyY2I4ZDJhZTYxNTE0MWQyYzY1ODkyZjM2NGZjYWRkZjczZmRlYzk5YmUxYWE2ODc0ODYzZWViNWMifX19");
        addHead(NamedTextColor.GOLD, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM0ODg2ZWYzNjJiMmM4MjNhNmFhNjUyNDFjNWM3ZGU3MWM5NGQ4ZWM1ODIyYzUxZTk2OTc2NjQxZjUzZWEzNSJ9fX0=");
        addHead(NamedTextColor.GRAY, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQzY2ZjMjM5MDA2YjI1N2I4YjIwZjg1YTdiZjQyMDI2YzRhZGEwODRjMTQ0OGQwNGUwYzQwNmNlOGEyZWEzMSJ9fX0=");
        addHead(NamedTextColor.DARK_GRAY, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA4ZjMyMzQ2MmZiNDM0ZTkyOGJkNjcyODYzOGM5NDRlZTNkODEyZTE2MmI5YzZiYTA3MGZjYWM5YmY5In19fQ==");
        addHead(NamedTextColor.BLUE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWY4NjkwNDhmMDZkMzE4ZTUwNThiY2EwYTg3NmE1OTg2MDc5ZjQ1YTc2NGQxMmFiMzRhNDkzMWRiNmI4MGFkYyJ9fX0=");
        addHead(NamedTextColor.GREEN, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDI3Y2E0NmY2YTliYjg5YTI0ZmNhZjRjYzBhY2Y1ZTgyODVhNjZkYjc1MjEzNzhlZDI5MDlhZTQ0OTY5N2YifX19");
        addHead(NamedTextColor.AQUA, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMDdjNzhmM2VlNzgzZmVlY2QyNjkyZWJhNTQ4NTFkYTVjNDMyMzA1NWViZDJmNjgzY2QzZTgzMDJmZWE3YyJ9fX0=");
        addHead(NamedTextColor.RED, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZkZTNiZmNlMmQ4Y2I3MjRkZTg1NTZlNWVjMjFiN2YxNWY1ODQ2ODRhYjc4NTIxNGFkZDE2NGJlNzYyNGIifX19");
        addHead(NamedTextColor.LIGHT_PURPLE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VmMGM1NzczZGY1NjBjYzNmYzczYjU0YjVmMDhjZDY5ODU2NDE1YWI1NjlhMzdkNmQ0NGYyZjQyM2RmMjAifX19");
        addHead(NamedTextColor.YELLOW, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY0MTY4MmY0MzYwNmM1YzlhZDI2YmM3ZWE4YTMwZWU0NzU0N2M5ZGZkM2M2Y2RhNDllMWMxYTI4MTZjZjBiYSJ9fX0=");
        addHead(NamedTextColor.WHITE, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY2YTVjOTg5MjhmYTVkNGI1ZDViOGVmYjQ5MDE1NWI0ZGRhMzk1NmJjYWE5MzcxMTc3ODE0NTMyY2ZjIn19fQ==");
    }

    private static void addHead(NamedTextColor chatColor, String textures) {
        colorHeads.put(chatColor, ItemUtils.getHead(textures));
    }

    public static NamedTextColor getNearestChatColor(Color color) {
        TextColor textColor = TextColor.color(color.getRed(), color.getGreen(), color.getBlue());
        return NamedTextColor.nearestTo(textColor);
    }

    public static ItemStack getColorHead(NamedTextColor chatColor) {
        return colorHeads.getOrDefault(chatColor, new ItemStack(Material.DIRT));
    }

    public static TextColor getColor(String colorString, TextColor def) {
        if (colorString != null) {
            try {
                return NamedTextColor.NAMES.value(colorString.toLowerCase());
            } catch (IllegalArgumentException e) {
                return def;
            }
        }
        return def;
    }

    /**
     * Turns all Ampersands into Sections, allowing compatibility with legacy colors
     */
    public static String convertColors(String message, boolean convertHex) {
        if (convertHex) {
            message = convertHex(message);
        }
        return message.replace('&', '§');
    }

    /**
     * Makes a bracket hex color compatible with Adventure's LegacyComponentSerializer
     */
    public static String convertHex(String message) {
        Pattern pattern = Pattern.compile(bracketHexRegex);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            // Get Color Code
            String color = message.substring(matcher.start(), matcher.end());

            // Turn it into an Adventure-Compatible code
            String newColor = color;
            newColor = newColor.replace("{#", "&#");
            newColor = newColor.replace("}", "");

            // Update the Message
            message = message.replace(color, newColor);
            matcher = pattern.matcher(message);
        }
        return message;
    }

    /**
     * Completely removes all hex colors from a String
     */
    public static String removeHex(String message) {

        message = convertHex(message);

        // &#hex color codes
        Pattern pattern = Pattern.compile(adventureHexRegex);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            // Get Color Code
            String color = message.substring(matcher.start(), matcher.end());

            // Remove it.
            message = message.replace(color, "");
            matcher = pattern.matcher(message);
        }
        return message;
    }

    /**
     * Completely removes all colors from a String
     */
    public static String removeColors(String message, boolean removeHex) {
        if (removeHex) {
            message = removeHex(message);
        }

        Pattern pattern = Pattern.compile(legacyColorRegex);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            // Get Color Code
            String color = message.substring(matcher.start(), matcher.end());

            // Remove it.
            message = message.replace(color, "");
            matcher = pattern.matcher(message);
        }
        return message;
    }

    /**
     * Makes a hex color code compatible with legacy
     */
    public static String hexToLegacy(String message) {

        // Convert to &#hex format so we don't need to do this twice
        message = convertHex(message);

        // Turn this into legacy codes
        Pattern pattern = Pattern.compile(adventureHexRegex);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            // Get Color Code
            String color = message.substring(matcher.start(), matcher.end());

            // Turn it into a legacy hex code
            StringBuilder newColor = new StringBuilder();
            newColor.append("§x");
            char[] chars = color.replace("&#", "").toCharArray();
            for (char character : chars) {
                newColor.append("§").append(character);
            }

            // Update the Message
            message = message.replace(color, newColor.toString());
            matcher = pattern.matcher(message);
        }

        return message;
    }

}
