package uk.firedev.daisylib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import uk.firedev.daisylib.replacers.ComponentReplacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComponentUtils {

    public static Component parseComponent(String s, String... replacements) {
        if (s == null) {
            return Component.text("");
        }
        Component component = LegacyComponentSerializer.legacySection().deserialize(ColorUtils.convertColors(s, true)).decoration(TextDecoration.ITALIC, false);
        return new ComponentReplacer(component).replace(replacements).build();
    }

    public static Component parseComponent(String s, Map<String, Component> replacements) {
        if (s == null) {
            return Component.text("");
        }
        Component component = LegacyComponentSerializer.legacySection().deserialize(ColorUtils.convertColors(s, true)).decoration(TextDecoration.ITALIC, false);
        return new ComponentReplacer(component).replace(replacements).build();
    }

    public static List<Component> parseComponentList(List<String> originalList, String... replacements) {
        List<Component> newList = new ArrayList<>();
        originalList.forEach(s -> newList.add(parseComponent(s, replacements)));
        return newList;
    }

    public static String toString(Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static String toUncoloredString(Component component) {
        return ColorUtils.removeColors(PlainTextComponentSerializer.plainText().serialize(component), true);
    }

    public static List<String> toStringList(List<Component> components) {
        List<String> newList = new ArrayList<>();
        components.forEach(component -> newList.add(toString(component)));
        return newList;
    }

    public static List<String> toUncoloredStringList(List<Component> components) {
        List<String> newList = new ArrayList<>();
        components.forEach(component -> newList.add(toUncoloredString(component)));
        return newList;
    }

    public static Component stripColors(Component component) {
        String s = toUncoloredString(component);
        return parseComponent(s).mergeStyle(component);
    }

    /**
     * Try not to use this on a Component with a hover/click event.
     * It tries to merge the style after parsing, but it doesn't seem to keep all of it.
     */
    public static Component parseColors(Component component) {
        String s = toString(component);
        s = StringUtils.parseColors(s);
        return parseComponent(s).mergeStyle(component);
    }

    public static Component parsePlaceholders(Component component, String... replacements) {
        if (component == null) {
            return parseComponent("");
        }
        return new ComponentReplacer(component).replace(replacements).build();
    }

    public static Component parsePlaceholders(Component component, Map<String, Component> replacements) {
        if (component == null) {
            return parseComponent("");
        }
        if (replacements == null || replacements.isEmpty()) {
            return component;
        }
        return new ComponentReplacer(component).replace(replacements).build();
    }

    public static List<Component> parsePlaceholders(List<Component> components, String... replacements) {
        if (components == null) {
            return new ArrayList<>();
        }
        List<Component> returnList = new ArrayList<>();
        components.forEach(component -> returnList.add(parsePlaceholders(component, replacements)));
        return returnList;
    }

    public static List<Component> parsePlaceholders(List<Component> components, Map<String, Component> replacements) {
        if (components == null) {
            return new ArrayList<>();
        }
        if (replacements == null || replacements.isEmpty()) {
            return components;
        }
        List<Component> returnList = new ArrayList<>();
        components.forEach(component -> returnList.add(parsePlaceholders(component, replacements)));
        return returnList;
    }

    public static void broadcastMessage(Component msg) {
        Bukkit.broadcast(msg);
    }

    public static void sendActionBar(Component msg, Player player) { player.sendActionBar(msg); }

    public static Component getMainHandHoverItem(Player player) {
        if (player == null) {
            return parseComponent("&f[None]");
        }
        return getHoverItem(player.getInventory().getItemInMainHand());
    }

    public static Component getOffHandHoverItem(Player player) {
        if (player == null) {
            return parseComponent("&f[None]");
        }
        return getHoverItem(player.getInventory().getItemInOffHand());
    }

    public static Component getHoverItem(ItemStack item) {
        if (item == null) {
            return parseComponent("&f[None]");
        }
        Component hover = item.displayName();
        hover = hover.hoverEvent(item);
        return hover;
    }

}