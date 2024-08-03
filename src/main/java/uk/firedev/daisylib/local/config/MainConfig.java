package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.local.DaisyLib;

public class MainConfig extends uk.firedev.daisylib.Config {

    private static MainConfig instance = null;

    private MainConfig() {
        super("config.yml", "config.yml", DaisyLib.getInstance(), true);
    }

    public static MainConfig getInstance() {
        if (instance == null) {
            instance = new MainConfig();
        }
        return instance;
    }

    public boolean doMoveBlockEvent() { return getConfig().getBoolean("config.custom-events.move-block", true); }

    public boolean doMoveChunkEvent() { return getConfig().getBoolean("config.custom-events.move-chunk", true); }

    public boolean doPlaceBreak() { return getConfig().getBoolean("config.enablePlaceBreak", true); }

    public boolean shouldHookVault() { return getConfig().getBoolean("config.vault", true); }

}
