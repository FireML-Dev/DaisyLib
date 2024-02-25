package uk.firedev.daisylib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
        Component component = MiniMessage.miniMessage().deserialize(s);
        return new ComponentReplacer(component).replace(replacements).build();
    }

    public static Component parseComponent(String s, Map<String, Component> replacements) {
        if (s == null) {
            return Component.text("");
        }
        Component component = MiniMessage.miniMessage().deserialize(s);
        return new ComponentReplacer(component).replace(replacements).build();
    }

    public static List<Component> parseComponentList(List<String> originalList, String... replacements) {
        return originalList.stream().map(string -> parseComponent(string, replacements)).toList();
    }

    public static String toString(Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    public static String toUncoloredString(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static List<String> toStringList(List<Component> components) {
        return components.stream().map(ComponentUtils::toString).toList();
    }

    public static List<String> toUncoloredStringList(List<Component> components) {
        return components.stream().map(ComponentUtils::toUncoloredString).toList();
    }

    public static Component stripStyling(Component component) {
        return Component.text(toUncoloredString(component));
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
        return components.stream().map(component -> parsePlaceholders(component, replacements)).toList();
    }

    public static List<Component> parsePlaceholders(List<Component> components, Map<String, Component> replacements) {
        if (components == null) {
            return new ArrayList<>();
        }
        if (replacements == null || replacements.isEmpty()) {
            return components;
        }
        return components.stream().map(component -> parsePlaceholders(component, replacements)).toList();
    }

    public static void broadcastMessage(Component msg) { Bukkit.broadcast(msg); }

    public static void sendActionBar(Component msg, Player player) { player.sendActionBar(msg); }

    public static Component getMainHandHoverItem(Player player) {
        if (player == null) {
            return parseComponent("<white>[None]</white>");
        }
        return getHoverItem(player.getInventory().getItemInMainHand());
    }

    public static Component getOffHandHoverItem(Player player) {
        if (player == null) {
            return parseComponent("<white>[None]</white>");
        }
        return getHoverItem(player.getInventory().getItemInOffHand());
    }

    public static Component getHoverItem(ItemStack item) {
        if (item == null) {
            return parseComponent("<white>[None]</white>");
        }
        Component hover = item.displayName();
        hover = hover.hoverEvent(item);
        return hover;
    }

    public static boolean isEmpty(Component component) {
        return toUncoloredString(component).isEmpty();
    }

}