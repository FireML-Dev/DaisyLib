package uk.firedev.daisylib.command;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HelpMessageBuilder {

    private final String commandName;
    private final HashMap<String, Supplier<ComponentSingleMessage>> usages;
    private final Supplier<ComponentMessage> header;
    private final Supplier<ComponentMessage> help;

    private HelpMessageBuilder(@NotNull String commandName, @NotNull HashMap<String, Supplier<ComponentSingleMessage>> usages, @NotNull Supplier<ComponentMessage> header, @NotNull Supplier<ComponentMessage> help) {
        this.commandName = commandName;
        this.usages = usages;
        this.header = header;
        this.help = help;
    }

    /**
     * Creates a HelpMessageBuilder instance
     */
    public static HelpMessageBuilder create(@NotNull String commandName, @NotNull Supplier<ComponentMessage> header, @NotNull Supplier<ComponentMessage> help) {
        return new HelpMessageBuilder(commandName, new HashMap<>(), header, help);
    }

    /**
     * Creates a HelpMessageBuilder instance with the provided usages
     */
    public static HelpMessageBuilder create(@NotNull String commandName, @NotNull HashMap<String, Supplier<ComponentSingleMessage>> usages, @NotNull Supplier<ComponentMessage> header, @NotNull Supplier<ComponentMessage> help) {
        return new HelpMessageBuilder(commandName, usages, header, help);
    }

    /**
     * Adds a usage to this builder
     */
    public HelpMessageBuilder addUsage(@NotNull String name, @NotNull Supplier<ComponentSingleMessage> helpMessage) {
        this.usages.putIfAbsent(name, helpMessage);
        return this;
    }

    /**
     * Adds multiple usages to this builder
     */
    public HelpMessageBuilder addUsages(@NotNull Map<String, Supplier<ComponentSingleMessage>> usages) {
        usages.forEach(this::addUsage);
        return this;
    }

    /**
     * Creates the final message.
     * @return The created help message
     */
    public ComponentMessage buildMessage() {
        final ComponentMessage message = header.get();
        usages.forEach((key, value) -> {
            ComponentMessage usage = help.get();
            usage.replace("command", constructCommand(key));
            usage.replace("description", value.get());
            message.append(Component.newline());
            message.append(usage);
        });
        return message;
    }

    /**
     * Adds "/[commandName] " to the start of the provided usage.
     */
    private String constructCommand(@NotNull String key) {
        return "/" + commandName + " " + key;
    }

    /**
     * Sends the final message to the provided audience.
     * @param audience The audience to send the message to.
     */
    public void sendMessage(@NotNull Audience audience) {
        buildMessage().send(audience);
    }


}
