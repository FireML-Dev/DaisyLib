package uk.firedev.daisylib.addons.action;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.registry.RegistryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ActionAddon<T extends Event> implements RegistryItem {

    private final Map<Plugin, ArrayList<Consumer<T>>> hooks = new HashMap<>();

    public ActionAddon() {}

    public abstract @NotNull Class<T> getEventType();

    /**
     * Adds a new hook into this action.
     * @param plugin The plugin that owns this hook.
     * @param consumer The event consumer to run when this action fires.
     */
    public void addHook(@NotNull Plugin plugin, @NotNull Consumer<T> consumer) {
        List<Consumer<T>> actionList = hooks.computeIfAbsent(plugin, k -> new ArrayList<>());
        actionList.add(consumer);
    }

    /**
     * Removes all hooks from this action.
     */
    public void removeAllHooks() {
        this.hooks.clear();
    }

    /**
     * Removes all hooks registered by the specified plugin.
     * @param plugin The plugin whose hooks should be removed.
     */
    public void removeHooks(@NotNull Plugin plugin) {
        this.hooks.remove(plugin);
    }

    public void fireEvent(T event) {
        System.out.println(event.getClass().getSimpleName());
        this.hooks.values().forEach(consumers ->
            consumers.forEach(consumer -> consumer.accept(event))
        );
    }

    public boolean register(boolean force) {
        return ActionAddonRegistry.get().register(this, force);
    }

    public boolean register() {
        return register(false);
    }

    public boolean unregister() {
        return ActionAddonRegistry.get().unregister(this);
    }

}
