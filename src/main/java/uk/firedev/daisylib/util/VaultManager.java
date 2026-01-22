package uk.firedev.daisylib.util;

import net.milkbowl.vault2.chat.Chat;
import net.milkbowl.vault2.economy.Economy;
import net.milkbowl.vault2.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.internal.DaisyLibPlugin;

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
    public @NonNull Economy getEconomyOrThrow(@Nullable String throwMessage) {
        if (!loaded || economy == null) {
            throw new RuntimeException(throwMessage == null ? "Failed to fetch Vault Economy!" : throwMessage);
        }
        return economy;
    }

    /**
     * Gets the Economy service if available.
     * @return the Economy service, or null if not available.
     */
    public @Nullable Economy getEconomy() {
        if (!loaded) {
            return null;
        }
        return economy;
    }

    /**
     * Gets the Permissions service, or throws a RuntimeException
     * @return the Permissions service
     */
    public @NonNull Permission getPermissionOrThrow(@Nullable String throwMessage) {
        if (!loaded || permission == null) {
            throw new RuntimeException(throwMessage == null ? "Failed to fetch Vault Permissions!" : throwMessage);
        }
        return permission;
    }

    /**
     * Gets the Permissions service if available.
     * @return the Permissions service, or null if not available.
     */
    public @Nullable Permission getPermissions() {
        if (!loaded) {
            return null;
        }
        return permission;
    }

    /**
     * Gets the Chat service, or throws a RuntimeException
     * @return the Chat service
     */
    public @NonNull Chat getChatOrThrow(@Nullable String throwMessage) {
        if (!loaded || chat == null) {
            throw new RuntimeException(throwMessage == null ? "Failed to fetch Vault Chat!" : throwMessage);
        }
        return chat;
    }

    /**
     * Gets the Chat service if available.
     * @return the Chat service, or null if not available.
     */
    public @Nullable Chat getChat() {
        if (!loaded) {
            return null;
        }
        return chat;
    }

    /**
     * Checks if the VaultManager is loaded.
     * @return true if VaultManager is loaded, false otherwise.
     */
    public boolean isLoaded() { return loaded; }

    /**
     * Loads Vault hooks.
     * <p>
     * Only works with VaultUnlocked, so checks for their classes before loading.
     */
    public void load() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            return;
        }
        try {
            Class.forName("net.milkbowl.vault2.economy.Economy");
        } catch (ClassNotFoundException e) {
            Loggers.warn(DaisyLibPlugin.getInstance().getComponentLogger(), "Vault detected, but not VaultUnlocked. Please install VaultUnlocked.");
            return;
        }
        Loggers.info(DaisyLibPlugin.getInstance().getComponentLogger(), "Loading VaultManager!");
        if (!setupEconomy()) {
            Loggers.warn(DaisyLibPlugin.getInstance().getComponentLogger(), "Vault Economy not found.");
        }
        if (!setupPermissions()) {
            Loggers.warn(DaisyLibPlugin.getInstance().getComponentLogger(), "Vault Permissions not found.");
        }
        if (!setupChat()) {
            Loggers.warn(DaisyLibPlugin.getInstance().getComponentLogger(), "Vault Chat not found.");
        }
        // Only set loaded if the vault hook is enabled.
        loaded = true;
    }

    private boolean setupEconomy() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    private boolean setupPermissions() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        permission = rsp.getProvider();
        return true;
    }

    private boolean setupChat() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        }
        chat = rsp.getProvider();
        return true;
    }

}
