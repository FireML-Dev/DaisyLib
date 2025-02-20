package uk.firedev.daisylib.api.builders;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.api.message.string.StringReplacer;
import uk.firedev.daisylib.api.utils.ItemUtils;
import uk.firedev.daisylib.api.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ItemBuilder {

    private @NotNull ItemStack item;

    private ItemBuilder(@NotNull Material material) {
        this.item = ItemStack.of(material);
    }

    /**
     * Create an ItemBuilder from a material.
     * @param material The material to use.
     */
    public static ItemBuilder create(@NotNull Material material) {
        return new ItemBuilder(material);
    }

    /**
     * Create an ItemBuilder from a material.
     * @param material The material to use.
     * @param defaultMaterial The default material, if the provided material is invalid.
     */
    public static ItemBuilder create(@Nullable Material material, @NotNull Material defaultMaterial) {
        return new ItemBuilder(Objects.requireNonNullElse(material, defaultMaterial));
    }

    /**
     * Create an ItemBuilder from the material's name.
     * @param materialName The material's name.
     * @param defaultMaterial The default material, if the provided name is invalid.
     */
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
    public static ItemBuilder createWithConfig(@Nullable ConfigurationSection section, @Nullable ComponentReplacer displayReplacer, @Nullable ComponentReplacer loreReplacer) {
        return create(Material.AIR).loadConfig(section, displayReplacer, loreReplacer);
    }

    public ItemBuilder withMaterial(@NotNull Material material) {
        ItemStack newItem = ItemStack.of(material);
        newItem.setItemMeta(this.item.getItemMeta());
        this.item = newItem;
        return this;
    }

    public ItemBuilder withMaterial(@NotNull String materialName, @NotNull Material defaultMaterial) {
        return withMaterial(ItemUtils.getMaterial(materialName, defaultMaterial));
    }

    public ItemBuilder withDisplay(@NotNull Component display, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            display = replacer.replace(display);
        }
        final Component finalDisplay = display;
        this.item.editMeta(meta -> meta.displayName(finalDisplay));
        return this;
    }

    public ItemBuilder withStringDisplay(@NotNull String display, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            display = replacer.replace(display);
        }
        return withDisplay(ComponentMessage.fromString(display).getMessage(), null);
    }

    public ItemBuilder withLore(@NotNull List<Component> lore, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            lore = replacer.replace(lore);
        }
        final List<Component> finalLore = lore;
        this.item.editMeta(meta -> meta.lore(finalLore));
        return this;
    }

    public ItemBuilder withStringLore(@NotNull List<String> lore, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            lore = replacer.replace(lore);
        }
        return withLore(
                lore.stream()
                        .map(ComponentMessage::fromString)
                        .map(ComponentMessage::getMessage)
                        .toList(),
                null
        );
    }

    public ItemBuilder addLore(@NotNull Component line, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            line = replacer.replace(line);
        }
        final Component finalLine = line;
        this.item.editMeta(meta -> {
            List<Component> lore = Objects.requireNonNullElse(meta.lore(), new ArrayList<>());
            lore.add(finalLine);
            meta.lore(lore);
        });
        return this;
    }

    public ItemBuilder addStringLore(@NotNull String line, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            line = replacer.replace(line);
        }
        return addLore(ComponentMessage.fromString(line).getMessage(), null);
    }

    public ItemBuilder addLore(@NotNull List<Component> lines, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            lines = replacer.replace(lines);
        }
        lines.forEach(line -> addLore(line, null));
        return this;
    }

    public ItemBuilder addStringLore(@NotNull List<String> lines, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            lines = replacer.replace(lines);
        }
        return addLore(
                lines.stream()
                        .map(ComponentMessage::fromString)
                        .map(ComponentMessage::getMessage)
                        .toList(),
                null
        );
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

    /**
     * @deprecated Use {@link #getItem()} instead.
     */
    @Deprecated(forRemoval = true)
    public ItemStack build() {
        return this.item;
    }

    public @NotNull ItemStack getItem() {
        return this.item.clone();
    }

    // TODO support for external plugin items
    public ItemBuilder loadConfig(@Nullable ConfigurationSection section, @Nullable ComponentReplacer displayReplacer, @Nullable ComponentReplacer loreReplacer) {
        if (section == null) {
            return this;
        }

        Material material = ItemUtils.getMaterial(section.getString("material"));
        if (material != null) {
            withMaterial(material);
        }

        String display = section.getString("display");
        if (display != null) {
            withDisplay(ComponentMessage.fromString(display).getMessage(), displayReplacer);
        }

        List<Component> lore = section.getStringList("lore").stream()
            .map(ComponentMessage::fromString)
            .map(ComponentMessage::getMessage)
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
