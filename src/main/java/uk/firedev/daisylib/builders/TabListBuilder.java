package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.List;

public class TabListBuilder {

    private ComponentSingleMessage header = null;
    private ComponentSingleMessage footer = null;

    private TabListBuilder() {}

    public static TabListBuilder create() { return new TabListBuilder(); }

    public TabListBuilder withHeader(@NonNull Object header, @Nullable Replacer replacer) {
        this.header = ComponentMessage.componentMessage(header).replace(replacer);
        return this;
    }

    public TabListBuilder withFooter(@NonNull Object footer, @Nullable Replacer replacer) {
        this.footer = ComponentMessage.componentMessage(footer).replace(replacer);
        return this;
    }

    public TabListBuilder withHeaderFooter(@NonNull Object header, @NonNull Object footer, @Nullable Replacer replacer) {
        return withHeader(header, replacer).withFooter(footer, replacer);
    }

    public void sendAll() { Audience.audience(Bukkit.getOnlinePlayers()).sendPlayerListHeaderAndFooter(this.header.get(), this.footer.get()); }

    public void send(List<Player> players) { Audience.audience(players).sendPlayerListHeaderAndFooter(this.header.get(), this.footer.get()); }

    public void send(Audience audience) { audience.sendPlayerListHeaderAndFooter(this.header.get(), this.footer.get()); }

}
