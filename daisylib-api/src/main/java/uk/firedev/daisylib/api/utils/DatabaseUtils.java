package uk.firedev.daisylib.api.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.Loggers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Turns many different types of data into a JSON string for database storage.
 */
public class DatabaseUtils {

    private static final Gson gson = new Gson();

    /**
     * Turns a list into a JSON String
     * @param list The list to turn into a JSON String
     */
    public static @NotNull String prepareList(@NotNull List<?> list) {
        return gson.toJson(list);
    }

    /**
     * Parses a list from a JSON String
     * @param json The JSON String to parse
     * @param clazz The class of the list
     * @return The parsed list, or an empty list if the JSON is invalid
     */
    public static @NotNull <T> List<T> parseList(@NotNull String json, @NotNull Class<T> clazz) {
        if (json.isEmpty()) {
            return List.of();
        }
        try {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(json, listType);
        } catch (JsonParseException exception) {
            Loggers.warn(DatabaseUtils.class, "Invalid JSON!", exception);
            return List.of();
        }
    }

    /**
     * Turns a map into a JSON String
     * @param map The map to turn into a JSON String
     */
    public static @NotNull String prepareMap(@NotNull Map<?, ?> map) {
        return gson.toJson(map);
    }

    /**
     * Parses a map from a JSON String
     * @param json The JSON String to parse
     * @param keyClazz The class of the map's key
     * @param valueClazz The class of the map's value
     * @return The parsed map, or an empty map if the JSON is invalid
     */
    public static @NotNull <K, V> Map<K, V> parseMap(@NotNull String json, @NotNull Class<K> keyClazz, @NotNull Class<V> valueClazz) {
        if (json.isEmpty()) {
            return Map.of();
        }
        try {
            Type listType = TypeToken.getParameterized(Map.class, keyClazz, valueClazz).getType();
            return gson.fromJson(json, listType);
        } catch (JsonParseException exception) {
            Loggers.warn(DatabaseUtils.class, "Invalid JSON!", exception);
            return Map.of();
        }
    }

    /**
     * Turns a location into a JSON String
     * @param list The location to turn into a JSON String
     */
    public static @NotNull String prepareLocation(@NotNull Location location) {
        return prepareMap(location.serialize());
    }

    /**
     * Parses a location from a JSON String
     * @param json The JSON String to parse
     * @return The parsed location, or null if the JSON is invalid
     */
    public static @Nullable Location parseLocation(@NotNull String json) {
        Map<String, Object> serialized = parseMap(json, String.class, Object.class);
        if (serialized.isEmpty()) {
            return null;
        }
        return Location.deserialize(serialized);
    }

    /**
     * Turns a chunk into a JSON String
     * @param list The location to turn into a JSON String
     */
    public static @NotNull String prepareChunk(@NotNull Chunk chunk) {
        Map<String, String> serialized = Map.of(
                "world", chunk.getWorld().getName(),
                "x", Integer.toString(chunk.getX()),
                "z", Integer.toString(chunk.getZ())
        );
        return prepareMap(serialized);
    }

    /**
     * Parses a chunk from a JSON String
     * @param json The JSON String to parse
     * @return The parsed chunk, or null if the JSON, World, or coordinates are invalid
     */
    public static @Nullable Chunk parseChunk(@NotNull String json) {
        Map<String, String> serialized = parseMap(json, String.class, String.class);
        if (serialized.isEmpty()) {
            return null;
        }
        String worldName = serialized.get("world");
        if (worldName == null) {
            return null;
        }
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            Loggers.warn(DatabaseUtils.class, "World " + worldName + " does not exist!");
            return null;
        }
        int x;
        int z;
        try {
            x = Integer.parseInt(serialized.get("x"));
            z = Integer.parseInt(serialized.get("z"));
        } catch (NumberFormatException exception) {
            Loggers.warn(DatabaseUtils.class, "Invalid chunk coordinates!", exception);
            return null;
        }
        return world.getChunkAt(x, z);
    }

}
