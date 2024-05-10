package uk.firedev.daisylib.utils;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

public class FileUtils {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File loadFile(@NotNull File directory, @NotNull String fileName, @NotNull Plugin plugin) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File configFile = new File(directory, fileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                Loggers.logException(e, plugin.getLogger());
            }

            InputStream stream = plugin.getResource(fileName);
            if (stream == null) {
                Loggers.log(Level.SEVERE, plugin.getLogger(), "Could not retrieve " + fileName);
                return null;
            }
            try {
                Files.copy(stream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Loggers.logException(e, plugin.getLogger());
            }
            return configFile;
        }
        return configFile;
    }

}
