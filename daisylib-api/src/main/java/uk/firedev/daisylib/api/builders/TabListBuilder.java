package uk.firedev.daisylib.api.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.api.message.component.ComponentMessage;
import uk.firedev.daisylib.api.message.component.ComponentReplacer;
import uk.firedev.daisylib.api.message.string.StringReplacer;

import java.util.List;

public class TabListBuilder {

    private Component header = null;

    private Component footer = null;

    private TabListBuilder() {}

    public static TabListBuilder create() { return new TabListBuilder(); }

    public TabListBuilder withHeader(@NotNull Component header, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            header = replacer.replace(header);
        }
        this.header = header;
        return this;
    }

    public TabListBuilder withStringHeader(@NotNull String header, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            header = replacer.replace(header);
        }
        this.header = ComponentMessage.fromString(header).getMessage();
        return this;
    }

    public TabListBuilder withFooter(@NotNull Component footer, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            footer = replacer.replace(footer);
        }
        this.footer = footer;
        return this;
    }

    public TabListBuilder withStringFooter(@NotNull String footer, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            footer = replacer.replace(footer);
        }
        this.footer = ComponentMessage.fromString(footer).getMessage();
        return this;
    }

    public TabListBuilder withHeaderFooter(@NotNull Component header, @NotNull Component footer, @Nullable ComponentReplacer replacer) {
        return withHeader(header, replacer).withFooter(footer, replacer);
    }

    public TabListBuilder withStringHeaderFooter(@NotNull String header, @NotNull String footer, @Nullable StringReplacer replacer) {
        return withStringHeader(header, replacer).withStringFooter(footer, replacer);
    }

    public void sendAll() { Audience.audience(Bukkit.getOnlinePlayers()).sendPlayerListHeaderAndFooter(this.header, this.footer); }

    public void send(List<Player> players) { Audience.audience(players).sendPlayerListHeaderAndFooter(this.header, this.footer); }

    public void send(Player player) { player.sendPlayerListHeaderAndFooter(this.header, this.footer); }

}
