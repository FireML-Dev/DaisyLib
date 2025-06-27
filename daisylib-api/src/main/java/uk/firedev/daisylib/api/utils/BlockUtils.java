package uk.firedev.daisylib.api.utils;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockUtils {

    public static @Nullable BlockType getBlockType(@Nullable String blockName) {
        if (blockName == null || blockName.isEmpty()) {
            return null;
        }
        Key key = NamespacedKey.fromString(blockName);
        if (key == null) {
            return null;
        }
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.BLOCK).get(key);
    }

    public static @NotNull BlockType getBlockType(@Nullable String blockName, @NotNull BlockType defaultType) {
        BlockType type = getBlockType(blockName);
        return type == null ? defaultType : type;
    }

    public static boolean isValidBlockType(@Nullable String blockName) {
        return getBlockType(blockName) != null;
    }

}
