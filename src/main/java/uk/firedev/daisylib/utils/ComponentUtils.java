package uk.firedev.daisylib.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.message.component.ComponentReplacer;

import java.util.List;
import java.util.Map;

public class ComponentUtils {

    public static Component deserializeString(@NotNull String s, String... replacements) {
        if (s.isEmpty()) {
            return Component.empty();
        }
        Component component = MiniMessage.miniMessage().deserialize(s);
        return new ComponentReplacer().addReplacements(replacements).replace(component);
    }

    public static List<Component> deserializeStringList(@NotNull List<String> originalList, String... replacements) {
        return originalList.stream().map(string -> deserializeString(string, replacements)).toList();
    }

    public static Component deserializeString(@NotNull String s, @NotNull Map<String, Component> replacements) {
        if (s.isEmpty()) {
            return Component.empty();
        }
        Component component = MiniMessage.miniMessage().deserialize(s);
        return new ComponentReplacer().addReplacements(replacements).replace(component);
    }

    public static List<Component> deserializeStringList(@NotNull List<String> originalList, @NotNull Map<String, Component> replacements) {
        return originalList.stream().map(string -> deserializeString(string, replacements)).toList();
    }

    public static String serializeComponent(@NotNull Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    public static List<String> serializeComponentList(@NotNull List<Component> components) {
        return components.stream().map(ComponentUtils::serializeComponent).toList();
    }

    public static String toPlainText(@NotNull Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static List<String> toPlainTextList(@NotNull List<Component> components) {
        return components.stream().map(ComponentUtils::toPlainText).toList();
    }

    public static String toJSON(@NotNull Component component) {
        return JSONComponentSerializer.json().serialize(component);
    }

    public static List<String> toJSONList(@NotNull List<Component> components) {
        return components.stream().map(ComponentUtils::toJSON).toList();
    }

    public static Component fromJSON(@NotNull String json) {
        return JSONComponentSerializer.json().deserialize(json);
    }

    public static List<Component> fromJSONList(@NotNull List<String> jsonList) {
        return jsonList.stream().map(ComponentUtils::fromJSON).toList();
    }

    public static Component stripStyling(@NotNull Component component) {
        return Component.text(toPlainText(component));
    }

    public static List<Component> stripStylingList(@NotNull List<Component> components) {
        return components.stream().map(ComponentUtils::stripStyling).toList();
    }

    public static Component parsePlaceholders(@NotNull Component component, String... replacements) {
        return new ComponentReplacer().addReplacements(replacements).replace(component);
    }

    public static Component parsePlaceholders(@NotNull Component component, @NotNull Map<String, Component> replacements) {
        return new ComponentReplacer().addReplacements(replacements).replace(component);
    }

    public static List<Component> parsePlaceholders(@NotNull List<Component> components, String... replacements) {
        return components.stream().map(component -> parsePlaceholders(component, replacements)).toList();
    }

    public static List<Component> parsePlaceholders(@NotNull List<Component> components, @NotNull Map<String, Component> replacements) {
        return components.stream().map(component -> parsePlaceholders(component, replacements)).toList();
    }

    public static void broadcastMessage(Component msg) { Bukkit.broadcast(msg); }

    public static void sendActionBar(@NotNull Component msg, @NotNull Audience audience) { audience.sendActionBar(msg); }

    public static Component getMainHandHoverItem(@NotNull Audience audience) {
        if (!(audience instanceof Player player)) {
            return Component.text("[None]").color(NamedTextColor.WHITE);
        }
        return getHoverItem(player.getInventory().getItemInMainHand());
    }

    public static Component getOffHandHoverItem(@NotNull Audience audience) {
        if (!(audience instanceof Player player)) {
            return Component.text("[None]").color(NamedTextColor.WHITE);
        }
        return getHoverItem(player.getInventory().getItemInOffHand());
    }

    public static Component getHoverItem(ItemStack item) {
        if (item == null) {
            return Component.text("[None]").color(NamedTextColor.WHITE);
        }
        Component quantity = deserializeString(item.getAmount() + "x ");
        Component hover = quantity.append(item.displayName());
        hover = hover.hoverEvent(item);
        return hover;
    }

    public static boolean matchesString(@NotNull Component component, @NotNull String matcher) {
        return toPlainText(component).equals(matcher);
    }

    public static boolean isEmpty(@NotNull Component component) {
        return toPlainText(component).isEmpty();
    }

}