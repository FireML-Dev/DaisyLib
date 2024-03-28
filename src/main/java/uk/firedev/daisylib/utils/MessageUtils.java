package uk.firedev.daisylib.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;

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

    default Component addPrefix(Component message) { return ComponentUtils.parseComponent(getPrefix()).append(message); }

    default void sendMessage(Audience audience, String string, String... replacements) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parseComponent(string, replacements));
        }
    }

    default void sendMessage(Audience audience, Component component, Map<String, Component> replacements) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parsePlaceholders(component, replacements));
        }
    }

    default void sendMessage(Audience audience, Component component, String... replacements) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parsePlaceholders(component, replacements));
        }
    }

    default void sendMessage(Audience audience, String string, Map<String, Component> replacements) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parseComponent(string, replacements));
        }
    }

    default void sendMessageFromConfig(Audience audience, String key, String... replacements) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parseComponent(fromConfig(key, replacements)));
        }
    }

    default void sendMessageFromConfig(Audience audience, String key, Map<String, String> replacements) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parseComponent(fromConfig(key, replacements)));
        }
    }

    default void sendPrefixedMessage(Audience audience, String string) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parseComponent(addPrefix(string)));
        }
    }

    default void sendPrefixedMessage(Audience audience, Component component) {
        if (audience != null) {
            audience.sendMessage(addPrefix(component));
        }
    }

    default void sendPrefixedMessageFromConfig(Audience audience, String key, String... replacements) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parseComponent(addPrefix(fromConfig(key, replacements))));
        }
    }

    default void sendPrefixedMessageFromConfig(Audience audience, String key, Map<String, String> replacements) {
        if (audience != null) {
            audience.sendMessage(ComponentUtils.parseComponent(addPrefix(fromConfig(key, replacements))));
        }
    }

}
