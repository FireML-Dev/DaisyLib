package uk.firedev.daisylib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.daisylib.local.DaisyLib;

import java.util.List;

public class ObjectUtils {

    public static String locationToString(Location location, boolean includeYawPitch) {
        String finalString = location.getWorld().getName() + "_" + location.getX() + "_" + location.getY() + "_" + location.getZ();
        if (includeYawPitch) {
            finalString += "_" + location.getYaw() + "_" + location.getPitch();
        }
        return finalString;
    }

    public static Location locationFromString(String s) {
        String[] stringloc = s.split("_");
        if (stringloc.length < 4) {
            return null;
        }
        World world = Bukkit.getWorld(stringloc[0]);
        if (world == null) {
            return null;
        }
        double x = Double.parseDouble(stringloc[1]);
        double y = Double.parseDouble(stringloc[2]);
        double z = Double.parseDouble(stringloc[3]);
        float yaw = Float.parseFloat(getOrDefault(stringloc, 4, String.valueOf(180)));
        float pitch = Float.parseFloat(getOrDefault(stringloc, 4, String.valueOf(0)));
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

    @SuppressWarnings("UnreachableCode")
    public static <T> T getOrDefault(T[] array, int index, T def) {
        try {
            return array[index];
        } catch (ArrayIndexOutOfBoundsException ex) {
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
