package uk.firedev.daisylib.database;

import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.Loggers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Helps with connecting to a SQLite Database
 */
public class Database {

    private JavaPlugin plugin = null;
    private Connection connection = null;

    public void setup(JavaPlugin plugin) {
        this.plugin = plugin;
        initConnection();
        initTables();
        reload();
    }

    public Connection getConnection() {
        if (this.connection == null) {
            initConnection();
        }
        return this.connection;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    private void initConnection() {

        // Make sure the data folder exists
        this.plugin.saveDefaultConfig();

        // Try to connect to the SQLite database
        String url = "jdbc:sqlite:" + this.plugin.getDataFolder() + "/data.db";

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            Loggers.log(Level.SEVERE, plugin.getLogger(), "Failed to connect to the database. Disabling " + plugin.getName() + ".");
            Loggers.logException(e, plugin.getLogger());
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        Loggers.log(Level.INFO, plugin.getLogger(), "Successfully connected to the database.");

    }

    public void initTables() { }

    public void reload() { }

    public void loadAll() { }

    public void saveAll() { }

}
