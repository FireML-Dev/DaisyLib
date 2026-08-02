package uk.firedev.daisylib.database;

import org.jspecify.annotations.NonNull;

public interface DatabaseModule {

    void init();

    void save();

    default void register(@NonNull SQLiteDatabase database) {
        database.registerModule(this);
    }

}
