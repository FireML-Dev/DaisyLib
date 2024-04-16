package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BossBarBuilder {

    private Component title = Component.empty();
    private BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;
    private float progress = BossBar.MAX_PROGRESS;
    private BossBar.Color color = BossBar.Color.WHITE;
    private Set<BossBar.Flag> flags = new HashSet<>();

    public BossBarBuilder withTitle(@NotNull Component title, String... replacements) {
        this.title = ComponentUtils.parsePlaceholders(title, replacements);
        return this;
    }

    public BossBarBuilder withTitle(@NotNull Component title, Map<String, Component> replacements) {
        this.title = ComponentUtils.parsePlaceholders(title, replacements);
        return this;
    }

    public BossBarBuilder withStringTitle(@NotNull String title, String... replacements) {
        this.title = ComponentUtils.deserializeString(title, replacements);
        return this;
    }

    public BossBarBuilder withStringTitle(@NotNull String title, Map<String, Component> replacements) {
        this.title = ComponentUtils.deserializeString(title, replacements);
        return this;
    }

    public BossBarBuilder withOverlay(@NotNull BossBar.Overlay overlay) {
        this.overlay = overlay;
        return this;
    }

    /**
     * Requires a value between 0 and 1
     */
    public BossBarBuilder withProgress(float progress) {
        if (progress > 1) {
            progress = 1;
        } else if (progress < 0) {
            progress = 0;
        }
        this.progress = progress;
        return this;
    }

    public BossBarBuilder withColor(@NotNull BossBar.Color color) {
        this.color = color;
        return this;
    }

    public BossBarBuilder clearFlags() {
        this.flags.clear();
        return this;
    }

    public BossBarBuilder withFlags(@NotNull Set<BossBar.Flag> flags) {
        this.flags = flags;
        return this;
    }

    public BossBarBuilder addFlag(@NotNull BossBar.Flag flag) {
        this.flags.add(flag);
        return this;
    }

    public BossBarBuilder removeFlag(@NotNull BossBar.Flag flag) {
        this.flags.remove(flag);
        return this;
    }

    public void sendAll(String... replacements) { Bukkit.getOnlinePlayers().forEach(player -> send(player, replacements)); }

    public void send(Audience audience, String... replacements) {
        audience.showBossBar(build(replacements));
    }

    public void sendAll(Map<String, Component> replacements) { Bukkit.getOnlinePlayers().forEach(player -> send(player, replacements)); }

    public void send(Audience audience, Map<String, Component> replacements) {
        audience.showBossBar(build(replacements));
    }

    public BossBar build(String... replacements) {
        Component title = ComponentUtils.parsePlaceholders(this.title, replacements);
        return BossBar.bossBar(title, progress, color, overlay, flags);
    }

    public BossBar build(Map<String, Component> replacements) {
        Component title = ComponentUtils.parsePlaceholders(this.title, replacements);
        return BossBar.bossBar(title, progress, color, overlay, flags);
    }
    
}
