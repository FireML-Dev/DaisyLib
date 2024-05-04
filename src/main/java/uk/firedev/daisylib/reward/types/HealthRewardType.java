package uk.firedev.daisylib.reward.types;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.local.DaisyLib;
import uk.firedev.daisylib.reward.RewardType;
import uk.firedev.daisylib.utils.ObjectUtils;

import java.util.logging.Level;

public class HealthRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value) {
        if (!checkAsync()) {
            return;
        }
        if (!ObjectUtils.isDouble(value)) {
            Loggers.log(Level.INFO, getLogger(), "Invalid number specified for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        double amount = Double.parseDouble(value);
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
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
    public @NotNull String getIdentifier() {
        return "HEALTH";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull JavaPlugin getPlugin() {
        return DaisyLib.getInstance();
    }

}
