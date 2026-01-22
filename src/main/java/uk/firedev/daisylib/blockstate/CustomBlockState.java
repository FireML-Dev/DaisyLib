package uk.firedev.daisylib.blockstate;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

@ApiStatus.Internal
public interface CustomBlockState<T extends BlockState> {

    /**
     * Gets the block state.
     * @return The block state.
     */
    @NonNull T getState();

    /**
     * Gets the block this blockstate is for.
     * @return The block.
     */
    @NonNull Block getBlock();

    /**
     * Saves the block state to the block.
     */
    default void saveToBlock() {
        saveToBlock(getBlock());
    }

    /**
     * Saves the block state to the given block.
     * @param block The block to save to.
     */
    default void saveToBlock(@NonNull Block block) {
        block.setBlockData(getState().getBlockData());
    }

    /**
     * Edits the persistent data container if the block state is a PersistentDataHolder (e.g. TileState).
     * @param consumer The consumer to edit the persistent data container.
     */
    default void editPersistentDataContainer(@NonNull Consumer<PersistentDataContainer> consumer) {
        if (getState() instanceof PersistentDataHolder holder) {
            consumer.accept(holder.getPersistentDataContainer());
        }
    }

}
