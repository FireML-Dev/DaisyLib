package uk.firedev.daisylib.actions;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.actions.types.BreakActionType;
import uk.firedev.daisylib.local.DaisyLib;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ActionManager {

    private static ActionManager instance = null;

    private boolean loaded = false;
    private final Map<String, ActionType> loadedActionTypes = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private ActionManager() {}

    public static ActionManager getInstance() {
        if (instance == null) {
            instance = new ActionManager();
        }
        return instance;
    }

    public void load() {
        if (isLoaded()) {
            return;
        }
        new BreakActionType().register();
        loaded = true;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void registerActionType(@NotNull ActionType type) {
        loadedActionTypes.putIfAbsent(type.getActionIdentifier(), type);
        Bukkit.getPluginManager().registerEvents(type, type.getPlugin());
    }

    public @Nullable ActionType getActionType(@NotNull String identifier) {
        return loadedActionTypes.get(identifier);
    }

    public void fire(@NotNull ActionType type, @NotNull ActionContext context) {
        DaisyLibActionEvent event = new DaisyLibActionEvent(type, context);
        DaisyLib.getInstance().getServer().getPluginManager().callEvent(event);
    }

}
