package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;
import uk.firedev.daisylib.message.string.StringReplacer;

import java.util.HashSet;
import java.util.Set;

public class BossBarBuilder {

    private Component title = Component.empty();
    private BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;
    private float progress = BossBar.MAX_PROGRESS;
    private BossBar.Color color = BossBar.Color.WHITE;
    private Set<BossBar.Flag> flags = new HashSet<>();

    public BossBarBuilder withTitle(@NotNull Component title, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            title = replacer.replace(title);
        }
        this.title = title;
        return this;
    }

    public BossBarBuilder withStringTitle(@NotNull String title, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            title = replacer.replace(title);
        }
        this.title = ComponentMessage.fromString(title).getMessage();
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

    public void sendAll() { Audience.audience(Bukkit.getOnlinePlayers()).showBossBar(build()); }

    public void send(Player player) {
        player.showBossBar(build());
    }

    public BossBar build() {
        return BossBar.bossBar(title, progress, color, overlay, flags);
    }
    
}
