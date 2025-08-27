package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;


import java.util.HashSet;
import java.util.Set;

public class BossBarBuilder {

    private ComponentSingleMessage title = ComponentMessage.componentMessage(Component.empty());
    private BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;
    private float progress = BossBar.MAX_PROGRESS;
    private BossBar.Color color = BossBar.Color.WHITE;
    private Set<BossBar.Flag> flags = new HashSet<>();

    private BossBarBuilder() {}

    public static BossBarBuilder create() {
        return new BossBarBuilder();
    }

    public BossBarBuilder withTitle(@NotNull Object title, @Nullable Replacer replacer) {
        this.title = ComponentMessage.componentMessage(title).replace(replacer);
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
        return BossBar.bossBar(title.get(), progress, color, overlay, flags);
    }
    
}
