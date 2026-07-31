package uk.firedev.daisylib.common.registry;

import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NonNull;

public interface RegistryItem {

    @NonNull NamespacedKey getKey();

}
