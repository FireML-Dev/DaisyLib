package uk.firedev.daisylib.command;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import uk.firedev.messagelib.message.ComponentListMessage;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HelpMessage {

    private final String commandName;
    private final HashMap<String, Supplier<ComponentSingleMessage>> usages;
    private final Supplier<ComponentSingleMessage> header;
    private final Supplier<ComponentSingleMessage> help;

    private HelpMessage(@NotNull String commandName, @NotNull HashMap<String, Supplier<ComponentSingleMessage>> usages, @NotNull Supplier<ComponentSingleMessage> header, @NotNull Supplier<ComponentSingleMessage> help) {
        this.commandName = commandName;
        this.usages = usages;
        this.header = header;
        this.help = help;
    }

    /**
     * Creates a HelpMessageBuilder instance
     */
    public static HelpMessage helpMessage(@NotNull String commandName, @NotNull Supplier<ComponentSingleMessage> header, @NotNull Supplier<ComponentSingleMessage> help) {
        return new HelpMessage(commandName, new HashMap<>(), header, help);
    }

    /**
     * Adds a usage to this builder
     */
    public HelpMessage addUsage(@NotNull String name, @NotNull Supplier<ComponentSingleMessage> helpMessage) {
        this.usages.putIfAbsent(name, helpMessage);
        return this;
    }

    /**
     * Adds multiple usages to this builder
     */
    public HelpMessage addUsages(@NotNull Map<String, Supplier<ComponentSingleMessage>> usages) {
        usages.forEach(this.usages::putIfAbsent);
        return this;
    }

    /**
     * Creates the final message.
     * @return The created help message
     */
    public ComponentListMessage build() {
        List<Object> finalMessage = new ArrayList<>();
        finalMessage.add(header.get());
        usages.forEach((key, value) -> {
            ComponentMessage usage = help.get()
                .replace("{command}", constructCommand(key))
                .replace("{description}", value.get());
            finalMessage.add(usage);
        });
        return ComponentMessage.componentMessage(finalMessage);
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
        build().send(audience);
    }


}
