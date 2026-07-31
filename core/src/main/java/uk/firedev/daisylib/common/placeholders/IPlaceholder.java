package uk.firedev.daisylib.common.placeholders;

import org.bukkit.OfflinePlayer;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface IPlaceholder {

    boolean shouldProcess(@NonNull String identifier);

    @Nullable String parse(@Nullable OfflinePlayer player, @NonNull String identifier);

}
