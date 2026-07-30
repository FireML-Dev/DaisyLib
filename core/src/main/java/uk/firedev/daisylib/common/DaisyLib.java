package uk.firedev.daisylib.common;

import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.common.logging.Logging;

public class DaisyLib {

    private static final DaisyLib INSTANCE = new DaisyLib();

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


}
