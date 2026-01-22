package uk.firedev.daisylib.addons.action;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.registry.Registry;
import uk.firedev.daisylib.util.Loggers;

import java.util.Map;
import java.util.TreeMap;

public class ActionAddonRegistry implements Registry<ActionAddon<?>> {

    private static final ActionAddonRegistry instance = new ActionAddonRegistry();

    private final Map<String, ActionAddon<?>> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private ActionAddonRegistry() {}

    public static @NonNull ActionAddonRegistry get() {
        return instance;
    }

    @NonNull
    @Override
    public Map<String, ActionAddon<?>> getRegistry() {
        return Map.copyOf(registry);
    }

    @Nullable
    @Override
    public ActionAddon<?> get(@NonNull String key) {
        return registry.get(key);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Event> ActionAddon<T> getWithType(@NonNull String key, @NonNull Class<T> eventType) {
        ActionAddon<?> addon = get(key);
        if (addon == null || !addon.getEventType().equals(eventType)) {
            return null;
        }
        // This is a safe cast, as we check above.
        return (ActionAddon<T>) addon;
    }

    @NonNull
    @Override
    public ActionAddon<?> getOrDefault(@NonNull String key, @NonNull ActionAddon<?> defaultValue) {
        return registry.getOrDefault(key, defaultValue);
    }

    @Override
    public boolean unregister(@NonNull String key) {
        ActionAddon<?> addon = registry.remove(key);
        if (addon == null) {
            return false;
        }
        addon.removeAllHooks();
        if (addon instanceof Listener listener) {
            HandlerList.unregisterAll(listener);
        }
        Loggers.info(DaisyLibPlugin.getInstance().getComponentLogger(), "Unregistered " + addon.getKey() + " Action.");
        return true;
    }

    @Override
    public boolean register(@NonNull ActionAddon<?> value, boolean force) {
        if (!force && registry.containsKey(value.getKey())) {
            return false;
        }
        if (!(value instanceof Listener listener)) {
            Loggers.warn(DaisyLibPlugin.getInstance().getComponentLogger(), "Action " + value.getClass().getSimpleName() + " is not a listener. Not registering.");
            return false;
        }
        registry.put(value.getKey(), value);
        Bukkit.getPluginManager().registerEvents(listener, DaisyLibPlugin.getInstance());
        Loggers.info(DaisyLibPlugin.getInstance().getComponentLogger(), "Registered " + value.getKey() + " Action.");
        return true;
    }

    /**
     * Checks if this registry is empty.
     *
     * @return Whether this registry is empty.
     */
    @Override
    public boolean isEmpty() {
        return registry.isEmpty();
    }

    /**
     * Removes all items from this registry.
     */
    @Override
    public void clear() {
        registry.clear();
    }
    
}
