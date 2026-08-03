package uk.firedev.daisylib.external.vault;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.jspecify.annotations.NonNull;

/**
 * A wrapper around the default Vault Economy class to make things more simple.
 * <p>
 * All deprecated methods are forwarded to their alternatives, and {@link OfflinePlayer} objects are fetched using {@link Bukkit#getOfflinePlayer(String)}.
 * <p>
 * Also provides {@link #register(Plugin, ServicePriority)} for easy registration.
 */
public abstract class SimpleEconomy extends AbstractEconomy {

    /**
     * Registers this economy provider with the {@link org.bukkit.plugin.ServicesManager}.
     * @param plugin The plugin this provider belongs to.
     * @param priority The priority of this provider.
     */
    public void register(@NonNull Plugin plugin, @NonNull ServicePriority priority) {
        Bukkit.getServicesManager().register(
            Economy.class,
            this,
            plugin,
            priority
        );
    }

    private OfflinePlayer fetchPlayer(String name) {
        return Bukkit.getOfflinePlayer(name);
    }

    @Deprecated
    @Override
    public boolean hasAccount(String playerName) {
        return hasAccount(fetchPlayer(playerName));
    }

    @Override
    @Deprecated
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(fetchPlayer(playerName), worldName);
    }

    @Deprecated
    @Override
    public double getBalance(String playerName) {
        return getBalance(fetchPlayer(playerName));
    }

    @Deprecated
    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(fetchPlayer(playerName), world);
    }

    @Deprecated
    @Override
    public boolean has(String playerName, double amount) {
        return has(fetchPlayer(playerName), amount);
    }

    @Deprecated
    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(fetchPlayer(playerName), worldName, amount);
    }

    @Deprecated
    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return withdrawPlayer(fetchPlayer(playerName), amount);
    }

    @Deprecated
    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(fetchPlayer(playerName), worldName, amount);
    }

    @Deprecated
    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return depositPlayer(fetchPlayer(playerName), amount);
    }

    @Deprecated
    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(fetchPlayer(playerName), worldName, amount);
    }

    @Deprecated
    @Override
    public EconomyResponse createBank(String name, String player) {
        return createBank(name, fetchPlayer(player));
    }

    @Deprecated
    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return isBankOwner(name, fetchPlayer(playerName));
    }

    @Deprecated
    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return isBankMember(name, fetchPlayer(playerName));
    }

    @Deprecated
    @Override
    public boolean createPlayerAccount(String playerName) {
        return createPlayerAccount(fetchPlayer(playerName));
    }

    @Deprecated
    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(fetchPlayer(playerName), worldName);
    }

}
