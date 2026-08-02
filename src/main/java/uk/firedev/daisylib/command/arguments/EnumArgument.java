package uk.firedev.daisylib.command.arguments;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class EnumArgument<E extends Enum<E>> implements CustomArgumentType.Converted<E, String> {

    private static final DynamicCommandExceptionType INVALID_INPUT = new DynamicCommandExceptionType(name ->
        new LiteralMessage("Invalid Input: " + name)
    );

    private final Class<E> theEnum;

    private EnumArgument(Class<E> theEnum) {
        this.theEnum = theEnum;
    }

    public static <E extends Enum<E>> EnumArgument<E> enumArgument(@NonNull Class<E> theEnum) {
        return new EnumArgument<>(theEnum);
    }

    public List<String> getSuggestions() {
        E[] constants = theEnum.getEnumConstants();
        if (constants == null) {
            return List.of();
        }
        return Arrays.stream(constants)
            .map(o -> o.toString().toLowerCase())
            .toList();
    }
    
    @NonNull
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(@NonNull CommandContext<S> context, @NonNull SuggestionsBuilder builder) {
        String remaining = builder.getRemainingLowerCase();
        getSuggestions().stream()
            .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(remaining))
            .forEach(builder::suggest);
        return builder.buildFuture();
    }

    @Override
    public @NonNull E convert(@NonNull String nativeType) throws CommandSyntaxException {
        try {
            return Enum.valueOf(theEnum, nativeType.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw INVALID_INPUT.create(nativeType);
        }
    }

    @NonNull
    @Override
    public ArgumentType<String> getNativeType() {
        return StringArgumentType.string();
    }

}
