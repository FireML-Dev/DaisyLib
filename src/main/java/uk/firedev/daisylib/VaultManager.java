package uk.firedev.daisylib;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.config.MainConfig;

public class VaultManager {

    private static VaultManager instance = null;
    private static Economy economy = null;
    private static Permission permission = null;
    private static Chat chat = null;

    private boolean loaded = false;

    private VaultManager() {}

    public static VaultManager getInstance() {
        if (instance == null) {
            instance = new VaultManager();
        }
        return instance;
    }

    /**
     * Gets the Economy service, or throws a RuntimeException
     * @return the Economy service
     */
    public static @NotNull Economy getEconomyOrThrow(@Nullable String throwMessage) {
        if (economy == null) {
            throw new RuntimeException(throwMessage == null ? "Failed to fetch Vault Economy!" : throwMessage);
        }
        return economy;
    }

    /**
     * Gets the Economy service if available.
     * @return the Economy service, or null if not available.
     */
    public static @Nullable Economy getEconomy() {
        return economy;
    }

    /**
     * Gets the Permissions service, or throws a RuntimeException
     * @return the Permissions service
     */
    public static @NotNull Permission getPermissionOrThrow(@Nullable String throwMessage) {
        if (permission == null) {
            throw new RuntimeException(throwMessage == null ? "Failed to fetch Vault Permissions!" : throwMessage);
        }
        return permission;
    }

    /**
     * Gets the Permissions service if available.
     * @return the Permissions service, or null if not available.
     */
    public static @Nullable Permission getPermissions() {
        return permission;
    }

    /**
     * Gets the Chat service, or throws a RuntimeException
     * @return the Chat service
     */
    public static @NotNull Chat getChatOrThrow(@Nullable String throwMessage) {
        if (chat == null) {
            throw new RuntimeException(throwMessage == null ? "Failed to fetch Vault Chat!" : throwMessage);
        }
        return chat;
    }

    /**
     * Gets the Chat service if available.
     * @return the Chat service, or null if not available.
     */
    public static @Nullable Chat getChat() {
        return chat;
    }

    /**
     * Checks if the VaultManager is loaded.
     * @return true if VaultManager is loaded, false otherwise.
     */
    public boolean isLoaded() { return loaded; }

    /**
     * Loads Vault services if the Vault hook is enabled in the configuration.
     */
    public void load() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Vault was not found.");
            return;
        }
        if (MainConfig.getInstance().shouldHookVault()) {
            Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Loading VaultManager!");
            if (!setupEconomy()) {
                Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Vault Economy not found.");
            }
            if (!setupPermissions()) {
                Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Vault Permissions not found.");
            }
            if (!setupChat()) {
                Loggers.warn(DaisyLib.getInstance().getComponentLogger(), "Vault Chat not found.");
            }
            // Only set loaded if the vault hook is enabled.
            loaded = true;
        }
    }

    private boolean setupEconomy() {
        if (!DaisyLib.getInstance().getServer().getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = DaisyLib.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    private boolean setupPermissions() {
        if (!DaisyLib.getInstance().getServer().getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Permission> rsp = DaisyLib.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        permission = rsp.getProvider();
        return true;
    }

    private boolean setupChat() {
        if (!DaisyLib.getInstance().getServer().getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Chat> rsp = DaisyLib.getInstance().getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        }
        chat = rsp.getProvider();
        return true;
    }

}
