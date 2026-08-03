package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.jspecify.annotations.NonNull;

import java.time.Duration;
import java.util.Collection;

public class TitleBuilder {

    private int in = 20;
    private int stay = 60;
    private int out = 20;
    private Component title = Component.empty();
    private Component subtitle = Component.empty();

    private TitleBuilder() {}

    public static TitleBuilder create() { return new TitleBuilder(); }

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

    public TitleBuilder withTitle(@NonNull Component title) {
        this.title = title;
        return this;
    }

    public TitleBuilder withSubtitle(@NonNull Component subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public Title build() {
        Title.Times times = Title.Times.times(Duration.ofSeconds(in / 20), Duration.ofSeconds(stay / 20), Duration.ofSeconds(out / 20));
        return Title.title(title, subtitle, times);
    }

    public void sendAll() {
        send(Bukkit.getOnlinePlayers());
    }

    public void send(@NonNull Audience audience) {
        audience.showTitle(build());
    }

    public void send(@NonNull Collection<? extends Audience> audiences) {
        Audience.audience(audiences).showTitle(build());
    }

}
