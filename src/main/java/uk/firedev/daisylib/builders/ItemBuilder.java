package uk.firedev.daisylib.builders;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.utils.ComponentUtils;

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

    public ItemBuilder withMaterial(@NotNull Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder withDisplay(Component display) {
        this.display = display;
        return this;
    }

    public ItemBuilder withStringDisplay(String display) {
        this.display = ComponentUtils.parseComponent(display);
        return this;
    }

    public ItemBuilder withLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder withStringLore(List<String> lore) {
        this.lore = ComponentUtils.parseComponentList(lore);
        return this;
    }

    public ItemBuilder addLore(Component line) {
        this.lore.add(line);
        return this;
    }

    public ItemBuilder addStringLore(String line) {
        this.lore.add(ComponentUtils.parseComponent(line));
        return this;
    }

    public ItemBuilder addLore(List<Component> lines) {
        this.lore.addAll(lines);
        return this;
    }

    public ItemBuilder addStringLore(List<String> lines) {
        this.lore.addAll(ComponentUtils.parseComponentList(lines));
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

    public ItemBuilder addFlag(ItemFlag flag) {
        if (!this.flags.contains(flag)) {
            this.flags.add(flag);
        }
        return this;
    }

    public ItemBuilder removeFlag(ItemFlag flag) {
        this.flags.remove(flag);
        return this;
    }

    public ItemBuilder addFlags(List<ItemFlag> flags) {
        flags.forEach(flag -> {
            if (!this.flags.contains(flag)) {
                this.flags.add(flag);
            }
        });
        return this;
    }

    public ItemBuilder removeFlags(List<ItemFlag> flags) {
        this.flags.removeAll(flags);
        return this;
    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemBuilder removeAllEnchantments() {
        this.enchantments.clear();
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.enchantments.remove(enchantment);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments.putAll(enchantments);
        return this;
    }

    public ItemBuilder removeEnchantments(List<Enchantment> enchantments) {
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
