package uk.firedev.daisylib.api.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.api.message.string.StringReplacer;

import java.time.Duration;

public class TitleBuilder {

    private int in = 20;
    private int stay = 60;
    private int out = 20;
    private Component title = Component.empty();
    private Component subtitle = Component.empty();

    private TitleBuilder() {}

    public static TitleBuilder titleBuilder() { return new TitleBuilder(); }

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

    public TitleBuilder withTitle(@NotNull Component title, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            title = replacer.replace(title);
        }
        this.title = title;
        return this;
    }

    public TitleBuilder withStringTitle(@NotNull String title, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            title = replacer.replace(title);
        }
        this.title = ComponentMessage.fromString(title).getMessage();
        return this;
    }

    public TitleBuilder withSubtitle(@NotNull Component subtitle, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            subtitle = replacer.replace(subtitle);
        }
        this.subtitle = subtitle;
        return this;
    }

    public TitleBuilder withStringSubtitle(@NotNull String subtitle, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            subtitle = replacer.replace(subtitle);
        }
        this.subtitle = ComponentMessage.fromString(subtitle).getMessage();
        return this;
    }

    public Title build() {
        Title.Times times = Title.Times.times(Duration.ofSeconds(in / 20), Duration.ofSeconds(stay / 20), Duration.ofSeconds(out / 20));
        return Title.title(title, subtitle, times);
    }

    public void sendAll() { Audience.audience(Bukkit.getOnlinePlayers()).showTitle(build()); }

    public void send(Player player) {
        player.showTitle(build());
    }

}
