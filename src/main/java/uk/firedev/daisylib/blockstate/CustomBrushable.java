package uk.firedev.daisylib.blockstate;

import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CustomBrushable implements CustomBlockState<BrushableBlock> {

    private final BrushableBlock state;

    private CustomBrushable(@NonNull BrushableBlock state) {
        this.state = state;
    }

    public static @NonNull CustomBrushable customBrushable(@NonNull BrushableBlock state) {
        return new CustomBrushable(state);
    }

    public static @Nullable CustomBrushable customBrushable(@NonNull Block block) {
        if (block.getState() instanceof BrushableBlock state) {
            return new CustomBrushable(state);
        }
        return null;
    }

    @Override
    public @NonNull BrushableBlock getState() {
        return this.state;
    }

    @Override
    public @NonNull Block getBlock() {
        return this.state.getBlock();
    }

}
