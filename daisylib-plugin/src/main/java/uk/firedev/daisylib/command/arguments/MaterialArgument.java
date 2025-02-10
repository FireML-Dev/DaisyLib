package uk.firedev.daisylib.command.arguments;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.api.utils.ObjectUtils;

import java.util.Arrays;

public class MaterialArgument {

    public static Argument<Material> create(@NotNull String nodeName) {
        return new CustomArgument<>(
            new StringArgument(nodeName), info -> {
                Material material = ObjectUtils.getEnumValue(Material.class, info.input());
                if (material == null) {
                    throw CustomArgument.CustomArgumentException.fromMessageBuilder(
                        new CustomArgument.MessageBuilder("Invalid Material: ").appendArgInput()
                    );
                }
                return material;
            }
        ).includeSuggestions(
            ArgumentSuggestions.strings(
                Arrays.stream(Material.values()).map(material -> material.toString().toLowerCase()).toArray(String[]::new)
            )
        );
    }

}
