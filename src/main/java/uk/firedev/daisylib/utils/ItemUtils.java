package uk.firedev.daisylib.utils;

import com.denizenscript.denizen.scripts.containers.core.ItemScriptHelper;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.List;
import java.util.UUID;

public class ItemUtils {

    public static @Nullable Material getMaterial(@NotNull String materialName, Material defaultMaterial) {
        try {
            return Material.valueOf(materialName.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return defaultMaterial;
        }
    }

    public static boolean validMaterial(String materialName) {
        try {
            Material.valueOf(materialName.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public static ItemStack setGlowing(ItemStack item, boolean glowing) {
        if (item == null) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (glowing) {
            meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.DAMAGE_ALL);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getHead(OfflinePlayer player, String name) {
        return getHead(player.getUniqueId(), name);
    }

    public static ItemStack getHead(UUID uuid) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(uuid, "Unknown");
        skull.setPlayerProfile(profile);
        item.setItemMeta(skull);
        return hideAllFlags(item);
    }

    public static ItemStack getHead(UUID uuid, String name) {
        ItemStack item = getHead(uuid);
        if (name != null && !name.equals("none")) {
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.displayName(ComponentUtils.parseComponent(name));
            item.setItemMeta(skull);
        }
        return hideAllFlags(item);
    }

    public static ItemStack getHead(String textures) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), "Unknown");
        profile.setProperty(new ProfileProperty("textures", textures));
        skull.setPlayerProfile(profile);
        item.setItemMeta(skull);
        return hideAllFlags(item);
    }

    public static ItemStack setHeadSkin(ItemStack item, UUID uuid) {
        if (!item.getType().equals(Material.PLAYER_HEAD)) {
            return item;
        }
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(uuid, "Unknown");
        skull.setPlayerProfile(profile);
        item.setItemMeta(skull);
        return hideAllFlags(item);
    }

    public static void setSkullProfile(SkullMeta skull, UUID uuid, String name, String... properties) {
        PlayerProfile profile = Bukkit.createProfile(uuid, name);
        for (int i = 0; i + 1 < properties.length; i += 2) {
            profile.setProperty(new ProfileProperty(properties[i], properties[i+1]));
        }
        skull.setPlayerProfile(profile);
    }

    public static void markOwned(Item item, Player player) {
        item.setPickupDelay(0);
        item.setOwner(player.getUniqueId());
        item.setCanMobPickup(false);
    }

    public static ItemStack hideAllFlags(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack toIcon(Material material) {
        return material != null ? hideAllFlags(new ItemStack(material)) : null;
    }

    public static boolean isCustomItem(ItemStack item) {
        return DaisyLib.getInstance().denizenEnabled && ItemScriptHelper.isItemscript(item);
    }

    public static void giveItems(ItemStack[] items, Player player) {
        if (items.length == 0) {
            return;
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.5f);
        player.getInventory().addItem(items)
                .values()
                .forEach(item -> player.getWorld().dropItem(player.getLocation(), item));
    }

    public static void giveItems(List<ItemStack> items, Player player) {
        if (items.isEmpty()) {
            return;
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.5f);
        player.getInventory().addItem(items.toArray(ItemStack[]::new))
                .values()
                .forEach(item -> player.getWorld().dropItem(player.getLocation(), item));
    }

    public static void giveItem(ItemStack item, Player player) {
        if (item == null) {
            return;
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.5f, 1.5f);
        player.getInventory().addItem(item)
                .values()
                .forEach(remaining -> player.getWorld().dropItem(player.getLocation(), remaining));
    }

}
