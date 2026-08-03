package uk.firedev.daisylib.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public abstract class PlaceholderReceiver extends PlaceholderExpansion {

    public abstract @NonNull List<@NonNull IPlaceholder> getCustomPlaceholders();

    @Override
    public @Nullable String onRequest(@Nullable OfflinePlayer player, @NonNull String identifier) {
        for (IPlaceholder placeholder : getCustomPlaceholders()) {
            if (placeholder.shouldProcess(identifier)) {
                return placeholder.parse(player, identifier);
            }
        }
        return null;
    }

}
