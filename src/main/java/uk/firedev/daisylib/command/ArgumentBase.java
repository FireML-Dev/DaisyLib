package uk.firedev.daisylib.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public interface ArgumentBase<T, N> extends CustomArgumentType.Converted<T, N> {

    List<String> getSuggestions(@NotNull CommandContext<CommandSourceStack> context);

    @SuppressWarnings("unchecked")
    default List<String> getSuggestionsRaw(@NotNull CommandContext<?> context) {
        try {
            return getSuggestions((CommandContext<CommandSourceStack>) context);
        } catch (ClassCastException exception) {
            System.out.println("Exception go brr.");
            return List.of();
        }
    }

    /**
     * Provides a list of suggestions to show to the client.
     *
     * @param context command context
     * @param builder suggestion builder
     * @return suggestions
     */
    @NotNull
    @Override
    default <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        getSuggestionsRaw(context).stream()
            .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(builder.getRemainingLowerCase()))
            .forEach(builder::suggest);
        return builder.buildFuture();
    }

}
