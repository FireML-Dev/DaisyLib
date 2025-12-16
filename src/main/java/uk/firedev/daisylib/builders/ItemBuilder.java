package uk.firedev.daisylib.builders;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.utils.ItemUtils;
import uk.firedev.daisylib.utils.ObjectUtils;
import uk.firedev.daisylib.utils.ReadOnlyPair;
import uk.firedev.messagelib.message.ComponentListMessage;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class ItemBuilder {

    private @NotNull ItemStack item;

    private ItemBuilder(@NotNull ItemStack item) {
        this.item = item;
    }

    private ItemBuilder(@NotNull ItemType itemType) {
        this.item = itemType.createItemStack();
    }

    /**
     * Create an ItemBuilder from an ItemType.
     * @param itemType The {@link ItemType} to use.
     */
    public static ItemBuilder create(@NotNull ItemType itemType) {
        return new ItemBuilder(itemType);
    }

    /**
     * Create an ItemBuilder from an ItemType.
     * @param itemType The {@link ItemType} to use.
     * @param defaultItemType The default ItemType, if the provided ItemType is invalid.
     */
    public static ItemBuilder create(@Nullable ItemType itemType, @NotNull ItemType defaultItemType) {
        return itemType == null ? new ItemBuilder(defaultItemType) : new ItemBuilder(itemType);
    }

    /**
     * Create an ItemBuilder from the ItemType's name.
     * @param itemName The ItemType's name.
     * @param defaultType The default ItemType, if the provided name is invalid.
     */
    public static ItemBuilder create(@Nullable String itemName, @NotNull ItemType defaultType) {
        return new ItemBuilder(ItemUtils.getItemType(itemName, defaultType));
    }

    public ItemBuilder withItemType(@NotNull ItemType itemType) {
        // No other way to do this until the ItemType API is properly implemented, so we're using the internal method.
        @SuppressWarnings("deprecation") Material material = itemType.asMaterial();
        if (material == null) {
            Loggers.warn(DaisyLib.getInstance().getLogger(), "ItemType " + itemType.key().asString() + " cannot be a Material.");
            return this;
        }
        this.item = this.item.withType(material);
        return this;
    }

    public ItemBuilder withItemType(@NotNull String itemName, @NotNull ItemType defaultType) {
        ItemType type = ItemUtils.getItemType(itemName, defaultType);
        return withItemType(type);
    }

    public ItemBuilder withDisplay(@NotNull Object display, @Nullable Replacer replacer) {
        ComponentSingleMessage message = ComponentMessage.componentMessage(display).replace(replacer);
        this.item.setData(DataComponentTypes.CUSTOM_NAME, message.get());
        return this;
    }

    public ItemBuilder withLore(@NotNull List<?> lore, @Nullable Replacer replacer) {
        ComponentListMessage message = ComponentMessage.componentMessage(lore).replace(replacer);
        this.item.setData(DataComponentTypes.LORE, ItemLore.lore(message.get()));
        return this;
    }

    public ItemBuilder addLore(@NotNull Object line, @Nullable Replacer replacer) {
        return addLore(List.of(line), replacer);
    }

    public ItemBuilder addLore(@NotNull List<?> lines, @Nullable Replacer replacer) {
        ItemLore lore = this.item.getData(DataComponentTypes.LORE);
        if (lore == null) {
            return withLore(lines, replacer);
        }
        ComponentListMessage message = ComponentMessage.componentMessage(lines).replace(replacer);
        ItemLore newLore = ItemLore.lore().addLines(lore.lines()).addLines(message.get()).build();
        this.item.setData(DataComponentTypes.LORE, newLore);
        return this;
    }

    /// Enchantments

    // Setting

    public ItemBuilder setEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        this.item.setData(DataComponentTypes.ENCHANTMENTS, ItemEnchantments.itemEnchantments(enchantments));
        return this;
    }

    // Adding

    public ItemBuilder addEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        ItemEnchantments data = this.item.getData(DataComponentTypes.ENCHANTMENTS);
        if (data == null) {
            return setEnchantments(enchantments);
        }
        ItemEnchantments newEnchantments = ItemEnchantments.itemEnchantments().addAll(data.enchantments()).addAll(enchantments).build();
        this.item.setData(DataComponentTypes.ENCHANTMENTS, newEnchantments);
        return this;
    }

    public ItemBuilder addEnchantment(@NotNull Enchantment enchantment, int level) {
        return addEnchantments(Map.of(enchantment, level));
    }

    // Removing

    public ItemBuilder removeAllEnchantments() {
        this.item.unsetData(DataComponentTypes.ENCHANTMENTS);
        return this;
    }

    public ItemBuilder removeEnchantments(@NotNull List<Enchantment> enchantments) {
        ItemEnchantments data = this.item.getData(DataComponentTypes.ENCHANTMENTS);
        if (data == null) {
            return this;
        }
        Map<Enchantment, Integer> newEnchants = new HashMap<>(data.enchantments());
        enchantments.forEach(newEnchants::remove);
        if (newEnchants.isEmpty()) {
            this.item.unsetData(DataComponentTypes.ENCHANTMENTS);
        } else {
            this.item.setData(DataComponentTypes.ENCHANTMENTS, ItemEnchantments.itemEnchantments(newEnchants));
        }
        return this;
    }

    public ItemBuilder removeEnchantment(@NotNull Enchantment enchantment) {
        return removeEnchantments(List.of(enchantment));
    }

    /// Unbreakable

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if (unbreakable) {
            this.item.setData(DataComponentTypes.UNBREAKABLE);
        } else {
            this.item.unsetData(DataComponentTypes.UNBREAKABLE);
        }
        return this;
    }

    /// Glowing

    public ItemBuilder setGlowing(boolean glowing) {
        this.item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, glowing);
        return this;
    }

    /// Amount

    public ItemBuilder withAmount(int amount) {
        this.item.setAmount(Math.max(1, amount));
        return this;
    }

    /// Utility

    /**
     * Allows more control over the item than what is provided in this class
     */
    public ItemBuilder editItem(@NotNull Function<ItemStack, ItemStack> function) {
        this.item = function.apply(this.item);
        return this;
    }

    public @NotNull ItemStack getItem() {
        return this.item.clone();
    }

    public boolean isEmpty() {
        return this.item.isEmpty();
    }

    /// Config Factory

    public static @NotNull ItemBuilder fromConfig(@Nullable ConfigurationSection section, @Nullable Replacer displayReplacer, @Nullable Replacer loreReplacer) {
        if (section == null) {
            return new ItemBuilder(ItemType.AIR);
        }
        String itemStr = section.getString("material", section.getString("item-type"));
        ItemStack item = ItemUtils.getItem(itemStr);
        if (item == null) {
            return new ItemBuilder(ItemType.AIR);
        }
        return fromConfigWithBaseItem(item, section, displayReplacer, loreReplacer);
    }

    public static @NotNull ItemBuilder fromConfigWithBaseItem(@NotNull ItemStack baseItem, @Nullable ConfigurationSection section, @Nullable Replacer displayReplacer, @Nullable Replacer loreReplacer) {
        ItemBuilder builder = new ItemBuilder(baseItem);
        if (section == null) {
            return builder;
        }

        Map<Enchantment, Integer> enchants = section.getStringList("enchantments").stream()
            .map(ItemBuilder::parseEnchantment)
            .filter(ReadOnlyPair::isNotEmpty)
            .collect(Collectors.toMap(
                ReadOnlyPair::left,
                ReadOnlyPair::right,
                (existing, replacement) -> existing
            ));

        String display = section.getString("display");
        if (display != null) {
            builder = builder.withDisplay(display, displayReplacer);
        }

        return builder
            .withLore(section.getStringList("lore"), loreReplacer)
            .setEnchantments(enchants)
            .setUnbreakable(section.getBoolean("unbreakable"))
            .withAmount(section.getInt("amount", 1))
            .setGlowing(section.getBoolean("glowing"));
    }

    private static ReadOnlyPair<Enchantment, Integer> parseEnchantment(@NotNull String enchantStr) {
        // Split namespace and level
        String[] levelSplit = enchantStr.split(",");

        // Get enchantment name and level
        String enchantName = levelSplit[0].toLowerCase();
        String levelString = (levelSplit.length > 1) ? levelSplit[1] : "1";

        // Create NamespacedKey and parse level
        NamespacedKey enchantKey = NamespacedKey.fromString(enchantName);
        if (enchantKey == null) {
            return ReadOnlyPair.empty();
        }
        Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(enchantKey);
        if (enchantment == null) {
            return ReadOnlyPair.empty();
        }

        int level = ObjectUtils.getIntOrDefault(levelString, 1);
        return new ReadOnlyPair<>(enchantment, level);
    }

}
