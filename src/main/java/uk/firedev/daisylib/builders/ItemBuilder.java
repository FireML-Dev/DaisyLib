package uk.firedev.daisylib.builders;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;
import uk.firedev.daisylib.message.string.StringReplacer;
import uk.firedev.daisylib.utils.ItemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private Material material;
    private Component display = null;
    private List<Component> lore = new ArrayList<>();
    private List<ItemFlag> flags = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private boolean unbreakable = false;
    private int amount = 1;

    public ItemBuilder(@NotNull Material material) {
        this.material = material;
    }

    public ItemBuilder(@NotNull String materialName, @NotNull Material defaultMaterial) {
        this.material = ItemUtils.getMaterial(materialName, defaultMaterial);
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
        this.flags = List.of(ItemFlag.values());
        return this;
    }

    public ItemBuilder removeAllFlags() {
        this.flags.clear();
        return this;
    }

    public ItemBuilder addFlag(@NotNull ItemFlag flag) {
        if (!this.flags.contains(flag)) {
            this.flags.add(flag);
        }
        return this;
    }

    public ItemBuilder removeFlag(@NotNull ItemFlag flag) {
        this.flags.remove(flag);
        return this;
    }

    public ItemBuilder addFlags(@NotNull List<ItemFlag> flags) {
        flags.forEach(flag -> {
            if (!this.flags.contains(flag)) {
                this.flags.add(flag);
            }
        });
        return this;
    }

    public ItemBuilder removeFlags(@NotNull List<ItemFlag> flags) {
        this.flags.removeAll(flags);
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
        ItemMeta meta = stack.getItemMeta();
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
        meta.setUnbreakable(this.unbreakable);
        stack.setItemMeta(meta);
        stack.setAmount(this.amount);
        return stack;
    }

}
