package uk.firedev.daisylib.builders;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.time.Duration;
import java.util.Map;

public class TitleBuilder {

    private int in = 20;
    private int stay = 60;
    private int out = 20;
    private Component title = null;
    private Component subtitle = null;

    public TitleBuilder withTimings(int in, int stay, int out) { return withIn(in).withStay(stay).withOut(out); }

    public TitleBuilder withIn(int in) {
        this.in = in;
        return this;
    }

    public TitleBuilder withStay(int stay) {
        this.stay = stay;
        return this;
    }

    public TitleBuilder withOut(int out) {
        this.out = out;
        return this;
    }

    public TitleBuilder withTitle(Component title) {
        this.title = title;
        return this;
    }

    public TitleBuilder withStringTitle(String title) {
        this.title = ComponentUtils.parseComponent(title);
        return this;
    }

    public TitleBuilder withSubtitle(Component subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public TitleBuilder withStringSubtitle(String subtitle) {
        this.subtitle = ComponentUtils.parseComponent(subtitle);
        return this;
    }

    public void sendAll(String... replacements) { Bukkit.getOnlinePlayers().forEach(player -> send(player, replacements)); }

    public void send(Player p, String... replacements) {
        if (this.title != null && this.subtitle != null) {
            Component title = ComponentUtils.parsePlaceholders(this.title, replacements);
            Component subtitle = ComponentUtils.parsePlaceholders(this.subtitle, replacements);
            Title.Times times = Title.Times.times(Duration.ofSeconds(in / 20), Duration.ofSeconds(stay / 20), Duration.ofSeconds(out / 20));
            p.showTitle(Title.title(title, subtitle, times));
        }
    }

    public void sendAll(Map<String, Component> replacements) { Bukkit.getOnlinePlayers().forEach(player -> send(player, replacements)); }

    public void send(Player p, Map<String, Component> replacements) {
        if (title != null && subtitle != null) {
            Component title = ComponentUtils.parsePlaceholders(this.title, replacements);
            Component subtitle = ComponentUtils.parsePlaceholders(this.subtitle, replacements);
            Title.Times times = Title.Times.times(Duration.ofSeconds(in / 20), Duration.ofSeconds(stay / 20), Duration.ofSeconds(out / 20));
            p.showTitle(Title.title(title, subtitle, times));
        }
    }

}
