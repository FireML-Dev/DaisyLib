package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;

/**
 * This file is reset on every server start.
 */
public class ExampleConfig {

    public static void load() {
        DaisyLib.getInstance().saveResource("examples.yml", true);
        Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Regenerated examples.yml");
    }

}
