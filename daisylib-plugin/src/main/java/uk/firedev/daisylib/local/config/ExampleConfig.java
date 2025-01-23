package uk.firedev.daisylib.local.config;

import org.bukkit.configuration.file.YamlConfiguration;
import uk.firedev.daisylib.api.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.api.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * This file is reset on every server start.
 */
public class ExampleConfig {

    public static void load() {
        DaisyLib daisyLib = DaisyLib.getInstance();
        InputStream resource = daisyLib.getResource("examples.yml");
        if (resource == null) {
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(resource));
        try {
            config.save(new File(daisyLib.getDataFolder(), "examples.yml"));
            Loggers.info(daisyLib.getComponentLogger(), "Created examples.yml");
        } catch (IOException exception) {
            Loggers.error(daisyLib.getComponentLogger(), "Failed to create examples.yml!");
        }
    }

}
