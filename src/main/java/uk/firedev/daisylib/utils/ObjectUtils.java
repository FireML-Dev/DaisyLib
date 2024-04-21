package uk.firedev.daisylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.List;

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

    public static NamespacedKey createNamespacedKey(String string, JavaPlugin plugin) {
        if (plugin == null) {
            return new NamespacedKey(DaisyLib.getInstance(), string);
        }
        return new NamespacedKey(plugin, string);
    }

    public static boolean isInt(String str) {
        try {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static <T> T getOrDefault(T[] array, int index, T def) {
        try {
            return array[index];
        } catch (IndexOutOfBoundsException ex) {
            return def;
        }
    }

    public static <T> T getOrDefault(List<T> list, int index, T def) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ex) {
            return def;
        }
    }

}
