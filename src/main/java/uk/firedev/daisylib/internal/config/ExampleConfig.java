package uk.firedev.daisylib.internal.config;

import org.jspecify.annotations.NonNull;
import uk.firedev.configlib.ConfigFile;
import uk.firedev.configlib.loading.Loader;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;

import java.io.File;
import java.io.IOException;

import static uk.firedev.configlib.loading.Loader.createFile;

public class ExampleConfig {

    private ExampleConfig() {}

    public static void load(@NonNull DaisyLibPlugin plugin) {
        String name = "examples.yml";
        File file = new File(plugin.getDataFolder(), name);
        file.delete();

        try {
            createFile(file, plugin.getResource(name));
        } catch (IOException exception) {
            Loggers.warn(plugin.getLogger(), "Failed to load examples.yml file.");
        }
    }

}
