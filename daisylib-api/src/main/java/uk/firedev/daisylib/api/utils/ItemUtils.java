package uk.firedev.daisylib.api.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.addons.item.ItemAddon;
import uk.firedev.daisylib.api.message.component.ComponentMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemUtils {

    public static @Nullable ItemStack getItem(@Nullable String itemName) {
        ItemType itemType = getItemType(itemName);
        if (itemType != null) {
            return itemType.createItemStack();
        }
        return ItemAddon.processString(itemName);
    }

    public static @NotNull ItemStack getItem(@Nullable String itemName, @NotNull ItemStack defaultItem) {
        ItemStack item = getItem(itemName);
        return item == null ? defaultItem : item;
    }

    public static @Nullable ItemType getItemType(@Nullable String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            return null;
        }
        Key key = NamespacedKey.fromString(itemName);
        if (key == null) {
            return null;
        }
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM).get(key);
    }

    public static @NotNull ItemType getItemType(@Nullable String itemName, @NotNull ItemType defaultType) {
        ItemType type = getItemType(itemName);
        return type == null ? defaultType : type;
    }

    public static boolean isValidItemType(@Nullable String itemName) {
        return getItemType(itemName) != null;
    }

    /**
     * @deprecated Use {@link #getItemType(String)} or {@link BlockUtils#getBlockType(String)} instead.
     */
    // Suppress deprecation warning as this is still used where alternative API does not exist just yet.
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public static @Nullable Material getMaterial(@Nullable String materialName) {
        if (materialName == null || materialName.isEmpty()) {
            return null;
        }
        return ObjectUtils.getEnumValue(Material.class, materialName);
    }

    /**
     * @deprecated Use {@link #getItemType(String, ItemType)} or {@link BlockUtils#getBlockType(String, org.bukkit.block.BlockType)} instead.
     */
    // Suppress deprecation warning as this is still used where alternative API does not exist just yet.
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public static @NotNull Material getMaterial(@Nullable String materialName, @NotNull Material defaultMaterial) {
        Material material = getMaterial(materialName);
        return material == null ? defaultMaterial : material;
    }

    /**
     * @deprecated Use {@link #isValidItemType(String)} or {@link BlockUtils#isValidBlockType(String)} instead.
     */
    @Deprecated
    public static boolean isValidMaterial(@Nullable String materialName) {
        return getMaterial(materialName) != null;
    }

    public static ItemStack setGlowing(@NotNull ItemStack item, boolean glowing) {
        item.editMeta(meta -> meta.setEnchantmentGlintOverride(glowing));
        return item;
    }

    public static ItemStack getHead(@NotNull UUID uuid) {
        ItemStack item = ItemStack.of(Material.PLAYER_HEAD);
        item.editMeta(SkullMeta.class, meta -> {
            PlayerProfile profile = Bukkit.createProfile(uuid, "Unknown");
            meta.setPlayerProfile(profile);
        });
        return hideAllFlags(item);
    }

    public static ItemStack getHead(@NotNull UUID uuid, @Nullable String displayName) {
        ItemStack item = getHead(uuid);
        if (displayName != null && !displayName.isEmpty()) {
            item.editMeta(SkullMeta.class, meta -> meta.displayName(ComponentMessage.fromString(displayName).getMessage()));
        }
        return hideAllFlags(item);
    }

    public static ItemStack getHead(@NotNull String textures) {
        ItemStack item = ItemStack.of(Material.PLAYER_HEAD);
        item.editMeta(SkullMeta.class, meta -> {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), "Unknown");
            profile.setProperty(new ProfileProperty("textures", textures));
            meta.setPlayerProfile(profile);
        });
        return hideAllFlags(item);
    }

    public static ItemStack setHeadSkin(@NotNull ItemStack item, @NotNull UUID uuid) {
        item.editMeta(SkullMeta.class, meta -> {
            PlayerProfile profile = Bukkit.createProfile(uuid, "Unknown");
            meta.setPlayerProfile(profile);
        });
        return hideAllFlags(item);
    }

    public static void setSkullProfile(@NotNull SkullMeta skull, @NotNull UUID uuid, @NotNull String name, String... properties) {
        PlayerProfile profile = Bukkit.createProfile(uuid, name);
        for (int i = 0; i + 1 < properties.length; i += 2) {
            profile.setProperty(new ProfileProperty(properties[i], properties[i+1]));
        }
        skull.setPlayerProfile(profile);
    }

    public static void markOwned(@NotNull Item item, @NotNull UUID owner) {
        item.setPickupDelay(0);
        item.setOwner(owner);
        item.setCanMobPickup(false);
    }

    public static ItemStack hideAllFlags(@NotNull ItemStack item) {
        item.editMeta(meta -> meta.addItemFlags(ItemFlag.values()));
        return item;
    }

    public static void giveItems(@Nullable ItemStack @NotNull [] items, @NotNull Player player) {
        if (items.length == 0) {
            return;
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.5f);
        ItemStack[] filteredItems = Arrays.stream(items).filter(Objects::nonNull).toArray(ItemStack[]::new);
        player.give(filteredItems);
    }

    public static void giveItems(@NotNull List<ItemStack> items, @NotNull Player player) {
        giveItems(items.toArray(ItemStack[]::new), player);
    }

    public static void giveItem(@NotNull ItemStack item, @NotNull Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.5f);
        player.getInventory().addItem(item)
                .values()
                .forEach(remaining -> player.getWorld().dropItem(player.getLocation(), remaining));
    }

    public static @Nullable Enchantment getEnchantment(@Nullable String enchantmentName) {
        if (enchantmentName == null || enchantmentName.isEmpty()) {
            return null;
        }
        NamespacedKey key = NamespacedKey.fromString(enchantmentName);
        if (key == null) {
            return null;
        }
        Registry<@NotNull Enchantment> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);
        return registry.get(key);
    }

}
