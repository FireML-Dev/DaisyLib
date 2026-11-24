package uk.firedev.daisylib.requirement.types;

import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.requirement.RequirementContext;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.utils.CommonUtils;

import java.util.List;

public class WeatherRequirementType extends RequirementType {

    @Override
    public boolean check(@NonNull RequirementContext context, @NonNull List<String> values) {
        World world = context.world();
        if (world == null) {
            return false;
        }
        boolean isClear = world.isClearWeather();
        for (String value : values) {
            WeatherType weatherType = CommonUtils.getEnumValue(WeatherType.class, value);
            if (weatherType == null) {
                DaisyLib.get().getLogging().warn(value + " is not a valid weather type.");
                return false;
            }
            if (isClear ? weatherType.equals(WeatherType.CLEAR) : weatherType.equals(WeatherType.DOWNFALL)) {
                debugLogStatus(true);
                return true;
            }
        }
        debugLogStatus(false);
        return false;
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Weather";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return true;
    }

}
