package uk.firedev.daisylib.command.arguments;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.command.ArgumentBuilder;

import java.util.function.Predicate;

public class PlayerArgument {

    public static Argument<Player> create(@NotNull String nodeName) {
        return create(nodeName, player -> true);
    }

    public static Argument<Player> create(@NotNull String nodeName, @NotNull Predicate<Player> predicate) {
        return new CustomArgument<>(
            new StringArgument(nodeName), info -> {
            Player player = Bukkit.getPlayer(info.input());
            if (player == null) {
                throw CustomArgument.CustomArgumentException.fromMessageBuilder(
                    new CustomArgument.MessageBuilder("Player is not online: ").appendArgInput()
                );
            }
            if (!predicate.test(player)) {
                throw CustomArgument.CustomArgumentException.fromMessageBuilder(
                    new CustomArgument.MessageBuilder("This player cannot be used: ").appendArgInput()
                );
            }
            return player;
        }
        ).includeSuggestions(
            ArgumentBuilder.getAsyncSuggestions(info ->
                Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new)
            )
        );
    }

}
