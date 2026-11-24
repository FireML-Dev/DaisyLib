package uk.firedev.daisylib.registry;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;
import uk.firedev.daisylib.requirement.RequirementType;
import uk.firedev.daisylib.reward.RewardType;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

@NullMarked
@ApiStatus.Internal
public class Registry<T extends RegistryItem> implements Iterable<T> {

    public static Registry<RewardType> REWARD_TYPE = new Registry<>();
    public static Registry<RequirementType> REQUIREMENT_TYPE = new Registry<>();

    private final Map<String, T> items = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private Registry() {}

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    @SafeVarargs
    public final void register(T... items) {
        for (T item : items) {
            register(item);
        }
    }

    public boolean register(T item) {
        return register(item, false);
    }

    public boolean register(T item, boolean force) {
        if (!force && items.containsKey(item.getKey())) {
            return false;
        }
        if (!item.checkDependencies()) {
            DaisyLib.get().getLogging().debug(item.getClass().getSimpleName() + " failed dependency check. Cannot register.");
            return false;
        }
        items.put(item.getKey(), item);
        DaisyLib.get().getLogging().info("Registered " + item.getClass().getSimpleName() + " to registry.");
        return true;
    }

    public boolean unregister(String key) {
        return items.remove(key) != null;
    }

    // Getters

    public Map<String, T> getSnapshot() {
        return Map.copyOf(items);
    }

    public List<T> getAll() {
        return List.copyOf(items.values());
    }

    public @Nullable T get(String key) {
        return items.get(key);
    }

    public T getOrThrow(String key) {
        return getOrThrow(key, "Could not find " + key + " in registry.");
    }

    public T getOrThrow(String key, String errorMessage) {
        T item = items.get(key);
        if (item == null) {
            throw new RuntimeException(errorMessage);
        }
        return item;
    }

    // Iterable & Stream

    @Override
    public Iterator<T> iterator() {
        return items.values().iterator();
    }

    public Stream<T> stream() {
        return items.values().stream();
    }

}
