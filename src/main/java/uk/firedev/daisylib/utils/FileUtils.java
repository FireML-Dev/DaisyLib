package uk.firedev.daisylib.utils;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class FileUtils {

    public static File loadFile(@NotNull File directory, @NotNull String fileName, @NotNull String resourceName, @NotNull Plugin plugin) {
        createDirectory(directory);
        File configFile = new File(directory, fileName);
        if (!configFile.exists()) {
            if (!createFile(configFile)) {
                Loggers.warn(plugin.getComponentLogger(), "Failed to create " + fileName + "!");
                return null;
            }
            InputStream stream = plugin.getResource(resourceName);
            if (stream == null) {
                Loggers.error(plugin.getComponentLogger(), "Could not retrieve " + resourceName);
                return null;
            }
            try {
                Files.copy(stream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Loggers.logException(plugin.getComponentLogger(), e);
            }
            return configFile;
        }
        return configFile;
    }

    public static boolean createFile(@NotNull File file) {
        try {
            if (!file.exists()) {
                if (file.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.createNewFile();
                }
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static boolean createDirectory(@NotNull File directory) {
        if (!directory.exists()) {
            return directory.mkdirs();
        }
        return true;
    }

    /**
     * Retrieves all files in the given directory.
     *
     * @param directory The directory to search
     * @param ignoreUnderscoreFiles Should files that start with an underscore be ignored?
     * @param recursive Should this also search subdirectories?
     * @return A list of files in the directory. Returns an empty list if none.
     */
    public static List<File> getFilesInDirectory(@NotNull File directory, boolean ignoreUnderscoreFiles, boolean recursive) {
        List<File> finalList = new ArrayList<>();
        if (!directory.exists() || !directory.isDirectory()) {
            return finalList;
        }
        try {
            File[] fileArray = directory.listFiles();
            if (fileArray == null) {
                return finalList;
            }
            for (File file : fileArray) {
                if (ignoreUnderscoreFiles && file.getName().startsWith("_")) {
                    continue;
                }
                if (file.isDirectory() && recursive) {
                    finalList.addAll(getFilesInDirectory(file, ignoreUnderscoreFiles, true));
                } else if (file.isFile()) {
                    finalList.add(file);
                }
            }
        } catch (SecurityException exception) {
            Loggers.warn(
                    DaisyLib.getInstance().getComponentLogger(),
                    "Failed to retrieve files in " + directory.getAbsolutePath() + ": Access Denied.",
                    exception
            );
        }
        return finalList;
    }

    public static boolean doesDirectoryContainFile(@NotNull File directory, @NotNull String fileName, boolean recursive) {
        for (File file : getFilesInDirectory(directory, false, recursive)) {
            if (file.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

}
