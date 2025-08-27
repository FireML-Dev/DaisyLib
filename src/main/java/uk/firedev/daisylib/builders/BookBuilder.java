package uk.firedev.daisylib.builders;

import uk.firedev.messagelib.message.ComponentListMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.message.ComponentMessage;

import java.util.List;

public class BookBuilder {

    private ComponentSingleMessage title = null;
    private ComponentSingleMessage author = null;
    private ComponentListMessage pages = null;

    private BookBuilder() {}

    public static BookBuilder create() {
        return new BookBuilder();
    }

    public BookBuilder withTitle(@NotNull Object title, @Nullable Replacer replacer) {
        this.title = ComponentMessage.componentMessage(title).replace(replacer);
        return this;
    }

    public BookBuilder withAuthor(@NotNull Object author) {
        this.author = ComponentMessage.componentMessage(author);
        return this;
    }

    public BookBuilder withPages(@NotNull List<?> pages, @Nullable Replacer replacer) {
        this.pages = ComponentMessage.componentMessage(pages).replace(replacer);
        return this;
    }

    public BookBuilder addPage(@NotNull Object page, @Nullable Replacer replacer) {
        this.pages.append(ComponentMessage.componentMessage(page).replace(replacer));
        return this;
    }

    public BookBuilder addPages(@NotNull List<?> pages, @Nullable Replacer replacer) {
        this.pages.append(ComponentMessage.componentMessage(pages).replace(replacer));
        return this;
    }

    public Book build() { return Book.book(this.title.get(), this.author.get(), this.pages.get()); }

    public void showAll() { Audience.audience(Bukkit.getOnlinePlayers()).openBook(build()); }

    public void show(Player player) { player.openBook(build()); }

    public void show(List<Player> players) { Audience.audience(players).openBook(build()); }

}
