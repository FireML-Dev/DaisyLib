package uk.firedev.daisylib.command;

import net.kyori.adventure.audience.Audience;
import org.jspecify.annotations.NonNull;
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

    private HelpMessage(@NonNull String commandName, @NonNull HashMap<String, Supplier<ComponentSingleMessage>> usages, @NonNull Supplier<ComponentSingleMessage> header, @NonNull Supplier<ComponentSingleMessage> help) {
        this.commandName = commandName;
        this.usages = usages;
        this.header = header;
        this.help = help;
    }

    /**
     * Creates a HelpMessageBuilder instance
     */
    public static HelpMessage helpMessage(@NonNull String commandName, @NonNull Supplier<ComponentSingleMessage> header, @NonNull Supplier<ComponentSingleMessage> help) {
        return new HelpMessage(commandName, new HashMap<>(), header, help);
    }

    /**
     * Adds a usage to this builder
     */
    public HelpMessage addUsage(@NonNull String name, @NonNull Supplier<ComponentSingleMessage> helpMessage) {
        this.usages.putIfAbsent(name, helpMessage);
        return this;
    }

    /**
     * Adds multiple usages to this builder
     */
    public HelpMessage addUsages(@NonNull Map<String, Supplier<ComponentSingleMessage>> usages) {
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
    private String constructCommand(@NonNull String key) {
        return "/" + commandName + " " + key;
    }

    /**
     * Sends the final message to the provided audience.
     * @param audience The audience to send the message to.
     */
    public void sendMessage(@NonNull Audience audience) {
        build().send(audience);
    }


}
