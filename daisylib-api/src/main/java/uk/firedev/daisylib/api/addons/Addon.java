package uk.firedev.daisylib.api.addons;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface Addon {

    @NotNull String getIdentifier();

    @NotNull Plugin getOwningPlugin();

    @NotNull String getAuthor();

}
