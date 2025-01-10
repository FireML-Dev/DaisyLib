package uk.firedev.daisylib.database;

import org.jetbrains.annotations.NotNull;

public interface DatabaseModule {

    void init();

    void save();

    default void register(@NotNull SQLiteDatabase database) {
        if (database.getConnection() == null) {
            throw new RuntimeException("Tried to load a DatabaseModule before the Database was loaded!");
        }
        database.registerModule(this);
    }

}
