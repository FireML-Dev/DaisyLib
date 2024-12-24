package uk.firedev.daisylib.actions;

import org.jetbrains.annotations.NotNull;

public abstract class ActionListener {

    abstract void accept(@NotNull ActionContext context);

    public boolean register(@NotNull String actionName) {
        return ActionManager.getInstance().registerListener(actionName, this);
    }

}
