package uk.firedev.daisylib.command.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.command.ArgumentBase;
import uk.firedev.daisylib.utils.PlayerHelper;
import uk.firedev.daisylib.command.ArgumentBuilder;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OfflinePlayerArgument extends ArgumentBase<OfflinePlayer, String> {

    private static final DynamicCommandExceptionType CANNOT_USE = new DynamicCommandExceptionType(val ->
        MessageComponentSerializer.message().serialize(Component.text("This player cannot be used: " + val))
    );

    private final Predicate<OfflinePlayer> predicate;

    private OfflinePlayerArgument(@NotNull Predicate<OfflinePlayer> predicate) {
        this.predicate = predicate;
    }

    public static OfflinePlayerArgument create() {
        return create(offlinePlayer -> true);
    }

    public static OfflinePlayerArgument create(@NotNull Predicate<OfflinePlayer> predicate) {
        return new OfflinePlayerArgument(predicate);
    }

    public static OfflinePlayerArgument createPlayedBefore() {
        return create(PlayerHelper::hasPlayerBeenOnServer);
    }

    @Override
    public List<String> getSuggestions() {
        return Bukkit.getOnlinePlayers().stream()
            .filter(this.predicate)
            .map(Player::getName)
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
    public OfflinePlayer convert(String nativeType) throws CommandSyntaxException {
        OfflinePlayer player = Bukkit.getOfflinePlayer(nativeType);
        if (!predicate.test(player)) {
            throw CANNOT_USE.create(nativeType);
        }
        return player;
    }

    /**
     * Gets the native type that this argument uses,
     * the type that is sent to the client.
     *
     * @return native argument type
     */
    @NotNull
    @Override
    public ArgumentType<String> getNativeType() {
        return StringArgumentType.string();
    }

}
