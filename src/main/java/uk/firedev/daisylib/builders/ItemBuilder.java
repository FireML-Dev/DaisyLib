package uk.firedev.daisylib.builders;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;
import uk.firedev.daisylib.message.string.StringReplacer;
import uk.firedev.daisylib.utils.ItemUtils;
import uk.firedev.daisylib.utils.ObjectUtils;

import java.util.*;

public class ItemBuilder {

    private Material material;
    private Component display = null;
    private List<Component> lore = new ArrayList<>();
    private Set<ItemFlag> flags = new HashSet<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private boolean unbreakable = false;
    private int amount = 1;
    private boolean glowing = false;

    public ItemBuilder(@NotNull Material material) {
        this.material = material;
    }

    public ItemBuilder(@NotNull String materialName, @NotNull Material defaultMaterial) {
        this.material = ItemUtils.getMaterial(materialName, defaultMaterial);
    }

    /**
     * Build an ItemBuilder from a ConfigurationSection object.
     * @param section The ConfigurationSection for the item.
     * @param defaultMaterial The default material to use, if the configured material is invalid.
     */
    public ItemBuilder(@NotNull ConfigurationSection section, @NotNull Material defaultMaterial, @Nullable ComponentReplacer displayReplacer, @Nullable ComponentReplacer loreReplacer) {
        // Material
        this.material = ItemUtils.getMaterial(section.getString("material", defaultMaterial.toString()), defaultMaterial);

        // Display
        String display = section.getString("display");
        if (display != null) {
            this.display = new ComponentMessage(display).applyReplacer(displayReplacer).getMessage();
        }

        // Lore
        this.lore = section.getStringList("lore").stream().map(line -> new ComponentMessage(line).applyReplacer(loreReplacer).getMessage()).toList();

        // ItemFlags
        List<ItemFlag> flags = section.getStringList("flags").stream()
                .map(flagString -> {
                    try {
                        return ItemFlag.valueOf(flagString);
                    } catch (IllegalArgumentException ex) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        this.flags.addAll(flags);

        // Enchantments
        List<String> stringEnchantments = section.getStringList("enchantments");
        stringEnchantments.forEach(stringEnchantment -> {
            // Split namespace and level
            String[] namespaceSplit = stringEnchantment.split(":");
            String namespace = namespaceSplit.length > 1 ? namespaceSplit[0] : "minecraft";
            String[] levelSplit = namespaceSplit[namespaceSplit.length - 1].split(",");

            // Get enchantment name and level
            String enchantName = levelSplit[0];
            String levelString = (levelSplit.length > 1) ? levelSplit[1] : "1";

            // Create NamespacedKey and parse level
            NamespacedKey enchantKey = new NamespacedKey(namespace, enchantName);
            int level = Objects.requireNonNullElse(ObjectUtils.getInt(levelString), 1);

            // Fetch the enchantment and put it into the map
            Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(enchantKey);
            if (enchantment != null) {
                enchantments.put(enchantment, level);
            }
        });

        // Unbreakable
        this.unbreakable = section.getBoolean("unbreakable");

        // Amount
        int amount = section.getInt("amount", 1);
        if (amount < 1) {
            amount = 1;
        }
        this.amount = amount;

        // Glowing
        this.glowing = section.getBoolean("glowing");
    }

    public ItemBuilder withMaterial(@NotNull Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder withMaterial(@NotNull String materialName, @NotNull Material defaultMaterial) {
        this.material = ItemUtils.getMaterial(materialName, defaultMaterial);
        return this;
    }

    public ItemBuilder withDisplay(@NotNull Component display, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            display = replacer.replace(display);
        }
        this.display = display;
        return this;
    }

    public ItemBuilder withStringDisplay(@NotNull String display, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            display = replacer.replace(display);
        }
        this.display = new ComponentMessage(display).getMessage();
        return this;
    }

    public ItemBuilder withLore(@NotNull List<Component> lore, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            lore = replacer.replace(lore);
        }
        this.lore = lore;
        return this;
    }

    public ItemBuilder withStringLore(@NotNull List<String> lore, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            lore = replacer.replace(lore);
        }
        this.lore = lore.stream().map(line -> new ComponentMessage(line).getMessage()).toList();
        return this;
    }

    public ItemBuilder addLore(@NotNull Component line, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            line = replacer.replace(line);
        }
        this.lore.add(line);
        return this;
    }

    public ItemBuilder addStringLore(@NotNull String line, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            line = replacer.replace(line);
        }
        this.lore.add(new ComponentMessage(line).getMessage());
        return this;
    }

    public ItemBuilder addLore(@NotNull List<Component> lines, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            lines = replacer.replace(lines);
        }
        this.lore.addAll(lines);
        return this;
    }

    public ItemBuilder addStringLore(@NotNull List<String> lines, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            lines = replacer.replace(lines);
        }
        this.lore.addAll(lines.stream().map(line -> new ComponentMessage(line).getMessage()).toList());
        return this;
    }

    public ItemBuilder addAllFlags() {
        this.flags = Set.of(ItemFlag.values());
        return this;
    }

    public ItemBuilder removeAllFlags() {
        this.flags.clear();
        return this;
    }

    public ItemBuilder addFlag(@NotNull ItemFlag flag) {
        this.flags.add(flag);
        return this;
    }

    public ItemBuilder removeFlag(@NotNull ItemFlag flag) {
        this.flags.remove(flag);
        return this;
    }

    public ItemBuilder addFlags(@NotNull List<ItemFlag> flags) {
        this.flags.addAll(flags);
        return this;
    }

    public ItemBuilder removeFlags(@NotNull List<ItemFlag> flags) {
        flags.forEach(this.flags::remove);
        return this;
    }

    public ItemBuilder setEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemBuilder removeAllEnchantments() {
        this.enchantments.clear();
        return this;
    }

    public ItemBuilder addEnchantment(@NotNull Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(@NotNull Enchantment enchantment) {
        this.enchantments.remove(enchantment);
        return this;
    }

    public ItemBuilder addEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        this.enchantments.putAll(enchantments);
        return this;
    }

    public ItemBuilder removeEnchantments(@NotNull List<Enchantment> enchantments) {
        enchantments.forEach(enchantment -> this.enchantments.remove(enchantment));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    public ItemBuilder withAmount(int amount) {
        if (this.amount < 1) {
            amount = 1;
        }
        this.amount = amount;
        return this;
    }

    public ItemStack build() {
        if (this.material == null) {
            return null;
        }
        ItemStack stack = new ItemStack(this.material);
        stack.editMeta(meta -> {
            if (this.display != null) {
                meta.displayName(this.display);
            }
            if (!this.lore.isEmpty()) {
                meta.lore(this.lore);
            }
            if (!this.flags.isEmpty()) {
                meta.addItemFlags(this.flags.toArray(ItemFlag[]::new));
            }
            if (!this.enchantments.isEmpty()) {
                this.enchantments.forEach((enchantment, integer) -> meta.addEnchant(enchantment, integer, true));
            }
            meta.setEnchantmentGlintOverride(this.glowing);
            meta.setUnbreakable(this.unbreakable);
        });
        stack.setAmount(this.amount);
        return stack;
    }

}
