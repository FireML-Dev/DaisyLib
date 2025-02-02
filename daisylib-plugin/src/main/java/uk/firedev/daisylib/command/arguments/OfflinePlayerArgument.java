package uk.firedev.daisylib.command.arguments;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.utils.PlayerHelper;
import uk.firedev.daisylib.command.ArgumentBuilder;

public class OfflinePlayerArgument {

    public static Argument<OfflinePlayer> create(@NotNull String nodeName) {
        return new CustomArgument<>(
            new StringArgument(nodeName), info -> Bukkit.getOfflinePlayer(info.input())
        ).includeSuggestions(
            ArgumentBuilder.getAsyncSuggestions(info ->
                Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new)
            )
        );
    }

    public static Argument<OfflinePlayer> createPlayedBefore(@NotNull String nodeName) {
        return new CustomArgument<>(new StringArgument(nodeName), info -> {
            OfflinePlayer player = PlayerHelper.getOfflinePlayer(info.input());
            if (player == null) {
                throw CustomArgument.CustomArgumentException.fromMessageBuilder(
                    new CustomArgument.MessageBuilder("Player has never joined the server: ").appendArgInput()
                );
            }
            return player;
        }).includeSuggestions(
            ArgumentBuilder.getAsyncSuggestions(info ->
                Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new)
            )
        );
    }

}
