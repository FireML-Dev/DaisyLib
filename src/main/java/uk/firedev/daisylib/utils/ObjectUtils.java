package uk.firedev.daisylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import uk.firedev.daisylib.local.DaisyLib;

public class ObjectUtils {

    public static String locationToString(Location location, boolean yawpitch) {
        String finalString = location.getWorld().getName() + "_" + location.getX() + "_" + location.getY() + "_" + location.getZ();
        if (yawpitch) {
            finalString += "_" + location.getYaw() + "_" + location.getPitch();
        }
        return finalString;
    }

    public static Location locationFromString(String s) {
        String[] stringloc = s.split("_");
        World world = Bukkit.getWorld(stringloc[0]);
        double x = Double.parseDouble(stringloc[1]);
        double y = Double.parseDouble(stringloc[2]);
        double z = Double.parseDouble(stringloc[3]);
        float yaw = 180;
        float pitch = 0;
        if (stringloc[4] != null) {
            yaw = Float.parseFloat(stringloc[4]);
        }
        if (stringloc[5] != null) {
            pitch = Float.parseFloat(stringloc[5]);
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static NamespacedKey createNamespacedKey(String string) {
        return new NamespacedKey(DaisyLib.getInstance(), string);
    }

}
