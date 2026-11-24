package uk.firedev.daisylib.config.serializer;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.item.CustomItem;
import uk.firedev.daisylib.registry.Registry;
import uk.firedev.daisylib.utils.CommonUtils;

public class ItemSerializer implements ConfigSerializer<ItemStack> {

    private static final ItemSerializer INSTANCE = new ItemSerializer();

    private ItemSerializer() {}

    public static @NonNull ItemSerializer get() {
        return INSTANCE;
    }

    @Override
    public @NonNull String serialize(@NonNull ItemStack element) {
        for (CustomItem custom : Registry.CUSTOM_ITEM) {
            String string = custom.convertToString(element);
            if (string != null) {
                return string;
            }
        }
        return element.getType().toString();
    }

    @Override
    public @Nullable ItemStack deserialize(@Nullable String element) {
        if (element == null) {
            return null;
        }
        ItemType type = CommonUtils.getItemType(element);
        if (type != null) {
            return type.createItemStack();
        }
        String[] split = element.split(":", 2);
        String prefix = split[0];
        String id = split[1];
        CustomItem custom = Registry.CUSTOM_ITEM.get(prefix);
        return custom == null ? null : custom.getItemStack(id);
    }

}
