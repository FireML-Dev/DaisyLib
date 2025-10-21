package uk.firedev.daisylib.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ArgumentBase<T, N> implements CustomArgumentType.Converted<T, N> {

    public abstract List<String> getSuggestions();

    @NotNull
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        getSuggestions().forEach(string -> {
            if (string.startsWith(builder.getRemaining())) {
                builder.suggest(string);
            }
        });
        return builder.buildFuture();
    }

}
