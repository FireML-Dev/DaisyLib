package uk.firedev.daisylib.internal.config;

import org.jetbrains.annotations.ApiStatus;
import uk.firedev.daisylib.config.ConfigBase;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

@ApiStatus.Internal
public class MainConfig extends ConfigBase {

    public MainConfig() {
        super("config.yml", "config.yml", DaisyLibPlugin.getInstance());
    }

    public boolean enableTestingEconomy() {
        return getConfig().getBoolean("enable-testing-economy", false);
    }

}
