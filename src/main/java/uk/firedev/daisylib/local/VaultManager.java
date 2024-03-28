package uk.firedev.daisylib.local;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.config.MainConfig;

import javax.annotation.Nullable;
import java.util.logging.Level;

public class VaultManager {

    private static VaultManager instance = null;
    private static Economy economy = null;
    private static Permission permission = null;
    private static Chat chat = null;

    private final DaisyLib plugin;
    private boolean loaded = false;

    private VaultManager() {
        plugin = DaisyLib.getInstance();
    }

    public static VaultManager getInstance() {
        if (instance == null) {
            instance = new VaultManager();
        }
        return instance;
    }

    public static @Nullable Economy getEconomy() { return economy; }

    public static @Nullable Permission getPermissions() { return permission; }

    public static @Nullable Chat getChat() { return chat; }

    public boolean isLoaded() { return loaded; }

    public boolean load() {
        if (MainConfig.getInstance().shouldHookVault()) {
            if (!setupEconomy()) {
                Loggers.log(Level.WARNING, plugin.getLogger(), "Vault Economy not found. Disabling DaisyLib.");
                return false;
            }
            if (!setupPermissions()) {
                Loggers.log(Level.WARNING, plugin.getLogger(), "Vault Permissions not found. Disabling DaisyLib.");
                return false;
            }
            if (!setupChat()) {
                Loggers.log(Level.WARNING, plugin.getLogger(), "Vault Chat not found. Disabling DaisyLib.");
                return false;
            }
            // Only set loaded if the vault hook is enabled.
            loaded = true;
        }
        return true;
    }

    private boolean setupEconomy() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private boolean setupPermissions() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        permission = rsp.getProvider();
        return permission != null;
    }

    private boolean setupChat() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("Vault")) {
            return false;
        }
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        }
        chat = rsp.getProvider();
        return chat != null;
    }

}
