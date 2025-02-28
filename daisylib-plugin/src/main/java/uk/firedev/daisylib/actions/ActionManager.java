package uk.firedev.daisylib.actions;

import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.actions.types.BreakActionType;
import uk.firedev.daisylib.local.DaisyLib;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ActionManager {

    private static ActionManager instance = null;

    private boolean loaded = false;

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
        new BreakActionType().register(DaisyLib.getInstance());
        loaded = true;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void fire(@NotNull ActionType type, @NotNull ActionContext context) {
        DaisyLibActionEvent event = new DaisyLibActionEvent(type, context);
        DaisyLib.getInstance().getServer().getPluginManager().callEvent(event);
    }

}
