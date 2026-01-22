package uk.firedev.daisylib.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.List;

public class EnumArgument<E extends Enum<E>> implements ArgumentBase<E, String> {

    private static final DynamicCommandExceptionType INVALID_INPUT = new DynamicCommandExceptionType(name ->
        MessageComponentSerializer.message().serialize(Component.text("Invalid Input: " + name))
    );

    private final Class<E> theEnum;

    private EnumArgument(Class<E> theEnum) {
        this.theEnum = theEnum;
    }

    public static <E extends Enum<E>> EnumArgument<E> create(@NonNull Class<E> theEnum) {
        return new EnumArgument<>(theEnum);
    }

    @Override
    public List<String> getSuggestions(@NonNull CommandContext<CommandSourceStack> commandContext) {
        E[] constants = theEnum.getEnumConstants();
        if (constants == null) {
            return List.of();
        }
        return Arrays.stream(constants)
            .map(o -> o.toString().toLowerCase())
            .toList();
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
    public E convert(String nativeType) throws CommandSyntaxException {
        try {
            return Enum.valueOf(theEnum, nativeType.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw INVALID_INPUT.create(nativeType);
        }
    }

    /**
     * Gets the native type that this argument uses,
     * the type that is sent to the client.
     *
     * @return native argument type
     */
    @NonNull
    @Override
    public ArgumentType<String> getNativeType() {
        return StringArgumentType.string();
    }

}
