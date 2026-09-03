package uk.firedev.daisylib.item.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.config.serializer.PotionEffectSerializer;

import java.util.Map;
import java.util.function.BiConsumer;

public class PotionEffectItemConfig extends ItemConfig<PotionEffect> {

    public PotionEffectItemConfig(@NonNull ConfigurationSection section) {
        super(section);
    }

    public PotionEffectItemConfig(@NonNull PotionEffectItemConfig base) {
        super(base);
    }

    @Override
    public PotionEffect getConfiguredValue() {
        String potionSettings = section.getString("potion", section.getString("potion-effect"));
        if (potionSettings == null) {
            return null;
        }
        return PotionEffectSerializer.get().deserialize(potionSettings);
    }

    @Override
    public BiConsumer<ItemStack, PotionEffect> applyToItem(@Nullable OfflinePlayer player, @Nullable Map<String, ?> replacements) {
        return (item, value) -> {
            if (value == null) {
                DaisyLib.get().getLogging().warn(section.getName() + " has an invalid potion effect.");
                return;
            }
            item.editMeta(PotionMeta.class, meta -> meta.addCustomEffect(value, true));
        };
    }

    @Override
    public @NonNull PotionEffectItemConfig createCopy() {
        return new PotionEffectItemConfig(this);
    }

}
