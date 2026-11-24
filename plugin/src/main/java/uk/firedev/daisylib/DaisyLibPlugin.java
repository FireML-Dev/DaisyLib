package uk.firedev.daisylib;

import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.item.types.DenizenCustomItem;
import uk.firedev.daisylib.item.types.NexoCustomItem;
import uk.firedev.daisylib.recipe.RecipeUtil;
import uk.firedev.daisylib.recipe.choice.CustomItemChoiceWrapper;
import uk.firedev.daisylib.registry.Registry;
import uk.firedev.daisylib.requirement.types.BiomeRequirementType;
import uk.firedev.daisylib.requirement.types.DisabledRequirementType;
import uk.firedev.daisylib.requirement.types.GroupRequirementType;
import uk.firedev.daisylib.requirement.types.PermissionRequirementType;
import uk.firedev.daisylib.requirement.types.PlaceholderRequirementType;
import uk.firedev.daisylib.requirement.types.RewardTypeRequirementType;
import uk.firedev.daisylib.requirement.types.WeatherRequirementType;
import uk.firedev.daisylib.requirement.types.WorldRequirementType;
import uk.firedev.daisylib.reward.types.CommandRewardType;
import uk.firedev.daisylib.reward.types.EXPRewardType;
import uk.firedev.daisylib.reward.types.EffectRewardType;
import uk.firedev.daisylib.reward.types.HealthRewardType;
import uk.firedev.daisylib.reward.types.HungerRewardType;
import uk.firedev.daisylib.reward.types.ItemRewardType;
import uk.firedev.daisylib.reward.types.MoneyRewardType;
import uk.firedev.daisylib.reward.types.PermissionRewardType;
import uk.firedev.daisylib.reward.types.SoundRewardType;
import uk.firedev.daisylib.reward.types.message.ActionBarRewardType;
import uk.firedev.daisylib.reward.types.message.MessageRewardType;
import uk.firedev.daisylib.reward.types.message.SubtitleRewardType;
import uk.firedev.daisylib.reward.types.message.TitleRewardType;

public class DaisyLibPlugin extends JavaPlugin {

    private static DaisyLibPlugin INSTANCE;

    public DaisyLibPlugin() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException(getClass().getName() + " has already been assigned!");
        }
        INSTANCE = this;
    }

    public static @NonNull DaisyLibPlugin get() {
        if (INSTANCE == null) {
            throw new IllegalStateException(DaisyLibPlugin.class.getSimpleName() + " has not been assigned!");
        }
        return INSTANCE;
    }

    @Override
    public void onLoad() {}

    @Override
    public void onEnable() {
        DaisyLib.get().init(this);
        loadRegistries();
        loadRecipeChoices();
    }

    @Override
    public void onDisable() {}

    private void loadRecipeChoices() {
        RecipeUtil.registerRecipeChoice(new CustomItemChoiceWrapper());
    }

    private void loadRegistries() {
        Registry.REQUIREMENT_TYPE.register(
            new BiomeRequirementType(),
            new DisabledRequirementType(),
            new GroupRequirementType(),
            new PermissionRequirementType(),
            new PlaceholderRequirementType(),
            new RewardTypeRequirementType(),
            new WeatherRequirementType(),
            new WorldRequirementType()
        );
        Registry.REWARD_TYPE.register(
            new ActionBarRewardType(),
            new MessageRewardType(),
            new SubtitleRewardType(),
            new TitleRewardType(),
            new CommandRewardType(),
            new EffectRewardType(),
            new EXPRewardType(),
            new HealthRewardType(),
            new HungerRewardType(),
            new ItemRewardType(),
            new MoneyRewardType(),
            new PermissionRewardType(),
            new SoundRewardType()
        );
        Registry.CUSTOM_ITEM.register(
            new DenizenCustomItem(),
            new NexoCustomItem()
        );
    }

}
