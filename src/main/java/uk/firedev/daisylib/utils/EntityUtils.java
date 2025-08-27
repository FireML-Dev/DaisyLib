package uk.firedev.daisylib.utils;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class EntityUtils {

    public static <E extends Entity> void modifyEntity(@NotNull Entity entity, @NotNull Class<E> classCheck, @NotNull Consumer<? super E> consumer) {
        if (classCheck.isInstance(entity)) {
            E checked = classCheck.cast(entity);
            consumer.accept(checked);
        }
    }

}
