package uk.firedev.daisylib;

import org.bukkit.Bukkit;
import org.bukkit.event.world.ChunkEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.events.brush.BrushEventListener;
import uk.firedev.daisylib.events.move.MoveEventListener;
import uk.firedev.daisylib.external.vault.VaultWrapper;
import uk.firedev.daisylib.logging.Logging;
import uk.firedev.daisylib.utils.CommonUtils;
import uk.firedev.daisylib.utils.MessageUtils;
import uk.firedev.daisylib.utils.VersionChecker;

import java.util.function.Supplier;

public class DaisyLib {

    private static final DaisyLib INSTANCE = new DaisyLib();
    public static final boolean IS_FOLIA = CommonUtils.classExists("io.papermc.paper.threadedregions.RegionizedServer");

    private Plugin plugin;
    private Logging logging = Logging.logging("DaisyLib");

    private DaisyLib() {
        if (VersionChecker.isOlderThan(Bukkit.getMinecraftVersion(), "1.21.1")) {
            throw new UnsupportedOperationException("Unsupported Minecraft version. Only 1.21.1 and above are supported.");
        }
    }

    public static @NonNull DaisyLib get() {
        return INSTANCE;
    }

    /**
     * Initializes DaisyLib. If another plugin has called this method, nothing will happen.
     * @param plugin Your plugin instance.
     */
    public void init(@NonNull Plugin plugin) {
        if (this.plugin != null) {
            getLogging().info("Skipping initialization attempt from " + plugin.getName());
            return;
        }
        this.plugin = plugin;
        this.logging = Logging.logging("DaisyLib via " + plugin.getName());

        VaultWrapper.get().load();
        registerListeners(plugin);
        this.logging.info("DaisyLib initialized successfully.");
    }

    public @NonNull Plugin getPlugin() {
        if (this.plugin == null) {
            logging.error("DaisyLib has not been initialized. You must call DaisyLib#init(Plugin).");
            throw new IllegalStateException();
        }
        return this.plugin;
    }

    public @NonNull Logging getLogging() {
        return this.logging;
    }

    private void registerListeners(@NonNull Plugin plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();

        if (Settings.ENABLE_MOVE_EVENTS.get()) {
            pm.registerEvents(new MoveEventListener(), plugin);
        }
        if (Settings.ENABLE_BRUSH_EVENT.get()) {
            pm.registerEvents(new BrushEventListener(), plugin);
        }
    }

    public static class Settings {

        /**
         * Should debug messages be shown? Default: false.
         * @see Logging#debug(String)
         */
        public static @NonNull Supplier<@NonNull Boolean> ENABLE_DEBUG = () -> false;

        /**
         * Should messages support legacy characters? Default: false.
         * @see MessageUtils#containsLegacy(String)
        */
        public static @NonNull Supplier<@NonNull Boolean> ALLOW_LEGACY_MESSAGES = () -> false;

        /**
         * Should custom move events be fired? Default: false.
         * @see uk.firedev.daisylib.events.move.PlayerMoveBlockEvent
         * @see uk.firedev.daisylib.events.move.PlayerMoveChunkEvent
        */
        public static @NonNull Supplier<@NonNull Boolean> ENABLE_MOVE_EVENTS = () -> false;

        /**
         * Should the custom brush event be fired? Default: false.
         * @see uk.firedev.daisylib.events.brush.BlockBrushEvent
         */
        public static @NonNull Supplier<@NonNull Boolean> ENABLE_BRUSH_EVENT = () -> false;

    }

}
