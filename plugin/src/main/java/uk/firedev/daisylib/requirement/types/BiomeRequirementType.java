package uk.firedev.daisylib.requirement.types;

import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.requirement.RequirementContext;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.utils.CommonUtils;

import java.util.List;

public class BiomeRequirementType extends RequirementType {

    @Override
    public boolean check(@NonNull RequirementContext context, @NonNull List<String> values) {
        World world = context.world();
        Location location = context.location();
        if (world == null) {
            return false;
        }
        if (location == null) {
            return false;
        }
        Biome locationBiome = location.getBlock().getBiome();
        for (String value : values) {
            Biome biome = CommonUtils.getFromRegistry(value, RegistryKey.BIOME);
            if (biome == null) {
                continue;
            }
            if (locationBiome.equals(biome)) {
                debugLogStatus(true);
                return true;
            }
        }
        debugLogStatus(false);
        return false;
    }

    @Override
    public @NonNull String getKey() {
        return "Biome";
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
