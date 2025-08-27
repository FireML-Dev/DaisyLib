package uk.firedev.daisylib.builders;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.firedev.daisylib.utils.ItemUtils;
import uk.firedev.daisylib.utils.ObjectUtils;
import uk.firedev.messagelib.message.ComponentListMessage;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ItemBuilder {

    private @NotNull ItemStack item;

    private ItemBuilder(@NotNull ItemType itemType) {
        this.item = itemType.createItemStack();
    }

    @Deprecated
    private ItemBuilder(@NotNull Material material) {
        this.item = ItemStack.of(material);
    }

    /**
     * Create an ItemBuilder from an ItemType.
     * @param itemType The {@link ItemType} to use.
     */
    public static ItemBuilder create(@NotNull ItemType itemType) {
        return new ItemBuilder(itemType);
    }

    /**
     * @deprecated Use {@link #create(ItemType)} instead.
     */
    @Deprecated
    public static ItemBuilder create(@NotNull Material material) {
        return new ItemBuilder(material);
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
     * @deprecated Use {@link #create(ItemType, ItemType)} instead.
     */
    @Deprecated
    public static ItemBuilder create(@Nullable Material material, @NotNull Material defaultMaterial) {
        return material == null ? new ItemBuilder(defaultMaterial) : new ItemBuilder(material);
    }

    /**
     * Create an ItemBuilder from the ItemType's name.
     * @param itemName The ItemType's name.
     * @param defaultType The default ItemType, if the provided name is invalid.
     */
    public static ItemBuilder create(@Nullable String itemName, @NotNull ItemType defaultType) {
        return new ItemBuilder(ItemUtils.getItemType(itemName, defaultType));
    }

    /**
     * @deprecated Use {@link #create(String, ItemType)} instead.
     */
    @Deprecated
    public static ItemBuilder create(@Nullable String materialName, @NotNull Material defaultMaterial) {
        return new ItemBuilder(ItemUtils.getMaterial(materialName, defaultMaterial));
    }

    /**
     * Creates an ItemBuilder with AIR, and loads the provided config.
     * @param section The config to load
     * @param displayReplacer A replacer for the item's display name
     * @param loreReplacer A replacer for the item's lore
     * @return A new ItemBuilder loaded from the provided config.
     */
    public static ItemBuilder createWithConfig(@Nullable ConfigurationSection section, @Nullable Replacer displayReplacer, @Nullable Replacer loreReplacer) {
        return create(ItemType.AIR).loadConfig(section, displayReplacer, loreReplacer);
    }

    public ItemBuilder withItemType(@NotNull ItemType itemType) {
        ItemStack newItem = itemType.createItemStack();
        newItem.setItemMeta(this.item.getItemMeta());
        this.item = newItem;
        return this;
    }

    /**
     * @deprecated Use {@link #withItemType(ItemType)} instead.
     */
    @Deprecated
    public ItemBuilder withMaterial(@NotNull Material material) {
        ItemStack newItem = ItemStack.of(material);
        newItem.setItemMeta(this.item.getItemMeta());
        this.item = newItem;
        return this;
    }

    public ItemBuilder withItemType(@NotNull String itemName, @NotNull ItemType defaultType) {
        return withItemType(ItemUtils.getItemType(itemName, defaultType));
    }

    /**
     * @deprecated Use {@link #withItemType(String, ItemType)} instead.
     */
    @Deprecated
    public ItemBuilder withMaterial(@NotNull String materialName, @NotNull Material defaultMaterial) {
        return withMaterial(ItemUtils.getMaterial(materialName, defaultMaterial));
    }

    public ItemBuilder withDisplay(@NotNull Object display, @Nullable Replacer replacer) {
        ComponentSingleMessage message = ComponentMessage.componentMessage(display).replace(replacer);
        this.item.editMeta(meta -> meta.displayName(message.get()));
        return this;
    }

    public ItemBuilder withLore(@NotNull List<?> lore, @Nullable Replacer replacer) {
        ComponentListMessage message = ComponentMessage.componentMessage(lore).replace(replacer);
        this.item.editMeta(meta -> meta.lore(message.get()));
        return this;
    }

    public ItemBuilder addLore(@NotNull Object line, @Nullable Replacer replacer) {
        ComponentSingleMessage message = ComponentMessage.componentMessage(line).replace(replacer);
        this.item.editMeta(meta -> {
            List<Component> lore = Objects.requireNonNullElse(meta.lore(), new ArrayList<>());
            lore.add(message.get());
            meta.lore(lore);
        });
        return this;
    }

    public ItemBuilder addLore(@NotNull List<?> lines, @Nullable Replacer replacer) {
        ComponentListMessage message = ComponentMessage.componentMessage(lines).replace(replacer);
        this.item.editMeta(meta -> {
            List<Component> lore = Objects.requireNonNullElse(meta.lore(), new ArrayList<>());
            lore.addAll(message.get());
            meta.lore(lore);
        });
        return this;
    }

    public ItemBuilder addAllFlags() {
        this.item.editMeta(meta -> meta.addItemFlags(ItemFlag.values()));
        return this;
    }

    public ItemBuilder removeAllFlags() {
        this.item.editMeta(meta -> meta.removeItemFlags(ItemFlag.values()));
        return this;
    }

    public ItemBuilder addFlag(@NotNull ItemFlag flag) {
        this.item.editMeta(meta -> meta.addItemFlags(flag));
        return this;
    }

    public ItemBuilder removeFlag(@NotNull ItemFlag flag) {
        this.item.editMeta(meta -> meta.removeItemFlags(flag));
        return this;
    }

    public ItemBuilder addFlags(@NotNull List<ItemFlag> flags) {
        this.item.editMeta(meta -> meta.addItemFlags(flags.toArray(ItemFlag[]::new)));
        return this;
    }

    public ItemBuilder removeFlags(@NotNull List<ItemFlag> flags) {
        this.item.editMeta(meta -> meta.removeItemFlags(flags.toArray(ItemFlag[]::new)));
        return this;
    }

    public ItemBuilder setEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        this.item.removeEnchantments();
        this.item.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder removeAllEnchantments() {
        this.item.removeEnchantments();
        return this;
    }

    public ItemBuilder addEnchantment(@NotNull Enchantment enchantment, int level) {
        this.item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(@NotNull Enchantment enchantment) {
        this.item.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder addEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        this.item.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder removeEnchantments(@NotNull List<Enchantment> enchantments) {
        enchantments.forEach(this.item::removeEnchantment);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.item.editMeta(meta -> meta.setUnbreakable(unbreakable));
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing) {
        this.item.editMeta(meta -> meta.setEnchantmentGlintOverride(glowing));
        return this;
    }

    public ItemBuilder withAmount(int amount) {
        this.item.setAmount(Math.max(1, amount));
        return this;
    }

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

    private ItemBuilder loadConfig(@Nullable ConfigurationSection section, @Nullable Replacer displayReplacer, @Nullable Replacer loreReplacer) {
        if (section == null) {
            return this;
        }

        ItemStack item = ItemUtils.getItem(section.getString("material"));
        if (item != null) {
            this.item = item;
        }

        String display = section.getString("display");
        if (display != null) {
            withDisplay(ComponentMessage.componentMessage(display).get(), displayReplacer);
        }

        List<Component> lore = section.getStringList("lore").stream()
            .map(ComponentMessage::componentMessage)
            .map(ComponentSingleMessage::get)
            .toList();
        withLore(lore, loreReplacer);

        List<ItemFlag> flags = section.getStringList("flags").stream()
            .map(flagString -> ObjectUtils.getEnumValue(ItemFlag.class, flagString))
            .filter(Objects::nonNull)
            .toList();
       addFlags(flags);

        List<String> stringEnchantments = section.getStringList("enchantments");
        for (String stringEnchantment : stringEnchantments) {
            // Split namespace and level
            String[] namespaceSplit = stringEnchantment.split(":");
            String namespace = (namespaceSplit.length > 1 ? namespaceSplit[0] : "minecraft").toLowerCase();
            String[] levelSplit = namespaceSplit[namespaceSplit.length - 1].split(",");

            // Get enchantment name and level
            String enchantName = levelSplit[0].toLowerCase();
            String levelString = (levelSplit.length > 1) ? levelSplit[1] : "1";

            // Create NamespacedKey and parse level
            NamespacedKey enchantKey = new NamespacedKey(namespace, enchantName);
            int level = Objects.requireNonNullElse(ObjectUtils.getInt(levelString), 1);

            // Fetch the enchantment and put it into the map
            Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(enchantKey);
            if (enchantment != null) {
                addEnchantment(enchantment, level);
            }
        }

        setUnbreakable(section.getBoolean("unbreakable"));

        int amount = section.getInt("amount", 1);
        withAmount(Math.max(1, amount));

        setGlowing(section.getBoolean("glowing"));

        return this;
    }

}
