package uk.firedev.daisylib.command.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.command.ArgumentBase;

import java.util.List;

public class BooleanArgument implements ArgumentBase<Boolean, String> {

    private static final DynamicCommandExceptionType INVALID_BOOLEAN = new DynamicCommandExceptionType(name ->
        MessageComponentSerializer.message().serialize(Component.text("Invalid boolean: " + name))
    );

    @Override
    public List<String> getSuggestions(@NotNull CommandContext<CommandSourceStack> context) {
        return List.of(
            "true",
            "false"
        );
    }

    /**
     * Converts the value from the native type to the custom argument type.
     *
     * @param nativeType native argument provided value
     * @return converted value
     * @throws CommandSyntaxException if an exception occurs while parsing
     * @see #convert(Object, Object)
     */
    @Override
    @NotNull
    public Boolean convert(String nativeType) throws CommandSyntaxException {
        Boolean bool = switch (nativeType.toLowerCase()) {
            case "true" -> true;
            case "false" -> false;
            default -> null;
        };
        if (bool == null) {
            throw INVALID_BOOLEAN.create(nativeType);
        }
        return bool;
    }

    /**
     * Gets the native type that this argument uses,
     * the type that is sent to the client.
     *
     * @return native argument type
     */
    @NotNull
    @Override
    public ArgumentType<String> getNativeType() {
        return StringArgumentType.string();
    }

}
