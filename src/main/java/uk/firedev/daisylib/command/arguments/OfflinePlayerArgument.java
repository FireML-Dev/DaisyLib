package uk.firedev.daisylib.command.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.command.ArgumentBase;

import java.util.List;
import java.util.function.Predicate;

public class OfflinePlayerArgument implements ArgumentBase<OfflinePlayer, String> {

    private static final DynamicCommandExceptionType CANNOT_BE_USED = new DynamicCommandExceptionType(name ->
        MessageComponentSerializer.message().serialize(Component.text("This player cannot be used: " + name))
    );

    private final Predicate<OfflinePlayer> filter;

    private OfflinePlayerArgument(@NotNull Predicate<OfflinePlayer> filter) {
        this.filter = filter;
    }

    public static OfflinePlayerArgument create() {
        return new OfflinePlayerArgument(offlinePlayer -> true);
    }

    public static OfflinePlayerArgument create(@NotNull Predicate<OfflinePlayer> filter) {
        return new OfflinePlayerArgument(filter);
    }

    @Override
    public List<String> getSuggestions(@NotNull CommandContext<?> context) {
        return Bukkit.getOnlinePlayers().stream()
            .filter(filter)
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
        if (!filter.test(player)) {
            throw CANNOT_BE_USED.create(nativeType);
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
