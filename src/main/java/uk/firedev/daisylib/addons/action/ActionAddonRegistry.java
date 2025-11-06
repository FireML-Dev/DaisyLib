package uk.firedev.daisylib.addons.action;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.registry.Registry;

import java.util.Map;
import java.util.TreeMap;

public class ActionAddonRegistry implements Registry<ActionAddon<?>> {

    private static final ActionAddonRegistry instance = new ActionAddonRegistry();

    private final Map<String, ActionAddon<?>> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private ActionAddonRegistry() {}

    public static @NotNull ActionAddonRegistry get() {
        return instance;
    }

    @NotNull
    @Override
    public Map<String, ActionAddon<?>> getRegistry() {
        return Map.copyOf(registry);
    }

    @Nullable
    @Override
    public ActionAddon<?> get(@NotNull String key) {
        return registry.get(key);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Event> ActionAddon<T> getWithType(@NotNull String key, @NotNull Class<T> eventType) {
        ActionAddon<?> addon = get(key);
        if (addon == null || !addon.getEventType().equals(eventType)) {
            return null;
        }
        // This is a safe cast, as we check above.
        return (ActionAddon<T>) addon;
    }

    @NotNull
    @Override
    public ActionAddon<?> getOrDefault(@NotNull String key, @NotNull ActionAddon<?> defaultValue) {
        return registry.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean unregister(@NotNull String key) {
        ActionAddon<?> addon = registry.remove(key);
        if (addon == null) {
            return false;
        }
        addon.removeAllHooks();
        if (addon instanceof Listener listener) {
            HandlerList.unregisterAll(listener);
        }
        Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Unregistered " + addon.getKey() + " Action.");
        return true;
    }

    @Override
    public boolean register(@NotNull ActionAddon<?> value, boolean force) {
        if (!force && registry.containsKey(value.getKey())) {
            return false;
        }
        registry.put(value.getKey(), value);
        if (value instanceof Listener listener) {
            Bukkit.getPluginManager().registerEvents(listener, DaisyLib.getInstance());
        }
        Loggers.info(DaisyLib.getInstance().getComponentLogger(), "Registered " + value.getKey() + " Action.");
        return true;
    }
    
}
