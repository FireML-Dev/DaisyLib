package uk.firedev.daisylib.builders;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.time.Duration;
import java.util.Map;

public class TitleBuilder {

    private int in = 20;
    private int stay = 60;
    private int out = 20;
    private Component title = Component.empty();
    private Component subtitle = Component.empty();

    public TitleBuilder withTimings(int inTicks, int stayTicks, int outTicks) { return withIn(inTicks).withStay(stayTicks).withOut(outTicks); }

    public TitleBuilder withTimingsSeconds(int inSeconds, int staySeconds, int outSeconds) { return withInSeconds(inSeconds).withStaySeconds(staySeconds).withOutSeconds(outSeconds); }

    public TitleBuilder withIn(int inTicks) {
        if (inTicks <= 0) {
            return this;
        }
        this.in = inTicks;
        return this;
    }

    public TitleBuilder withInSeconds(int inSeconds) {
        if (inSeconds <= 0) {
            return this;
        }
        this.in = inSeconds * 20;
        return this;
    }

    public TitleBuilder withStay(int stayTicks) {
        if (stayTicks <= 0) {
            return this;
        }
        this.stay = stayTicks;
        return this;
    }

    public TitleBuilder withStaySeconds(int staySeconds) {
        if (staySeconds <= 0) {
            return this;
        }
        this.stay = staySeconds * 20;
        return this;
    }

    public TitleBuilder withOut(int outTicks) {
        if (outTicks <= 0) {
            return this;
        }
        this.out = outTicks;
        return this;
    }

    public TitleBuilder withOutSeconds(int outSeconds) {
        if (outSeconds <= 0) {
            return this;
        }
        this.stay = outSeconds * 20;
        return this;
    }

    public TitleBuilder withTitle(@NotNull Component title) {
        this.title = title;
        return this;
    }

    public TitleBuilder withStringTitle(@NotNull String title) {
        this.title = ComponentUtils.parseComponent(title);
        return this;
    }

    public TitleBuilder withSubtitle(@NotNull Component subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public TitleBuilder withStringSubtitle(@NotNull String subtitle) {
        this.subtitle = ComponentUtils.parseComponent(subtitle);
        return this;
    }

    public void sendAll(String... replacements) { Bukkit.getOnlinePlayers().forEach(player -> send(player, replacements)); }

    public void send(Player p, String... replacements) {
        Component title = ComponentUtils.parsePlaceholders(this.title, replacements);
        Component subtitle = ComponentUtils.parsePlaceholders(this.subtitle, replacements);
        Title.Times times = Title.Times.times(Duration.ofSeconds(in / 20), Duration.ofSeconds(stay / 20), Duration.ofSeconds(out / 20));
        p.showTitle(Title.title(title, subtitle, times));
    }

    public void sendAll(Map<String, Component> replacements) { Bukkit.getOnlinePlayers().forEach(player -> send(player, replacements)); }

    public void send(Player p, Map<String, Component> replacements) {
        Component title = ComponentUtils.parsePlaceholders(this.title, replacements);
        Component subtitle = ComponentUtils.parsePlaceholders(this.subtitle, replacements);
        Title.Times times = Title.Times.times(Duration.ofSeconds(in / 20), Duration.ofSeconds(stay / 20), Duration.ofSeconds(out / 20));
        p.showTitle(Title.title(title, subtitle, times));
    }

}
