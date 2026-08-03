package uk.firedev.daisylib.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class HelpMessage {

    private static final EntryFormatter DEFAULT_FORMATTER = new EntryFormatter(
        Component.text("{command} - {description}"),
        "{command}",
        "{description}"
    );

    private final String commandName;
    private final String subcommand;
    private final List<HelpMessageEntry> entries = new ArrayList<>();

    private @Nullable Component header;
    private @Nullable Component footer;
    private @NonNull EntryFormatter formatter = DEFAULT_FORMATTER;

    private Predicate<CommandSender> defaultRequirement = sender -> true;

    private HelpMessage(@NonNull String commandName, @NonNull String subcommand) {
        this.commandName = commandName;
        this.subcommand = subcommand;
    }

    /**
     * Creates a HelpMessageBuilder instance
     */
    public static HelpMessage helpMessage(@NonNull String commandName) {
        return new HelpMessage(commandName, "");
    }

    public static HelpMessage helpMessage(@NonNull String commandName, @NonNull String subcommand) {
        return new HelpMessage(commandName, subcommand);
    }

    /**
     * Adds a usage to this builder
     */
    public HelpMessage addEntry(@NonNull String name, @NonNull Supplier<Component> description, @NonNull Predicate<CommandSender> requirement) {
        this.entries.add(new HelpMessageEntry(name, description, requirement));
        return this;
    }

    /**
     * Adds a usage to this builder
     */
    public HelpMessage addEntry(@NonNull String name, @NonNull Supplier<Component> description, @NonNull String permissionRequirement) {
        Predicate<CommandSender> requirement = sender -> sender.hasPermission(permissionRequirement);
        this.entries.add(new HelpMessageEntry(name, description, requirement));
        return this;
    }

    /**
     * Adds a usage to this builder
     */
    public HelpMessage addEntry(@NonNull String name, @NonNull Supplier<Component> description) {
        this.entries.add(new HelpMessageEntry(name, description, null));
        return this;
    }

    public HelpMessage setDefaultRequirement(@NonNull Predicate<CommandSender> requirement) {
        this.defaultRequirement = requirement;
        return this;
    }

    public HelpMessage setDefaultRequirement(@NonNull String permissionRequirement) {
        this.defaultRequirement = sender -> sender.hasPermission(permissionRequirement);
        return this;
    }

    public HelpMessage setHeader(@NonNull Component header) {
        this.header = header;
        return this;
    }

    public HelpMessage setFooter(@NonNull Component footer) {
        this.footer = footer;
        return this;
    }

    public HelpMessage setEntryFormat(@NonNull Component format, @NonNull String commandVariable, @NonNull String descriptionVariable) {
        this.formatter = new EntryFormatter(format, commandVariable, descriptionVariable);
        return this;
    }

    /**
     * Adds "/[commandname] " and the relevant subcommand name to the start of the provided entry.
     */
    private String correctCommand(@NonNull String name) {
        StringBuilder builder = new StringBuilder("/");
        builder.append(commandName).append(" ");
        if (!subcommand.isEmpty()) {
            builder.append(subcommand).append(" ");
        }
        builder.append(name);
        return builder.toString();
    }

    public void send(@NonNull CommandSender sender) {
        // Header
        if (header != null) {
            sender.sendMessage(header);
        }
        // All help entries
        entries.forEach(entry -> {
            Predicate<CommandSender> predicate = entry.requirement() == null ? defaultRequirement : entry.requirement();
            if (!predicate.test(sender)) {
                return;
            }
            Component description = entry.description().get();
            if (description == null || MessageUtils.isEmpty(description)) {
                return;
            }
            Component formatted = formatter.format()
                .replaceText(builder -> builder.matchLiteral(formatter.commandVariable()).replacement(entry.name()))
                .replaceText(builder -> builder.matchLiteral(formatter.descriptionVariable()).replacement(description));
            sender.sendMessage(formatted);
        });
        // Footer
        if (footer != null) {
            sender.sendMessage(footer);
        }
    }

    record HelpMessageEntry(
        @NonNull String name,
        @NonNull Supplier<Component> description,
        @Nullable Predicate<CommandSender> requirement
    ) {}

    record EntryFormatter(
        @NonNull Component format,
        @NonNull String commandVariable,
        @NonNull String descriptionVariable
    ) {}

}
