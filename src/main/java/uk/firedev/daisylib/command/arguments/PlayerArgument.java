package uk.firedev.daisylib.command.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.command.ArgumentBase;
import uk.firedev.daisylib.utils.PlayerHelper;

import java.util.List;
import java.util.function.Predicate;

public class PlayerArgument extends ArgumentBase<Player, String> {

    private static final DynamicCommandExceptionType INVALID_PLAYER = new DynamicCommandExceptionType(val ->
        MessageComponentSerializer.message().serialize(Component.text("This player is not online: " + val))
    );
    private static final DynamicCommandExceptionType CANNOT_USE = new DynamicCommandExceptionType(val ->
        MessageComponentSerializer.message().serialize(Component.text("This player cannot be used: " + val))
    );

    private final Predicate<Player> predicate;

    private PlayerArgument(@NotNull Predicate<Player> predicate) {
        this.predicate = predicate;
    }

    public static PlayerArgument create() {
        return create(Player -> true);
    }

    public static PlayerArgument create(@NotNull Predicate<Player> predicate) {
        return new PlayerArgument(predicate);
    }

    @Override
    public List<String> getSuggestions() {
        return Bukkit.getOnlinePlayers().stream().filter(this.predicate).map(Player::getName).toList();
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
            throw INVALID_PLAYER.create(nativeType);
        }
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
