package uk.firedev.daisylib.common.external.vault;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.utils.CommonUtils;

public class VaultWrapper {

    private static final VaultWrapper INSTANCE = new VaultWrapper();

    private boolean vaultAvailable;
    private Economy economy;
    private Permission permission;
    private Chat chat;

    private VaultWrapper() {}

    public static @NonNull VaultWrapper get() {
        return INSTANCE;
    }

    /**
     * Loads VaultWrapper. Can be called as many times as needed.
     */
    public void load() {
        vaultAvailable = CommonUtils.classExists("net.milkbowl.vault.economy.Economy");
        setupEconomy();
        setupPermissions();
        setupChat();
    }

    /**
     * @return Whether an Economy provider is registered.
     */
    public boolean isEconomyAvailable() {
        return economy != null;
    }

    /**
     * @return Whether a Permission provider is registered.
     */
    public boolean isPermissionAvailable() {
        return permission != null;
    }

    /**
     * @return Whether a Chat provider is registered.
     */
    public boolean isChatAvailable() {
        return chat != null;
    }

    /**
     * @return The registered Economy provider.
     * @throws IllegalStateException If there is no registered provider.
     */
    public @NonNull Economy getEconomy() throws IllegalStateException {
        if (economy == null) {
            throw new IllegalStateException("Economy is not available.");
        }
        return economy;
    }

    /**
     * @return The registered Economy provider, or {@code null} if there is no registered provider.
     */
    public @Nullable Economy getEconomyOrNull() {
        return economy;
    }

    /**
     * @return The registered Permission provider.
     * @throws IllegalStateException If there is no registered provider.
     */
    public @NonNull Permission getPermission() throws IllegalStateException {
        if (permission == null) {
            throw new IllegalStateException("Permission is not available.");
        }
        return permission;
    }

    /**
     * @return The registered Permission provider, or {@code null} if there is no registered provider.
     */
    public @Nullable Permission getPermissionOrNull() {
        return permission;
    }

    /**
     * @return The registered Chat provider.
     * @throws IllegalStateException If there is no registered provider.
     */
    public @NonNull Chat getChat() throws IllegalStateException {
        if (chat == null) {
            throw new IllegalStateException("Chat is not available.");
        }
        return chat;
    }

    /**
     * @return The registered Chat provider, or {@code null} if there is no registered provider.
     */
    public @Nullable Chat getChatOrNull() {
        return chat;
    }

    // Setup

    private void setupEconomy() {
        if (!vaultAvailable) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        economy = rsp.getProvider();
    }

    private void setupPermissions() {
        if (!vaultAvailable) {
            return;
        }
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return;
        }
        permission = rsp.getProvider();
    }

    private void setupChat() {
        if (!vaultAvailable) {
            return;
        }
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return;
        }
        chat = rsp.getProvider();
    }

}
