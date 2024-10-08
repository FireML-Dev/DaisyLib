package uk.firedev.daisylib.command;

import dev.jorel.commandapi.SuggestionInfo;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ArgumentBuilder {

    public static Argument<String> getAsyncStringsArgument(@NotNull String name, @NotNull Function<SuggestionInfo<CommandSender>, String[]> function) {
        return new StringArgument(name).includeSuggestions(
                ArgumentSuggestions.stringsAsync(
                        info -> CompletableFuture.supplyAsync(() -> function.apply(info))
                )
        );
    }

}
