package uk.firedev.daisylib.command;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.PlayerProfileListResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings("UnstableApiUsage")
public class CommandUtils {

    private static final SimpleCommandExceptionType PLAYER_REQUIRED = new SimpleCommandExceptionType(
        new LiteralMessage("Only players can use this command.")
    );
    private static final SimpleCommandExceptionType ONLY_ONE_TARGET = new SimpleCommandExceptionType(
        new LiteralMessage("Multiple targets selected when only one is required.")
    );
    private static final SimpleCommandExceptionType INVALID_TARGET = new SimpleCommandExceptionType(
        new LiteralMessage("Invalid target.")
    );

    public static @NonNull Player requirePlayer(@Nullable CommandSourceStack source) throws CommandSyntaxException {
        if (source == null) {
            throw PLAYER_REQUIRED.create();
        }
        CommandSender sender = source.getSender();
        if (!(sender instanceof Player player)) {
            throw PLAYER_REQUIRED.create();
        }
        return player;
    }

    public static @NonNull Player requirePlayer(@Nullable CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (context == null) {
            throw PLAYER_REQUIRED.create();
        }
        return requirePlayer(context.getSource());
    }

    public static Predicate<CommandSourceStack> playerPredicate(@NonNull Predicate<Player> playerPredicate) {
        return sender -> {
            if (!(sender.getSender() instanceof Player player)) {
                return false;
            }
            return playerPredicate.test(player);
        };
    }

    public static List<OfflinePlayer> parsePlayerProfilesArgument(@NonNull CommandSourceStack stack, @NonNull PlayerProfileListResolver resolver) throws CommandSyntaxException {
        return resolver.resolve(stack).stream()
            .map(PlayerProfile::getId)
            .filter(Objects::nonNull)
            .map(Bukkit::getOfflinePlayer)
            .filter(player -> player.getFirstPlayed() != 0)
            .toList();
    }

    public static OfflinePlayer parsePlayerProfileArgument(@NonNull CommandSourceStack stack, @NonNull PlayerProfileListResolver resolver) throws CommandSyntaxException {
        List<PlayerProfile> profiles = List.copyOf(resolver.resolve(stack));
        if (profiles.size() != 1) {
            throw ONLY_ONE_TARGET.create();
        }
        UUID uuid = profiles.getFirst().getId();
        if (uuid == null) {
            throw INVALID_TARGET.create();
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player.getFirstPlayed() == 0) {
            throw INVALID_TARGET.create();
        }
        return player;
    }

    public static List<Player> parsePlayersArgument(@NonNull CommandSourceStack stack, @NonNull PlayerSelectorArgumentResolver resolver) throws CommandSyntaxException {
        return resolver.resolve(stack);
    }

    public static Player parsePlayerArgument(@NonNull CommandSourceStack stack, @NonNull PlayerSelectorArgumentResolver resolver) throws CommandSyntaxException {
        return resolver.resolve(stack).getFirst();
    }

}
