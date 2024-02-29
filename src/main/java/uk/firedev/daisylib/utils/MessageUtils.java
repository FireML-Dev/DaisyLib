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

public class MessageUtils {

    private FileConfiguration messagesFile;
    private static MessageUtils instance;

    public MessageUtils(FileConfiguration config) {
        messagesFile = config;
        instance = this;
    }

    public MessageUtils(YamlConfiguration config) {
        messagesFile = config;
        instance = this;
    }

    public static MessageUtils getInstance() {
        return instance;
    }

    public void reload(FileConfiguration config) { messagesFile = config; }

    public void reload(YamlConfiguration config) { messagesFile = config; }

    public TextColor colorFromConfig(String path) {
        NamedTextColor color = NamedTextColor.WHITE;
        if (messagesFile.getString(path) != null) {
            color = NamedTextColor.NAMES.valueOrThrow(messagesFile.getString(path).toLowerCase());
        }
        return color;
    }

    public String fromConfig(String path, String... replacements) {
        String message = "<red>Unknown Message</red> <yellow>" + path + "</yellow>";
        if (messagesFile.getString(path) != null) {
            message = StringUtils.parsePlaceholders(messagesFile.getString(path), replacements);
        }
        return message;
    }

    public String fromConfig(String path, Map<String, String> replacements) {
        String message = "<red>Unknown Message</red> <yellow>" + path + "</yellow>";
        if (messagesFile.getString(path) != null) {
            message = StringUtils.parsePlaceholders(messagesFile.getString(path), replacements);
        }
        return message;
    }

    public List<String> fromConfigList(String path, String... replacements) {
        List<String> returnList = new ArrayList<>();
        List<String> stringList = messagesFile.getStringList(path);
        if (!stringList.isEmpty()) {
            stringList.forEach(string -> returnList.add(StringUtils.parsePlaceholders(string, replacements)));
        } else {
            returnList.add("<red>Unknown Message</red> <yellow>" + path + "</yellow>");
        }
        return returnList;
    }

    public List<String> fromConfigList(String path, Map<String, String> replacements) {
        List<String> returnList = new ArrayList<>();
        List<String> stringList = messagesFile.getStringList(path);
        if (!stringList.isEmpty()) {
            stringList.forEach(string -> returnList.add(StringUtils.parsePlaceholders(string, replacements)));
        } else {
            returnList.add("<red>Unknown Message</red> <yellow>" + path + "</yellow>");
        }
        return returnList;
    }

    public String getPrefix() { return fromConfig("messages.prefix"); }

    public String addPrefix(String message) { return getPrefix() + message; }

    public void sendMessage(Audience audience, String string, String... replacements) { audience.sendMessage(ComponentUtils.parseComponent(string, replacements)); }

    public void sendMessage(Audience audience, String string, Map<String, Component> replacements) { audience.sendMessage(ComponentUtils.parseComponent(string, replacements)); }

    public void sendMessageFromConfig(Audience audience, String key, String... replacements) { audience.sendMessage(ComponentUtils.parseComponent(fromConfig(key, replacements))); }

    public void sendMessageFromConfig(Audience audience, String key, Map<String, String> replacements) { audience.sendMessage(ComponentUtils.parseComponent(fromConfig(key, replacements))); }

    public void sendPrefixedMessage(Audience audience, String string) { audience.sendMessage(ComponentUtils.parseComponent(addPrefix(string))); }

    public void sendPrefixedMessageFromConfig(Audience audience, String key, String... replacements) { audience.sendMessage(ComponentUtils.parseComponent(addPrefix(fromConfig(key, replacements)))); }

    public void sendPrefixedMessageFromConfig(Audience audience, String key, Map<String, String> replacements) { audience.sendMessage(ComponentUtils.parseComponent(addPrefix(fromConfig(key, replacements)))); }

}
