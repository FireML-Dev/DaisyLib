package uk.firedev.daisylib.actions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ActionContext {

    private Player player;
    private Block block;
    private Entity affectedEntity;

    private ActionContext() {}

    public static ActionContext create() {
        return new ActionContext();
    }

    public ActionContext withPlayer(@Nullable Player player) {
        this.player = player;
        return this;
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public ActionContext withBlock(@Nullable Block block) {
        this.block = block;
        return this;
    }

    public @Nullable Block getBlock() {
        return block;
    }

    public ActionContext withAffectedEntity(@Nullable Entity affectedEntity) {
        this.affectedEntity = affectedEntity;
        return this;
    }

    public @Nullable Entity getAffectedEntity() {
        return affectedEntity;
    }

}
