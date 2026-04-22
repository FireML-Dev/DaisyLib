package uk.firedev.daisylib.builders;

import com.destroystokyo.paper.profile.ProfileProperty;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.persistence.PersistentDataContainer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.ReadOnlyPair;
import uk.firedev.daisylib.util.Utils;
import uk.firedev.messagelib.message.ComponentListMessage;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class ItemBuilder {

    private @NonNull ItemStack item;

    private ItemBuilder(@NonNull ItemStack item) {
        this.item = item;
    }

    private ItemBuilder(@NonNull ItemType itemType) {
        this.item = itemType.createItemStack();
    }

    /**
     * Create an ItemBuilder from an ItemType.
     * @param itemType The {@link ItemType} to use.
     */
    public static ItemBuilder create(@NonNull ItemType itemType) {
        return new ItemBuilder(itemType);
    }

    /**
     * Create an ItemBuilder from an ItemType.
     * @param itemType The {@link ItemType} to use.
     * @param defaultItemType The default ItemType, if the provided ItemType is invalid.
     */
    public static ItemBuilder create(@Nullable ItemType itemType, @NonNull ItemType defaultItemType) {
        return itemType == null ? new ItemBuilder(defaultItemType) : new ItemBuilder(itemType);
    }

    /**
     * Create an ItemBuilder from the ItemType's name.
     * @param itemName The ItemType's name.
     * @param defaultType The default ItemType, if the provided name is invalid.
     */
    public static ItemBuilder create(@Nullable String itemName, @NonNull ItemType defaultType) {
        return new ItemBuilder(Utils.getItemType(itemName, defaultType));
    }

    public static ItemBuilder skull(@NonNull UUID uuid) {
        return new ItemBuilder(ItemType.PLAYER_HEAD).withSkin(uuid);
    }

    public static ItemBuilder skull(@NonNull Player player) {
        return new ItemBuilder(ItemType.PLAYER_HEAD).withSkin(player);
    }

    public static ItemBuilder skull(@NonNull String base64) {
        return new ItemBuilder(ItemType.PLAYER_HEAD).withSkin(base64);
    }

    public ItemBuilder withItemType(@NonNull ItemType itemType) {
        // No other way to do this until the ItemType API is properly implemented, so we're using the internal method.
        @SuppressWarnings("deprecation") Material material = itemType.asMaterial();
        if (material == null) {
            Loggers.warn(DaisyLibPlugin.getInstance().getLogger(), "ItemType " + itemType.key().asString() + " cannot be a Material.");
            return this;
        }
        this.item = this.item.withType(material);
        return this;
    }

    public ItemBuilder withItemType(@NonNull String itemName, @NonNull ItemType defaultType) {
        ItemType type = Utils.getItemType(itemName, defaultType);
        return withItemType(type);
    }

    public ItemBuilder withDisplay(@NonNull Object display, @Nullable Replacer replacer) {
        ComponentSingleMessage message = ComponentMessage.componentMessage(display).replace(replacer);
        this.item.setData(DataComponentTypes.CUSTOM_NAME, message.get());
        return this;
    }

    public ItemBuilder withDisplay(@NonNull Object display) {
        return withDisplay(display, null);
    }

    public ItemBuilder withLore(@NonNull List<?> lore, @Nullable Replacer replacer) {
        ComponentListMessage message = ComponentMessage.componentMessage(lore).replace(replacer);
        this.item.setData(DataComponentTypes.LORE, ItemLore.lore(message.get()));
        return this;
    }

    public ItemBuilder withLore(@NonNull List<?> lore) {
        return withLore(lore, null);
    }

    public ItemBuilder addLore(@NonNull Object line, @Nullable Replacer replacer) {
        return addLore(List.of(line), replacer);
    }

    public ItemBuilder addLore(@NonNull Object line) {
        return addLore(line, null);
    }

    public ItemBuilder addLore(@NonNull List<?> lines, @Nullable Replacer replacer) {
        ItemLore lore = this.item.getData(DataComponentTypes.LORE);
        if (lore == null) {
            return withLore(lines, replacer);
        }
        ComponentListMessage message = ComponentMessage.componentMessage(lines).replace(replacer);
        ItemLore newLore = ItemLore.lore().addLines(lore.lines()).addLines(message.get()).build();
        this.item.setData(DataComponentTypes.LORE, newLore);
        return this;
    }

    public ItemBuilder addLore(@NonNull List<?> lines) {
        return addLore(lines, null);
    }

    // Enchantments

    // Setting

    public ItemBuilder setEnchantments(@NonNull Map<Enchantment, Integer> enchantments) {
        this.item.setData(DataComponentTypes.ENCHANTMENTS, ItemEnchantments.itemEnchantments(enchantments));
        return this;
    }

    // Adding

    public ItemBuilder addEnchantments(@NonNull Map<Enchantment, Integer> enchantments) {
        ItemEnchantments data = this.item.getData(DataComponentTypes.ENCHANTMENTS);
        if (data == null) {
            return setEnchantments(enchantments);
        }
        ItemEnchantments newEnchantments = ItemEnchantments.itemEnchantments().addAll(data.enchantments()).addAll(enchantments).build();
        this.item.setData(DataComponentTypes.ENCHANTMENTS, newEnchantments);
        return this;
    }

    public ItemBuilder addEnchantment(@NonNull Enchantment enchantment, int level) {
        return addEnchantments(Map.of(enchantment, level));
    }

    // Removing

    public ItemBuilder removeAllEnchantments() {
        this.item.unsetData(DataComponentTypes.ENCHANTMENTS);
        return this;
    }

    public ItemBuilder removeEnchantments(@NonNull List<Enchantment> enchantments) {
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

    public ItemBuilder removeEnchantment(@NonNull Enchantment enchantment) {
        return removeEnchantments(List.of(enchantment));
    }

    // Unbreakable

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if (unbreakable) {
            this.item.setData(DataComponentTypes.UNBREAKABLE);
        } else {
            this.item.unsetData(DataComponentTypes.UNBREAKABLE);
        }
        return this;
    }

    // Glowing

    public ItemBuilder setGlowing(boolean glowing) {
        this.item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, glowing);
        return this;
    }

    // Amount

    public ItemBuilder withAmount(int amount) {
        this.item.setAmount(Math.max(1, amount));
        return this;
    }

    // Skull Skin

    public ItemBuilder withSkin(@NonNull UUID uuid) {
        this.item.setData(
            DataComponentTypes.PROFILE,
            ResolvableProfile.resolvableProfile().uuid(uuid).build()
        );
        return this;
    }

    public ItemBuilder withSkin(@NonNull String base64) {
        ProfileProperty property = new ProfileProperty("textures", base64);
        this.item.setData(
            DataComponentTypes.PROFILE,
            ResolvableProfile.resolvableProfile().addProperty(property).build()
        );
        return this;
    }

    public ItemBuilder withSkin(@NonNull Player player) {
        this.item.setData(
            DataComponentTypes.PROFILE,
            ResolvableProfile.resolvableProfile(player.getPlayerProfile())
        );
        return this;
    }

    // Utility

    /**
     * Allows more control over the item than what is provided in this class
     */
    public ItemBuilder editItem(@NonNull Function<ItemStack, ItemStack> function) {
        this.item = function.apply(this.item);
        return this;
    }

    /**
     * Edits the {@link PersistentDataContainer} of the item.
     */
    public ItemBuilder editPdc(@NonNull Consumer<PersistentDataContainer> consumer) {
        this.item.editPersistentDataContainer(consumer);
        return this;
    }

    public @NonNull ItemStack getItem() {
        return this.item.clone();
    }

    public boolean isEmpty() {
        return this.item.isEmpty();
    }

    // Config Factory

    public static @NonNull ItemBuilder fromConfig(@Nullable ConfigurationSection section, @Nullable Replacer displayReplacer, @Nullable Replacer loreReplacer) {
        if (section == null) {
            return new ItemBuilder(ItemType.AIR);
        }
        String itemStr = section.getString("material", section.getString("item-type"));
        ItemStack item = Utils.getItem(itemStr);
        if (item == null) {
            return new ItemBuilder(ItemType.AIR);
        }
        return fromConfigWithBaseItem(item, section, displayReplacer, loreReplacer);
    }

    public static @NonNull ItemBuilder fromConfigWithBaseItem(@NonNull ItemStack baseItem, @Nullable ConfigurationSection section, @Nullable Replacer displayReplacer, @Nullable Replacer loreReplacer) {
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

    private static ReadOnlyPair<Enchantment, Integer> parseEnchantment(@NonNull String enchantStr) {
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

        int level = Utils.getIntOrDefault(levelString, 1);
        return new ReadOnlyPair<>(enchantment, level);
    }

}
