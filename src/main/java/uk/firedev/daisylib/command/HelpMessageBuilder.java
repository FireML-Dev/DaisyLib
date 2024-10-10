package uk.firedev.daisylib.command;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;

import java.util.Map;
import java.util.Objects;

public class HelpMessageBuilder {

    private final ComponentMessage header;
    private final ComponentMessage usageFormat;

    /**
     * @deprecated This constructor will be made private for 2.1.0-SNAPSHOT. Use {@link #create(ComponentMessage, ComponentMessage)} instead.
     */
    @Deprecated(forRemoval = true)
    public HelpMessageBuilder(@NotNull ComponentMessage header, @NotNull ComponentMessage usageFormat) {
        this.header = header;
        this.usageFormat = usageFormat;
    }

    /**
     * Creates a HelpMessageBuilder instance
     * @param header The header to display.
     * @param usageFormat The format for each usage message.
     */
    public static HelpMessageBuilder create(@NotNull ComponentMessage header, @NotNull ComponentMessage usageFormat) {
        return new HelpMessageBuilder(header, usageFormat);
    }

    /**
     * Creates the final message.
     * @param usages The command's usages.
     * @param commandVariable The variable to use for each command usage. Defaults to "command" if null.
     * @param descriptionVariable The variable to use for each command description. Defaults to "description" if null.
     * @return The created help message
     */
    public ComponentMessage buildMessage(@NotNull Map<String, String> usages, @Nullable String commandVariable, @Nullable String descriptionVariable) {
        final ComponentMessage finalMessage = this.header.duplicate();
        final String finalCommandVariable = Objects.requireNonNullElse(commandVariable, "command");
        final String finalDescriptionVariable = Objects.requireNonNullElse(descriptionVariable, "description");
        usages.forEach((key, value) -> {
            ComponentReplacer replacer = ComponentReplacer.componentReplacer(
                    finalCommandVariable, key,
                    finalDescriptionVariable, value
            );
            finalMessage.append(Component.newline()).append(this.usageFormat.duplicate().applyReplacer(replacer));
        });
        return finalMessage;
    }

    /**
     * Sends the final message to the provided audience.
     * @param audience The audience to send the message to.
     * @param usages The command's usages.
     * @param commandVariable The variable to use for each command usage.
     * @param descriptionVariable The variable to use for each command description.
     */
    public void sendMessage(@NotNull Audience audience, @NotNull Map<String, String> usages, @Nullable String commandVariable, @Nullable String descriptionVariable) {
        buildMessage(usages, commandVariable, descriptionVariable).sendMessage(audience);
    }


}
