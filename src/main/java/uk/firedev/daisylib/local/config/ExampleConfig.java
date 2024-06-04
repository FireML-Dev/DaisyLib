package uk.firedev.daisylib.local.config;

import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * This file is reset on every server start.
 */
public class ExampleConfig {

    public static void load() {
        DaisyLib plugin = DaisyLib.getInstance();
        File directory = plugin.getDataFolder();
        String fileName = "examples.yml";
        FileUtils.createDirectory(directory);
        File configFile = new File(directory, fileName);
        if (!FileUtils.createFile(configFile)) {
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Failed to create examples.yml");
            return;
        }
        InputStream stream = plugin.getResource(fileName);
        if (stream == null) {
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Failed to create examples.yml");
            return;
        }
        try {
            Files.copy(stream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Created examples.yml");
        } catch (IOException ex) {
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Failed to create examples.yml");
        }
    }

}
