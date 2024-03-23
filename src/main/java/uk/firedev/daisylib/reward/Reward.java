package uk.firedev.daisylib.reward;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.Objects;
import java.util.logging.Level;

public class Reward {

    private @NotNull String key;
    private @NotNull String value;
    private int quantity;
    private final JavaPlugin plugin;

    public Reward(@NotNull String identifier, JavaPlugin plugin) {
        this.plugin = Objects.requireNonNullElse(plugin, DaisyLib.getInstance());
        String[] split = identifier.split(":");
        try {
            this.key = split[0];
        } catch (IndexOutOfBoundsException ex) {
            Loggers.log(Level.INFO, this.plugin.getLogger(), "Broken reward " + identifier);
            this.key = "";
        }
        try {
            this.value = split[1];
        } catch (IndexOutOfBoundsException ex) {
            this.value = "";
        }
        String[] quantitySplit = identifier.split(";");
        try {
            String quantity = quantitySplit[1];
            this.key = this.key.replace(";" + quantity, "");
            this.value = this.value.replace(";" + quantity, "");
            this.quantity = Integer.parseInt(quantity);
        } catch (IndexOutOfBoundsException|NumberFormatException ex) {
            this.quantity = 1;
        }
    }

    public void rewardPlayer(@NotNull Player player) {
        for (RewardType rewardType : RewardManager.getInstance().getRegisteredRewardTypes()) {
            if (rewardType.isApplicable(this.key)) {
                rewardType.doReward(player, this.key, this.value, this.quantity);
                return;
            }
        }
    }

}
