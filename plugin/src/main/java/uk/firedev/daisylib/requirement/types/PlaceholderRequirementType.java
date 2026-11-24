package uk.firedev.daisylib.requirement.types;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.DaisyLibPlugin;
import uk.firedev.daisylib.requirement.RequirementContext;
import uk.firedev.daisylib.requirement.RequirementType;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderRequirementType extends RequirementType {

    private final Pattern pattern = Pattern.compile("^(.+?)(>=|<=|==|=|!=|<|>)(.+)$");

    @Override
    public boolean check(@NonNull RequirementContext requirementContext, @NonNull List<String> values) {
        OfflinePlayer player = requirementContext.player();
        for (String value : values) {
            Matcher matcher = this.pattern.matcher(value);
            if (!matcher.matches()) continue;
            String placeholder = PlaceholderAPI.setPlaceholders(player, matcher.group(1)).trim();
            String operator = matcher.group(2);
            String requiredValue = matcher.group(3).trim();
            boolean pass = evaluate(placeholder, operator, requiredValue);
            if (!pass) {
                debugLogStatus(false);
                return false;
            }
        }
        debugLogStatus(true);
        return true;
    }

    @NonNull
    @Override
    public String getIdentifier() {
        return "Placeholder";
    }

    @NonNull
    @Override
    public String getAuthor() {
        return "KitterKatter";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return DaisyLibPlugin.get();
    }

    @Override
    public boolean checkDependencies() {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    private boolean evaluate(String placeholder, String operator, String requiredValue) {
        try {
            double left = Double.parseDouble(placeholder);
            double right = Double.parseDouble(requiredValue);

            return switch (operator) {
                case ">=" -> left >= right;
                case "<=" -> left <= right;
                case ">"  -> left > right;
                case "<"  -> left < right;
                case "=", "==" -> Double.compare(left, right) == 0;
                case "!=" -> Double.compare(left, right) != 0;
                default -> false;
            };
        } catch (NumberFormatException e) {
            return switch (operator) {
                case "=", "==" -> placeholder.equalsIgnoreCase(requiredValue);
                case "!=" -> !placeholder.equalsIgnoreCase(requiredValue);
                default -> false;
            };
        }
    }
}
