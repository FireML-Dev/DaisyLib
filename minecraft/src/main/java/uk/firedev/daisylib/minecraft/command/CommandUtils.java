package uk.firedev.daisylib.minecraft.command;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

@SuppressWarnings("UnstableApiUsage")
public class CommandUtils {

    private static final SimpleCommandExceptionType PLAYER_REQUIRED = new SimpleCommandExceptionType(
        new LiteralMessage("Only players can use this command.")
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

}
