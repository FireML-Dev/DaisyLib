package uk.firedev.daisylib.external.luckperms;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.query.QueryOptions;
import org.jspecify.annotations.NullMarked;
import uk.firedev.daisylib.utils.CommonUtils;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@NullMarked
public class LuckPermsWrapper {

    public static final boolean LP_AVAILABLE = CommonUtils.classExists("net.luckperms.api.LuckPerms");

    /// Checks if a player has the given permission. If the player is offline, their data will be loaded on the current thread.
    /// @return Whether the player has the given permission.
    public static boolean hasPermission(UUID player, String permission) {
        if (!LP_AVAILABLE) {
            return false;
        }
        return getOrLoadUser(player).getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    /// Checks if a player has all the given permissions. If the player is offline, their data will be loaded on the current thread.
    /// @return Whether the player has all the given permissions.
    public static boolean hasPermissions(UUID player, Collection<String> permissions) {
        if (!LP_AVAILABLE) {
            return false;
        }
        CachedPermissionData data = getOrLoadUser(player).getCachedData().getPermissionData();
        for (String permission : permissions) {
            if (!data.checkPermission(permission).asBoolean()) {
                return false;
            }
        }
        return true;
    }
    
    /// Checks if a player is inside the given group. If the player is offline, their data will be loaded on the current thread.
    /// @return Whether the player is inside the given group.
    public static boolean isInGroup(UUID player, String group) {
        if (!LP_AVAILABLE) {
            return false;
        }
        return getOrLoadUser(player).getInheritedGroups(QueryOptions.nonContextual()).stream()
            .map(Group::getName)
            .anyMatch(name -> name.equalsIgnoreCase(group));
    }

    /// Checks if a player is inside all the given groups. If the player is offline, their data will be loaded on the current thread.
    /// @return Whether the player is inside all the given groups.
    public static boolean isInGroups(UUID player, Collection<String> groups) {
        if (!LP_AVAILABLE) {
            return false;
        }
        Set<String> userGroups = getOrLoadUser(player).getInheritedGroups(QueryOptions.nonContextual()).stream()
            .map(g -> g.getName().toLowerCase(Locale.ROOT))
            .collect(Collectors.toSet());
        for (String group : groups) {
            if (!userGroups.contains(group.toLowerCase(Locale.ROOT))) {
                return false;
            }
        }
        return true;
    }

    private static User getOrLoadUser(UUID uuid) {
        UserManager manager = LuckPermsProvider.get().getUserManager();
        User user = manager.getUser(uuid);
        if (user != null) {
            return user;
        }
        try {
            return manager.loadUser(uuid).join(); // I don't like loading on the main thread, but we have no choice here.
        } catch (CancellationException | CompletionException exception) {
            throw new IllegalStateException("Failed to load LuckPerms user " + uuid, exception);
        }
    }

}
