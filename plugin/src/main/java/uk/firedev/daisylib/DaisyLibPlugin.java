package uk.firedev.daisylib;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class DaisyLibPlugin extends JavaPlugin {

    private static DaisyLibPlugin INSTANCE;

    public DaisyLibPlugin() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException(getClass().getName() + " has already been assigned!");
        }
        INSTANCE = this;
    }

    public static @NotNull DaisyLibPlugin getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException(DaisyLibPlugin.class.getSimpleName() + " has not been assigned!");
        }
        return INSTANCE;
    }

    @Override
    public void onLoad() {}

    @Override
    public void onEnable() {
        DaisyLib.get().init(this);
    }

    @Override
    public void onDisable() {}

}
