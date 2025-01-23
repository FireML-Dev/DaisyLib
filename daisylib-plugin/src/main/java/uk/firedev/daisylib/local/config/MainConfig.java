package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.config.ConfigBase;
import uk.firedev.daisylib.local.DaisyLib;

public class MainConfig extends ConfigBase {

    private static MainConfig instance = null;

    private MainConfig() {
        super("config.yml", "config.yml", DaisyLib.getInstance());
        withDefaultUpdaterSettings();
    }

    public static MainConfig getInstance() {
        if (instance == null) {
            instance = new MainConfig();
        }
        return instance;
    }

    public boolean doMoveBlockEvent() { return getConfig().getBoolean("custom-events.move-block", true); }

    public boolean doMoveChunkEvent() { return getConfig().getBoolean("custom-events.move-chunk", true); }

    public boolean doPlaceBreak() { return getConfig().getBoolean("enablePlaceBreak", true); }

    public boolean shouldHookVault() { return getConfig().getBoolean("vault", true); }

}
