package uk.firedev.daisylib.item;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.item.config.CustomModelDataItemConfig;
import uk.firedev.daisylib.item.config.DisplayNameItemConfig;
import uk.firedev.daisylib.item.config.DurabilityItemConfig;
import uk.firedev.daisylib.item.config.DyeColorItemConfig;
import uk.firedev.daisylib.item.config.EnchantmentsItemConfig;
import uk.firedev.daisylib.item.config.FireResistantItemConfig;
import uk.firedev.daisylib.item.config.GlowingItemConfig;
import uk.firedev.daisylib.item.config.HideTooltipItemConfig;
import uk.firedev.daisylib.item.config.ItemConfig;
import uk.firedev.daisylib.item.config.ItemModelItemConfig;
import uk.firedev.daisylib.item.config.ItemRarityItemConfig;
import uk.firedev.daisylib.item.config.LoreItemConfig;
import uk.firedev.daisylib.item.config.MaxStackSizeItemConfig;
import uk.firedev.daisylib.item.config.PotionEffectItemConfig;
import uk.firedev.daisylib.item.config.QuantityItemConfig;
import uk.firedev.daisylib.item.config.TooltipStyleItemConfig;
import uk.firedev.daisylib.item.config.UnbreakableItemConfig;
import uk.firedev.daisylib.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class ItemBuilder {

    public static @NonNull ItemStack DEFAULT_ITEM = ItemStack.empty();

    private final @NonNull ConfigurationSection section;
    private final @NonNull ItemStack baseItem;

    private boolean ignoreConfigs = false;

    @SuppressWarnings("rawtypes") // Safe to ignore.
    private final Map<Class<? extends ItemConfig>, ItemConfig<?>> configMap = new HashMap<>();

    private ItemBuilder(@NonNull ConfigurationSection section, @NonNull ItemStack baseItem) {
        this.section = section;
        this.baseItem = baseItem;
        loadConfigMap();
    }

    public static @NonNull ItemBuilder itemBuilder(@NonNull ConfigurationSection section, @NonNull ItemStack baseItem) {
        return new ItemBuilder(section, baseItem);
    }

    public static @NonNull ItemBuilder itemBuilder(@NonNull ConfigurationSection section) {
        String typeStr = section.getString("material", section.getString("type"));
        if (typeStr == null) {
            return new ItemBuilder(section, DEFAULT_ITEM);
        }
        ItemType type = CommonUtils.getItemType(typeStr);
        if (type == null) {
            return new ItemBuilder(section, DEFAULT_ITEM);
        }
        return new ItemBuilder(section, type.createItemStack());
    }

    public void setIgnoreConfigs(boolean ignore) {
        this.ignoreConfigs = ignore;
    }

    @SuppressWarnings("unchecked") // Safe to ignore.
    public <T extends ItemConfig<?>> @Nullable T getItemConfig(@NonNull Class<T> clazz) {
        return (T) configMap.get(clazz);
    }

    public @NonNull ItemStack build() {
        return build(null, null);
    }

    public @NonNull ItemStack build(@Nullable OfflinePlayer player) {
        return build(player, null);
    }

    public @NonNull ItemStack build(@Nullable Map<String, ?> replacements) {
        return build(null, replacements);
    }

    public @NonNull ItemStack build(@Nullable OfflinePlayer player, @Nullable Map<String, ?> replacements) {
        ItemStack item = baseItem.clone();
        if (ignoreConfigs) {
            return item;
        }
        for (ItemConfig<?> config : configMap.values()) {
            config.apply(item, player, replacements);
        }
        return item;
    }

    private void loadConfigMap() {
        loadConfig(new CustomModelDataItemConfig(section));
        loadConfig(new DisplayNameItemConfig(section));
        loadConfig(new DurabilityItemConfig(section));
        loadConfig(new DyeColorItemConfig(section));
        loadConfig(new EnchantmentsItemConfig(section));
        loadConfig(new FireResistantItemConfig(section));
        loadConfig(new GlowingItemConfig(section));
        loadConfig(new HideTooltipItemConfig(section));
        loadConfig(new ItemModelItemConfig(section));
        loadConfig(new ItemRarityItemConfig(section));
        loadConfig(new LoreItemConfig(section));
        loadConfig(new MaxStackSizeItemConfig(section));
        loadConfig(new PotionEffectItemConfig(section));
        loadConfig(new QuantityItemConfig(section));
        loadConfig(new TooltipStyleItemConfig(section));
        loadConfig(new UnbreakableItemConfig(section));
    }

    private <T extends ItemConfig<?>> void loadConfig(@NonNull T config) {
        configMap.put(config.getClass(), config);
    }

}
