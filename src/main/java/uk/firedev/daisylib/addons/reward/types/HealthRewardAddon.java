package uk.firedev.daisylib.addons.reward.types;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.addons.reward.RewardAddon;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.daisylib.util.Loggers;
import uk.firedev.daisylib.util.Utils;


public class HealthRewardAddon extends RewardAddon {

    @Override
    public void doReward(@NotNull Player player, @NotNull String value) {
        Double amount = Utils.getDouble(value);
        if (amount == null) {
            Loggers.info(getClass(), "Invalid number specified: " + value);
            return;
        }
        AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
        double maxHealth;
        if (attribute == null) {
            maxHealth = 20.0D;
        } else {
            maxHealth = attribute.getValue();
        }
        double finalHealth = player.getHealth() + amount;
        if (finalHealth > maxHealth) {
            player.setHealth(maxHealth);
        } else if (finalHealth < 1)  {
            player.setHealth(1);
        } else {
            player.setHealth(finalHealth);
        }
    }

    @Override
    public @NotNull String getKey() {
        return "Health";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return DaisyLibPlugin.getInstance();
    }

}
