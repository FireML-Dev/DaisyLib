package uk.firedev.daisylib;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.local.config.MainConfig;

import javax.annotation.Nullable;
import java.util.logging.Level;

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

    public static @NotNull Economy getEconomy() { return economy; }

    public static @NotNull Permission getPermissions() { return permission; }

    public static @NotNull Chat getChat() { return chat; }

    public boolean isLoaded() { return loaded; }

    public boolean load() {
        if (MainConfig.getInstance().shouldHookVault()) {
            if (!setupEconomy()) {
                Loggers.log(Level.WARNING, DaisyLib.getInstance().getLogger(), "Vault Economy not found. Disabling DaisyLib.");
                return false;
            }
            if (!setupPermissions()) {
                Loggers.log(Level.WARNING, DaisyLib.getInstance().getLogger(), "Vault Permissions not found. Disabling DaisyLib.");
                return false;
            }
            if (!setupChat()) {
                Loggers.log(Level.WARNING, DaisyLib.getInstance().getLogger(), "Vault Chat not found. Disabling DaisyLib.");
                return false;
            }
            // Only set loaded if the vault hook is enabled.
            loaded = true;
        }
        return true;
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
        return economy != null;
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
        return permission != null;
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
        return chat != null;
    }

}
