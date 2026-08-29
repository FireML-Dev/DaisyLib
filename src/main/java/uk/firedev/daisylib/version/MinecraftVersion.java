package uk.firedev.daisylib.version;

import org.bukkit.Bukkit;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public enum MinecraftVersion {
    V1_21_1("1.21.1", 121, 1),
    V1_21_3("1.21.3", 121, 3),
    V1_21_4("1.21.4", 121, 4),
    V1_21_5("1.21.5", 121, 5),
    V1_21_6("1.21.6", 121, 6),
    V1_21_7("1.21.7", 121, 7),
    V1_21_8("1.21.8", 121, 8),
    V1_21_9("1.21.9", 121, 9),
    V1_21_10("1.21.10", 121, 10),
    V1_21_11("1.21.11", 121, 11),
    V26_1("26.1", 261, 0),
    V26_1_1("26.1.1", 261, 1),
    V26_1_2("26.1.2", 261, 2),
    V26_2("26.2", 262, 2);

    private static final MinecraftVersion CURRENT = getVersionOrThrow(Bukkit.getMinecraftVersion());

    private final String versionStr;
    private final int major;
    private final int minor;

    MinecraftVersion(@NonNull String versionStr, int major, int minor) {
        this.versionStr = versionStr;
        this.major = major;
        this.minor = minor;
    }

    /**
     * @return {@code true} if this version is newer than the given version.
     */
    public boolean isNewerThan(@NonNull MinecraftVersion version) {
        if (this.major > version.major) {
            return true;
        }
        return this.major == version.major && this.minor > version.minor;
    }

    /**
     * @return {@code true} if this version is newer than or equal to the given version.
     */
    public boolean isNewerThanOrEqualTo(@NonNull MinecraftVersion version) {
        if (this.major > version.major) {
            return true;
        }
        return this.major == version.major && this.minor >= version.minor;
    }

    /**
     * @return {@code true} if this version is older than the given version.
     */
    public boolean isOlderThan(@NonNull MinecraftVersion version) {
        if (this.major < version.major) {
            return true;
        }
        return this.major == version.major && this.minor < version.minor;
    }

    /**
     * @return {@code true} if this version is older than or equal to the given version.
     */
    public boolean isOlderThanOrEqualTo(@NonNull MinecraftVersion version) {
        if (this.major < version.major) {
            return true;
        }
        return this.major == version.major && this.minor <= version.minor;
    }

    /**
     * @return {@code true} if this version is equal to the given version.
     */
    public boolean isEqualTo(@NonNull MinecraftVersion version) {
        return this == version;
    }

    // Getters

    /**
     * @return The current Minecraft version.
     */
    public static @NonNull MinecraftVersion getCurrentVersion() {
        return CURRENT;
    }

    /**
     * Fetches a {@link MinecraftVersion} from the given version string.
     * @return A valid {@link MinecraftVersion} if a match is found, or {@code null} otherwise.
     */
    public static @Nullable MinecraftVersion getVersion(@NonNull String versionStr) {
        for (MinecraftVersion version : values()) {
            if (version.versionStr.equals(versionStr)) {
                return version;
            }
        }
        return null;
    }

    /**
     * Fetches a {@link MinecraftVersion} from the given version string.
     * @return A valid {@link MinecraftVersion} if a match is found.
     * @throws RuntimeException if a match is not found.
     */
    public static @NonNull MinecraftVersion getVersionOrThrow(@NonNull String versionStr) {
        MinecraftVersion version = getVersion(versionStr);
        if (version == null) {
            throw new RuntimeException(versionStr + " does not match any MinecraftVersion.");
        }
        return version;
    }

}
