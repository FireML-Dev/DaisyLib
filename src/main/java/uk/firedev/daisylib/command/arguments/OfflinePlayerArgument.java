package uk.firedev.daisylib.command.arguments;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.utils.PlayerHelper;
import uk.firedev.daisylib.command.ArgumentBuilder;

import java.util.function.Predicate;

public class OfflinePlayerArgument {

    public static Argument<OfflinePlayer> create(@NotNull String nodeName) {
        return create(nodeName, offlinePlayer -> true);
    }

    public static Argument<OfflinePlayer> create(@NotNull String nodeName, @NotNull Predicate<OfflinePlayer> predicate) {
        return new CustomArgument<>(new StringArgument(nodeName), info -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(info.input());
            if (!predicate.test(player)) {
                throw CustomArgument.CustomArgumentException.fromMessageBuilder(
                    new CustomArgument.MessageBuilder("This player cannot be used: ").appendArgInput()
                );
            }
            return player;
        }).includeSuggestions(
            ArgumentBuilder.getAsyncSuggestions(info ->
                Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new)
            )
        );
    }

    public static Argument<OfflinePlayer> createPlayedBefore(@NotNull String nodeName) {
        return create(nodeName, PlayerHelper::hasPlayerBeenOnServer);
    }

}
