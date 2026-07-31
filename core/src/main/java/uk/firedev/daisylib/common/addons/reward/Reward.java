package uk.firedev.daisylib.common.addons.reward;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.common.DaisyLib;

import java.util.Arrays;

public class Reward {

    private final @NonNull String key;
    private final @NonNull String value;
    private final RewardAddon rewardAddon;

    public Reward(@NonNull String identifier) {
        String[] split = identifier.split(":");
        if (split.length < 2) {
            DaisyLib.get().getLogging().warn(identifier + " is not formatted correctly. It won't be given as a reward");
            this.key = "";
            this.value = "";
        } else {
            this.key = split[0];
            this.value = String.join(":", Arrays.copyOfRange(split, 1, split.length));
        }
        this.rewardAddon = RewardAddonRegistry.get().get(this.key);
    }

    public RewardAddon getRewardAddon() {
        return this.rewardAddon;
    }

    public @NonNull String getKey() { return this.key; }

    public @NonNull String getValue() { return this.value; }

    public void give(@NonNull Player player, @Nullable Location location) {
        getRewardAddon().give(player, getValue(), location);
    }

    public void give(@NonNull OfflinePlayer player, @Nullable Location location) {
        if (getRewardAddon() == null) {
            DaisyLib.get().getLogging().warn("No reward found for key: " + getKey());
            return;
        }
        Player online = player.getPlayer();
        if (online != null) {
            give(online, location);
        }
    }

}
