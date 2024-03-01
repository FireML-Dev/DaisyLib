package uk.firedev.daisylib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import net.kyori.adventure.audience.Audience;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MessageUtils {

    FileConfiguration getConfig();

    default String fromConfig(String path, String... replacements) {
        String message = "<red>Unknown Message</red> <yellow>" + path + "</yellow>";
        if (getConfig().getString(path) != null) {
            message = StringUtils.parsePlaceholders(getConfig().getString(path), replacements);
        }
        return message;
    }

    default String fromConfig(String path, Map<String, String> replacements) {
        String message = "<red>Unknown Message</red> <yellow>" + path + "</yellow>";
        if (getConfig().getString(path) != null) {
            message = StringUtils.parsePlaceholders(getConfig().getString(path), replacements);
        }
        return message;
    }

    default List<String> fromConfigList(String path, String... replacements) {
        List<String> returnList = new ArrayList<>();
        List<String> stringList = getConfig().getStringList(path);
        if (!stringList.isEmpty()) {
            stringList.forEach(string -> returnList.add(StringUtils.parsePlaceholders(string, replacements)));
        } else {
            returnList.add("<red>Unknown Message</red> <yellow>" + path + "</yellow>");
        }
        return returnList;
    }

    default List<String> fromConfigList(String path, Map<String, String> replacements) {
        List<String> returnList = new ArrayList<>();
        List<String> stringList = getConfig().getStringList(path);
        if (!stringList.isEmpty()) {
            stringList.forEach(string -> returnList.add(StringUtils.parsePlaceholders(string, replacements)));
        } else {
            returnList.add("<red>Unknown Message</red> <yellow>" + path + "</yellow>");
        }
        return returnList;
    }

    default String getPrefix() { return fromConfig("messages.prefix"); }

    default String addPrefix(String message) { return getPrefix() + message; }

    default void sendMessage(Audience audience, String string, String... replacements) { audience.sendMessage(ComponentUtils.parseComponent(string, replacements)); }

    default void sendMessage(Audience audience, String string, Map<String, Component> replacements) { audience.sendMessage(ComponentUtils.parseComponent(string, replacements)); }

    default void sendMessageFromConfig(Audience audience, String key, String... replacements) { audience.sendMessage(ComponentUtils.parseComponent(fromConfig(key, replacements))); }

    default void sendMessageFromConfig(Audience audience, String key, Map<String, String> replacements) { audience.sendMessage(ComponentUtils.parseComponent(fromConfig(key, replacements))); }

    default void sendPrefixedMessage(Audience audience, String string) { audience.sendMessage(ComponentUtils.parseComponent(addPrefix(string))); }

    default void sendPrefixedMessageFromConfig(Audience audience, String key, String... replacements) { audience.sendMessage(ComponentUtils.parseComponent(addPrefix(fromConfig(key, replacements)))); }

    default void sendPrefixedMessageFromConfig(Audience audience, String key, Map<String, String> replacements) { audience.sendMessage(ComponentUtils.parseComponent(addPrefix(fromConfig(key, replacements)))); }

}
