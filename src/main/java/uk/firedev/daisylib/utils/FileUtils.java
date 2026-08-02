package uk.firedev.daisylib.utils;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static boolean loadFile(@NonNull File configFile, @Nullable String resourceName, @NonNull Plugin plugin) {
        if (configFile.exists()) {
            return true;
        }
        if (resourceName == null) {
            return createFile(configFile);
        }
        try (InputStream stream = plugin.getResource(resourceName)) {
            if (stream == null) {
                DaisyLib.get().getLogging().error("Could not retrieve " + resourceName);
                return false;
            }
            Files.copy(stream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException exception) {
            DaisyLib.get().getLogging().warn("Failed to create " + configFile.getName(), exception);
            return false;
        }
    }

    public static @Nullable File loadFile(@NonNull File directory, @NonNull String fileName, @NonNull String resourceName, @NonNull Plugin plugin) {
        File configFile = new File(directory, fileName);
        if (configFile.exists()) {
            return configFile;
        }
        try (InputStream stream = plugin.getResource(resourceName)) {
            if (stream == null) {
                DaisyLib.get().getLogging().error("Could not retrieve " + resourceName);
                return null;
            }
            Files.copy(stream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return configFile;
        } catch (IOException e) {
            DaisyLib.get().getLogging().exception(e);
            return null;
        }
    }

    public static boolean createFile(@NonNull File file) {
        try {
            if (!file.exists()) {
                if (file.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }


    public static @NonNull List<@NonNull File> getFilesInDirectoryWithExtension(@NonNull File directory, @Nullable String extension, boolean ignoreUnderscoreFiles, boolean recursive) {
        List<File> finalList = new ArrayList<>();
        if (!directory.exists() || !directory.isDirectory()) {
            return finalList;
        }
        try {
            FilenameFilter filter = extension == null ? null : (dir, name) -> name.endsWith(extension);
            File[] fileArray = directory.listFiles(filter);
            if (fileArray == null) {
                return finalList;
            }
            for (File file : fileArray) {
                if (ignoreUnderscoreFiles && file.getName().startsWith("_")) {
                    continue;
                }
                if (file.isDirectory() && recursive) {
                    finalList.addAll(getFilesInDirectoryWithExtension(file, extension, ignoreUnderscoreFiles, true));
                } else if (file.isFile()) {
                    finalList.add(file);
                }
            }
        } catch (SecurityException exception) {
            DaisyLib.get().getLogging().warn("Failed to retrieve files in " + directory.getAbsolutePath() + ": Access Denied.", exception);
        }
        return finalList;
    }

    public static @NonNull List<@NonNull File> getYamlFilesInDirectory(@NonNull File directory, boolean ignoreUnderscoreFiles, boolean recursive) {
        return getFilesInDirectoryWithExtension(directory, ".yml", ignoreUnderscoreFiles, recursive);
    }

    public static @NonNull List<@NonNull File> getFilesInDirectory(@NonNull File directory, boolean ignoreUnderscoreFiles, boolean recursive) {
        return getFilesInDirectoryWithExtension(directory, null, ignoreUnderscoreFiles, recursive);
    }

}
