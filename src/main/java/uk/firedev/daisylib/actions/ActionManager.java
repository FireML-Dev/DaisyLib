package uk.firedev.daisylib.actions;

import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.types.BreakActionType;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ActionManager {

    private static ActionManager instance = null;

    private boolean loaded = false;
    private final TreeMap<String, ArrayList<ActionListener>> listenerMap;

    private ActionManager() {
        listenerMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

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
        new BreakActionType().register(DaisyLib.getInstance());
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean registerListener(@NotNull String actionName, @NotNull ActionListener listener) {
        List<ActionListener> listeners = getListeners(actionName);
        if (listeners.contains(listener)) {
            return false;
        }
        listeners.add(listener);
        return true;
    }

    public void fire(@NotNull String actionName, @NotNull ActionContext context) {
        List<ActionListener> listeners = getListeners(actionName);
        if (listeners.isEmpty()) {
            return;
        }
        listeners.forEach(listener -> listener.accept(context));
    }

    private @NotNull ArrayList<ActionListener> getListeners(@NotNull String actionName) {
        return listenerMap.computeIfAbsent(actionName, k -> new ArrayList<>());
    }

}
