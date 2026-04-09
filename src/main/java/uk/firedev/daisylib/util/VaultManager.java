package uk.firedev.daisylib.util;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.internal.config.MainConfig;

import java.util.List;

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
        DaisyLibPlugin plugin = DaisyLibPlugin.getInstance();
        Loggers.info(plugin.getComponentLogger(), "Loading VaultManager!");

        if (plugin.getMainConfig().enableTestingEconomy()) {
            Bukkit.getServicesManager().register(Economy.class, new TestingEconomy(), plugin, ServicePriority.Highest);
            Loggers.warn(plugin.getComponentLogger(), "Registered TestingEconomy.");
        }
        if (!setupEconomy()) {
            Loggers.warn(plugin.getComponentLogger(), "Vault Economy not found.");
        }
        if (!setupPermissions()) {
            Loggers.warn(plugin.getComponentLogger(), "Vault Permissions not found.");
        }
        if (!setupChat()) {
            Loggers.warn(plugin.getComponentLogger(), "Vault Chat not found.");
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

    static class TestingEconomy extends AbstractEconomy {

        /**
         * Checks if economy method is enabled.
         *
         * @return Success or Failure
         */
        @Override
        public boolean isEnabled() {
            return true;
        }

        /**
         * Gets name of economy method
         *
         * @return Name of Economy Method
         */
        @Override
        public String getName() {
            return "DaisyLib Testing Economy";
        }

        /**
         * Returns true if the given implementation supports banks.
         *
         * @return true if the implementation supports banks
         */
        @Override
        public boolean hasBankSupport() {
            return false;
        }

        /**
         * Some economy plugins round off after a certain number of digits.
         * This function returns the number of digits the plugin keeps
         * or -1 if no rounding occurs.
         *
         * @return number of digits after the decimal point kept
         */
        @Override
        public int fractionalDigits() {
            return 1;
        }

        /**
         * Format amount into a human readable String This provides translation into
         * economy specific formatting to improve consistency between plugins.
         *
         * @param amount to format
         * @return Human readable string describing amount
         */
        @Override
        public String format(double amount) {
            return "$" + amount;
        }

        /**
         * Returns the name of the currency in plural form.
         * If the economy being used does not support currency names then an empty string will be returned.
         *
         * @return name of the currency (plural)
         */
        @Override
        public String currencyNamePlural() {
            return "Dollars";
        }

        /**
         * Returns the name of the currency in singular form.
         * If the economy being used does not support currency names then an empty string will be returned.
         *
         * @return name of the currency (singular)
         */
        @Override
        public String currencyNameSingular() {
            return "Dollar";
        }

        /**
         *
         * @param playerName
         * @deprecated As of VaultAPI 1.4 use {@link #hasAccount(OfflinePlayer)} instead.
         */
        @Deprecated
        @Override
        public boolean hasAccount(String playerName) {
            return true;
        }

        /**
         * @param playerName
         * @param worldName
         * @deprecated As of VaultAPI 1.4 use {@link #hasAccount(OfflinePlayer, String)} instead.
         */
        @Override
        @Deprecated
        public boolean hasAccount(String playerName, String worldName) {
            return true;
        }

        /**
         * @param playerName
         * @deprecated As of VaultAPI 1.4 use {@link #getBalance(OfflinePlayer)} instead.
         */
        @Deprecated
        @Override
        public double getBalance(String playerName) {
            return Double.MAX_VALUE;
        }

        /**
         * @param playerName
         * @param world
         * @deprecated As of VaultAPI 1.4 use {@link #getBalance(OfflinePlayer, String)} instead.
         */
        @Deprecated
        @Override
        public double getBalance(String playerName, String world) {
            return Double.MAX_VALUE;
        }

        /**
         * @param playerName
         * @param amount
         * @deprecated As of VaultAPI 1.4 use {@link #has(OfflinePlayer, double)} instead.
         */
        @Deprecated
        @Override
        public boolean has(String playerName, double amount) {
            return true;
        }

        /**
         * @param playerName
         * @param worldName
         * @param amount
         * @deprecated As of VaultAPI 1.4 use @{link {@link #has(OfflinePlayer, String, double)} instead.
         */
        @Deprecated
        @Override
        public boolean has(String playerName, String worldName, double amount) {
            return true;
        }

        /**
         * @param playerName
         * @param amount
         * @deprecated As of VaultAPI 1.4 use {@link #withdrawPlayer(OfflinePlayer, double)} instead.
         */
        @Deprecated
        @Override
        public EconomyResponse withdrawPlayer(String playerName, double amount) {
            return new EconomyResponse(amount, Double.MAX_VALUE, EconomyResponse.ResponseType.SUCCESS, null);
        }

        /**
         * @param playerName
         * @param worldName
         * @param amount
         * @deprecated As of VaultAPI 1.4 use {@link #withdrawPlayer(OfflinePlayer, String, double)} instead.
         */
        @Deprecated
        @Override
        public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
            return new EconomyResponse(amount, Double.MAX_VALUE, EconomyResponse.ResponseType.SUCCESS, null);
        }

        /**
         * @param playerName
         * @param amount
         * @deprecated As of VaultAPI 1.4 use {@link #depositPlayer(OfflinePlayer, double)} instead.
         */
        @Deprecated
        @Override
        public EconomyResponse depositPlayer(String playerName, double amount) {
            return new EconomyResponse(amount, Double.MAX_VALUE, EconomyResponse.ResponseType.SUCCESS, null);
        }

        /**
         * @param playerName
         * @param worldName
         * @param amount
         * @deprecated As of VaultAPI 1.4 use {@link #depositPlayer(OfflinePlayer, String, double)} instead.
         */
        @Deprecated
        @Override
        public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
            return new EconomyResponse(amount, Double.MAX_VALUE, EconomyResponse.ResponseType.SUCCESS, null);
        }

        /**
         * @param name
         * @param player
         * @deprecated As of VaultAPI 1.4 use {{@link #createBank(String, OfflinePlayer)} instead.
         */
        @Deprecated
        @Override
        public EconomyResponse createBank(String name, String player) {
            return new EconomyResponse(Double.MAX_VALUE, Double.MAX_VALUE, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
        }

        /**
         * Deletes a bank account with the specified name.
         *
         * @param name of the back to delete
         * @return if the operation completed successfully
         */
        @Override
        public EconomyResponse deleteBank(String name) {
            return new EconomyResponse(Double.MAX_VALUE, Double.MAX_VALUE, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
        }

        /**
         * Returns the amount the bank has
         *
         * @param name of the account
         * @return EconomyResponse Object
         */
        @Override
        public EconomyResponse bankBalance(String name) {
            return new EconomyResponse(Double.MAX_VALUE, Double.MAX_VALUE, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
        }

        /**
         * Returns true or false whether the bank has the amount specified - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param name   of the account
         * @param amount to check for
         * @return EconomyResponse Object
         */
        @Override
        public EconomyResponse bankHas(String name, double amount) {
            return new EconomyResponse(Double.MAX_VALUE, Double.MAX_VALUE, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
        }

        /**
         * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param name   of the account
         * @param amount to withdraw
         * @return EconomyResponse Object
         */
        @Override
        public EconomyResponse bankWithdraw(String name, double amount) {
            return new EconomyResponse(Double.MAX_VALUE, Double.MAX_VALUE, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
        }

        /**
         * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
         *
         * @param name   of the account
         * @param amount to deposit
         * @return EconomyResponse Object
         */
        @Override
        public EconomyResponse bankDeposit(String name, double amount) {
            return new EconomyResponse(Double.MAX_VALUE, Double.MAX_VALUE, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
        }

        /**
         * @param name
         * @param playerName
         * @deprecated As of VaultAPI 1.4 use {{@link #isBankOwner(String, OfflinePlayer)} instead.
         */
        @Deprecated
        @Override
        public EconomyResponse isBankOwner(String name, String playerName) {
            return new EconomyResponse(Double.MAX_VALUE, Double.MAX_VALUE, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
        }

        /**
         * @param name
         * @param playerName
         * @deprecated As of VaultAPI 1.4 use {{@link #isBankMember(String, OfflinePlayer)} instead.
         */
        @Deprecated
        @Override
        public EconomyResponse isBankMember(String name, String playerName) {
            return new EconomyResponse(Double.MAX_VALUE, Double.MAX_VALUE, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
        }

        /**
         * Gets the list of banks
         *
         * @return the List of Banks
         */
        @Override
        public List<String> getBanks() {
            return List.of();
        }

        /**
         * @param playerName
         * @deprecated As of VaultAPI 1.4 use {{@link #createPlayerAccount(OfflinePlayer)} instead.
         */
        @Deprecated
        @Override
        public boolean createPlayerAccount(String playerName) {
            return true;
        }

        /**
         * @param playerName
         * @param worldName
         * @deprecated As of VaultAPI 1.4 use {{@link #createPlayerAccount(OfflinePlayer, String)} instead.
         */
        @Deprecated
        @Override
        public boolean createPlayerAccount(String playerName, String worldName) {
            return true;
        }
    }

}
