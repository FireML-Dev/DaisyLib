package uk.firedev.daisylib.addons.action.types;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.action.ActionAddon;

public class BucketActionAddon extends ActionAddon<PlayerBucketEntityEvent> implements Listener {

    @NotNull
    @Override
    public Class<PlayerBucketEntityEvent> getEventType() {
        return PlayerBucketEntityEvent.class;
    }

    @NotNull
    @Override
    public String getKey() {
        return "bucket";
    }

    @EventHandler(ignoreCancelled = true)
    public void onBucket(PlayerBucketEntityEvent event) {
        fireEvent(event, event.getEntity().getType().toString());
    }

}

