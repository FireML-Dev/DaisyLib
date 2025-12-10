package uk.firedev.daisylib.local.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.config.ConfigBase;
import uk.firedev.daisylib.local.DaisyLib;

public class MainConfig extends ConfigBase {

    private static final MainConfig instance = new MainConfig();

    private MainConfig() {
        super("config.yml", "config.yml", DaisyLib.getInstance());
    }

    public static @NotNull MainConfig getInstance() {
        return instance;
    }

    public boolean doMoveBlockEvent() { return getConfig().getBoolean("custom-events.move-block", true); }

    public boolean doMoveChunkEvent() { return getConfig().getBoolean("custom-events.move-chunk", true); }

    public boolean shouldHookVault() { return getConfig().getBoolean("vault", true); }

    @Override
    public void performManualUpdates() {
        YamlConfiguration config = getConfig();
        if (config.contains("enablePlaceBreak")) {
            config.set("enablePlaceBreak", null);
        }
    }

}
