package uk.firedev.daisylib.internal.config;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.config.ConfigBase;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

@ApiStatus.Internal
public class MainConfig extends ConfigBase {

    public MainConfig() {
        super("config.yml", "config.yml", DaisyLibPlugin.getInstance());
    }

}
