package uk.firedev.daisylib.reward;

import com.denizenscript.denizen.objects.ItemTag;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.VaultManager;
import uk.firedev.daisylib.utils.ItemUtils;
import uk.firedev.daisylib.utils.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Reward {

    private @NotNull String key;
    private @NotNull String value;
    private int quantity;
    private final JavaPlugin plugin;

    public Reward(@NotNull String identifier, JavaPlugin plugin) {
        this.plugin = Objects.requireNonNullElse(plugin, DaisyLib.getInstance());
        String[] split = identifier.split(":");
        try {
            this.key = split[0];
        } catch (IndexOutOfBoundsException ex) {
            Loggers.log(Level.INFO, this.plugin.getLogger(), "Broken reward " + identifier);
            this.key = "";
        }
        try {
            this.value = split[1];
        } catch (IndexOutOfBoundsException ex) {
            this.value = "";
        }
        String[] quantitySplit = identifier.split(";");
        try {
            String quantity = quantitySplit[1];
            this.key = this.key.replace(";" + quantity, "");
            this.value = this.value.replace(";" + quantity, "");
            this.quantity = Math.max(Integer.parseInt(quantity), 1);
        } catch (IndexOutOfBoundsException|NumberFormatException ex) {
            this.quantity = 1;
        }
    }

    public void rewardPlayer(@NotNull Player player) {
        RewardType rewardType = RewardType.getForKey(this.key);
        switch (rewardType) {
            case ITEM -> {
                ItemStack item = null;
                if (key.equals("denizen") && Bukkit.getPluginManager().isPluginEnabled("Denizen")) {
                    ItemTag itemTag = ItemTag.valueOf(value, false);
                    if (itemTag == null) {
                        Loggers.log(Level.INFO, this.plugin.getLogger(), "Could not obtain denizen item " + value);
                        return;
                    }
                    item = itemTag.getItemStack();
                } else {
                    try {
                        Material material = Material.valueOf(key.toUpperCase());
                        item = new ItemStack(material);
                    } catch (IllegalArgumentException ex) {
                        Loggers.log(Level.INFO, this.plugin.getLogger(), "Invalid item reward " + key);
                    }
                }
                if (item != null) {
                    item.setAmount(this.quantity);
                    ItemUtils.giveItem(item, player);
                }
            }
            case PERMISSION -> {
                if (!VaultManager.getPermissions().has(player, value)) {
                    VaultManager.getPermissions().playerAdd(null, player, value);
                }
            }
            case COMMAND -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtils.parsePlaceholders(value,
                    "player", player.getName(),
                    "x", String.valueOf(player.getLocation().getX()),
                    "y", String.valueOf(player.getLocation().getY()),
                    "z", String.valueOf(player.getLocation().getZ())
            ));
        }
    }

    public enum RewardType {
        COMMAND,
        ITEM,
        PERMISSION;

        public static RewardType getForKey(String value) {
            return switch (value) {
                case "command" -> COMMAND;
                case "permission" -> PERMISSION;
                default -> ITEM;
            };
        }

    }

}
