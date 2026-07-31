package uk.firedev.daisylib.common;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.common.events.CustomEventListener;
import uk.firedev.daisylib.common.logging.Logging;
import uk.firedev.daisylib.common.utils.CommonUtils;

import java.util.function.Supplier;

public class DaisyLib {

    private static final DaisyLib INSTANCE = new DaisyLib();
    public static final boolean IS_FOLIA = CommonUtils.classExists("io.papermc.paper.threadedregions.RegionizedServer");

    private JavaPlugin plugin;
    private Logging logging = Logging.logging("DaisyLib");

    private DaisyLib() {}

    public static @NonNull DaisyLib get() {
        return INSTANCE;
    }

    public void init(@NonNull JavaPlugin plugin) {
        if (this.plugin != null) {
            throw new UnsupportedOperationException("DaisyLib is already initialized!");
        }
        this.plugin = plugin;
        this.logging = Logging.logging("DaisyLib via " + plugin.getName());
        registerListeners();
    }

    public @NonNull JavaPlugin getPlugin() {
        if (this.plugin == null) {
            logging.error("DaisyLib has not been initialized. You must call DaisyLib#init(JavaPlugin).");
            throw new IllegalStateException();
        }
        return this.plugin;
    }

    public @NonNull Logging getLogging() {
        return this.logging;
    }

    private void registerListeners() {
        JavaPlugin plugin = getPlugin();
        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new CustomEventListener(), plugin);
    }

    public void loadDefaultAddons() {

    }

    public static class Settings {

        // Should debug messages be shown? Defaults to false.
        public static @NonNull Supplier<@NonNull Boolean> ENABLE_DEBUG = () -> false;

        // Should Minecraft messages support legacy characters? Defaults to false.
        public static @NonNull Supplier<@NonNull Boolean> ALLOW_LEGACY_MESSAGES = () -> false;

    }

}
