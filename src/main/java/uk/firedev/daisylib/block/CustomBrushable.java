package uk.firedev.daisylib.block;

import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CustomBrushable {

    private final BrushableBlock state;

    private CustomBrushable(@NotNull BrushableBlock state) {
        this.state = state;
    }

    public static @NotNull CustomBrushable customBrushable(@NotNull BrushableBlock state) {
        return new CustomBrushable(state);
    }

    public static @Nullable CustomBrushable customBrushable(@NotNull Block block) {
        if (block.getState() instanceof BrushableBlock state) {
            return new CustomBrushable(state);
        }
        return null;
    }

    public @NotNull BrushableBlock getState() {
        return this.state;
    }

    public @NotNull Block getBlock() {
        return this.state.getBlock();
    }

    public void editPersistentDataContainer(@NotNull Consumer<PersistentDataContainer> consumer) {
        consumer.accept(state.getPersistentDataContainer());
    }

    public void save() {
        getBlock().setBlockData(this.state.getBlockData());
    }

}
