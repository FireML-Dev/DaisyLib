package uk.firedev.daisylib.reward.types;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.reward.RewardType;

public class HealthRewardType extends RewardType {

    @Override
    public void give(@NonNull Player player, @NonNull String value, @Nullable Location location) {
        double rewardHealth;
        try {
            rewardHealth = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            DaisyLib.get().getLogging().warn("Invalid number for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
        double maxHealth;
        if (attribute == null) {
            maxHealth = 20.0D;
        } else {
            maxHealth = attribute.getValue();
        }
        double finalHealth = player.getHealth() + rewardHealth;
        if (finalHealth > maxHealth) {
            player.setHealth(maxHealth);
        } else if (finalHealth < 0)  {
            player.setHealth(0);
        } else {
            player.setHealth(finalHealth);
        }
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Health";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull JavaPlugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return true;
    }

}
