package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.util.List;
import java.util.Map;

public class TabListBuilder {

    private Component header = null;

    private Component footer = null;

    public TabListBuilder withHeader(@NotNull Component header, String... replacements) {
        this.header = ComponentUtils.parsePlaceholders(header, replacements);
        return this;
    }

    public TabListBuilder withHeader(@NotNull Component header, Map<String, Component> replacements) {
        this.header = ComponentUtils.parsePlaceholders(header, replacements);
        return this;
    }

    public TabListBuilder withStringHeader(@NotNull String header, String... replacements) {
        this.header = ComponentUtils.parseComponent(header, replacements);
        return this;
    }

    public TabListBuilder withStringHeader(@NotNull String header, Map<String, Component> replacements) {
        this.header = ComponentUtils.parseComponent(header, replacements);
        return this;
    }

    public TabListBuilder withFooter(@NotNull Component footer, String... replacements) {
        this.footer = ComponentUtils.parsePlaceholders(footer, replacements);
        return this;
    }

    public TabListBuilder withFooter(@NotNull Component footer, Map<String, Component> replacements) {
        this.footer = ComponentUtils.parsePlaceholders(footer, replacements);
        return this;
    }

    public TabListBuilder withStringFooter(@NotNull String footer, String... replacements) {
        this.footer = ComponentUtils.parseComponent(footer, replacements);
        return this;
    }

    public TabListBuilder withStringFooter(@NotNull String footer, Map<String, Component> replacements) {
        this.footer = ComponentUtils.parseComponent(footer, replacements);
        return this;
    }

    public TabListBuilder withHeaderFooter(@NotNull Component header, @NotNull Component footer, String... replacements) {
        return withHeader(header, replacements).withFooter(footer, replacements);
    }

    public TabListBuilder withHeaderFooter(@NotNull Component header, @NotNull Component footer, Map<String, Component> replacements) {
        return withHeader(header, replacements).withFooter(footer, replacements);
    }

    public TabListBuilder withStringHeaderFooter(@NotNull String header, @NotNull String footer, String... replacements) {
        return withStringHeader(header, replacements).withStringFooter(footer, replacements);
    }

    public TabListBuilder withStringHeaderFooter(@NotNull String header, @NotNull String footer, Map<String, Component> replacements) {
        return withStringHeader(header, replacements).withStringFooter(footer, replacements);
    }

    public void sendAll() { Bukkit.getOnlinePlayers().forEach(this::send); }

    public void send(List<Audience> audiences) { audiences.forEach(this::send); }

    public void send(Audience audience) { audience.sendPlayerListHeaderAndFooter(this.header, this.footer); }

}
