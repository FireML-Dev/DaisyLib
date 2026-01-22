package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.time.Duration;

public class TitleBuilder {

    private int in = 20;
    private int stay = 60;
    private int out = 20;
    private ComponentSingleMessage title = ComponentSingleMessage.componentMessage(Component.empty());
    private ComponentSingleMessage subtitle = ComponentSingleMessage.componentMessage(Component.empty());

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

    public TitleBuilder withTitle(@NonNull Object title, @Nullable Replacer replacer) {
        this.title = ComponentMessage.componentMessage(title).replace(replacer);
        return this;
    }

    public TitleBuilder withSubtitle(@NonNull Object subtitle, @Nullable Replacer replacer) {
        this.subtitle = ComponentMessage.componentMessage(subtitle).replace(replacer);
        return this;
    }

    public Title build() {
        Title.Times times = Title.Times.times(Duration.ofSeconds(in / 20), Duration.ofSeconds(stay / 20), Duration.ofSeconds(out / 20));
        return Title.title(title.get(), subtitle.get(), times);
    }

    public void sendAll() { Audience.audience(Bukkit.getOnlinePlayers()).showTitle(build()); }

    public void send(Player player) {
        player.showTitle(build());
    }

}
