package uk.firedev.daisylib.api.database;

import org.jetbrains.annotations.NotNull;

public interface DatabaseModule {

    void init();

    void save();

    default void register(@NotNull SQLiteDatabase database) {
        database.registerModule(this);
    }

}
