package uk.firedev.daisylib.block;

import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomBrushable implements CustomBlockState<BrushableBlock> {

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

    @Override
    public @NotNull BrushableBlock getState() {
        return this.state;
    }

    @Override
    public @NotNull Block getBlock() {
        return this.state.getBlock();
    }

}
