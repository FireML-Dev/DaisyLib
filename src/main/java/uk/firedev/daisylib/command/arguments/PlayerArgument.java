package uk.firedev.daisylib.command.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.command.ArgumentBase;

import java.util.List;
import java.util.function.Predicate;

public class PlayerArgument implements ArgumentBase<Player, String> {

    private static final DynamicCommandExceptionType NOT_ONLINE = new DynamicCommandExceptionType(name ->
        MessageComponentSerializer.message().serialize(Component.text("Player is not online: " + name))
    );
    private static final DynamicCommandExceptionType CANNOT_BE_USED = new DynamicCommandExceptionType(name ->
        MessageComponentSerializer.message().serialize(Component.text("This player cannot be used: " + name))
    );

    private final Predicate<Player> filter;

    private PlayerArgument(@NotNull Predicate<Player> filter) {
        this.filter = filter;
    }

    public static PlayerArgument create() {
        return new PlayerArgument(player -> true);
    }

    public static PlayerArgument create(@NotNull Predicate<Player> filter) {
        return new PlayerArgument(filter);
    }

    @Override
    public List<String> getSuggestions(@NotNull CommandContext<CommandSourceStack> context) {
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
    public Player convert(String nativeType) throws CommandSyntaxException {
        Player player = Bukkit.getPlayer(nativeType);
        if (player == null) {
            throw NOT_ONLINE.create(nativeType);
        }
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
