package uk.firedev.daisylib.database;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.database.exceptions.DatabaseLoadException;
import uk.firedev.daisylib.utils.FileUtils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Helps with connecting to a SQLite database.
 */
public abstract class SQLiteDatabase extends Database {

    private final String fileName;

    /**
     * Creates a new SQLiteDatabase instance with the specified file name.
     * @param plugin The plugin this database belongs to.
     * @param fileName The database's file name.
     */
    public SQLiteDatabase(@NotNull Plugin plugin, @NotNull String fileName) {
        super(plugin);
        this.fileName = fileName;
    }

    /**
     * Creates a new SQLiteDatabase instance.
     * @param plugin The plugin this database belongs to.
     */
    public SQLiteDatabase(@NotNull Plugin plugin) {
        super(plugin);
        this.fileName = "data.db";
    }

    @Override
    public boolean addTable(@NotNull String table, @NotNull Map<String, String> columns) throws SQLException {
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS " + table + " (");
        columns.forEach((column, type) -> builder.append(column).append(" ").append(type).append(", "));
        // To remove the trailing comma and space
        builder.setLength(builder.length() - 2);
        builder.append(")");
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(builder.toString());
            return true;
        }
    }

    @Override
    public boolean addColumn(@NotNull String table, @NotNull String column, @NotNull String type) throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("ALTER TABLE " + table + " ADD COLUMN " + column + " " + type);
            return true;
        } catch (SQLException exception) {
            if (exception.getMessage().contains("duplicate column name")) {
                return true;
            }
            throw exception;
        }
    }

    @Override
    public void initConnection() throws DatabaseLoadException {

        // Make sure the data folder exists
        if (!FileUtils.createDirectory(getPlugin().getDataFolder())) {
            Loggers.error(getPlugin().getComponentLogger(), "Failed to create the plugin's data folder!");
            return;
        }

        // Try to connect to the SQLite database
        String url = "jdbc:sqlite:" + getPlugin().getDataFolder() + "/" + this.fileName;

        try {
            Class.forName("org.sqlite.JDBC");
            setConnection(DriverManager.getConnection(url));
        } catch (SQLException | ClassNotFoundException | IllegalArgumentException exception) {
            throw new DatabaseLoadException(exception) ;
        }

        Loggers.info(getPlugin().getComponentLogger(), "Successfully connected to the database.");

    }

}
