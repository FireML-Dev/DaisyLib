package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.util.List;

public class TabListBuilder {

    private Component header = null;

    private Component footer = null;

    public TabListBuilder withHeader(Component header) {
        this.header = header;
        return this;
    }

    public TabListBuilder withStringHeader(String header) {
        this.header = ComponentUtils.parseComponent(header);
        return this;
    }

    public TabListBuilder withFooter(Component footer) {
        this.footer = footer;
        return this;
    }

    public TabListBuilder withStringFooter(String footer) {
        this.footer = ComponentUtils.parseComponent(footer);
        return this;
    }

    public TabListBuilder withHeaderFooter(Component header, Component footer) { return withHeader(header).withFooter(footer); }

    public TabListBuilder withStringHeaderFooter(String header, String footer) { return withStringHeader(header).withStringFooter(footer); }

    public void sendAll() { Bukkit.getOnlinePlayers().forEach(this::send); }

    public void send(List<Audience> audiences) { audiences.forEach(this::send); }

    public void send(Audience audience) { audience.sendPlayerListHeaderAndFooter(this.header, this.footer); }

}
