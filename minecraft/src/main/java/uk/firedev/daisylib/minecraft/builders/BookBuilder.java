package uk.firedev.daisylib.minecraft.builders;

import me.clip.placeholderapi.replacer.Replacer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.List;

public class BookBuilder {

    private final Book.Builder book;

    private BookBuilder(Book.Builder builder) {
        this.book = builder;
    }

    public static @NonNull BookBuilder bookBuilder() {
        return new BookBuilder(Book.builder());
    }

    public BookBuilder withTitle(@NonNull Component title) {
        this.book.title(title);
        return this;
    }

    public BookBuilder withAuthor(@NonNull Component author) {
        this.book.author(author);
        return this;
    }

    public BookBuilder addPage(@NonNull Component page) {
        this.book.addPage(page);
        return this;
    }

    public BookBuilder addPages(@NonNull List<Component> pages) {
        pages.forEach(this.book::addPage);
        return this;
    }

    public @NonNull Book build() {
        return this.book.build();
    }

    public void showAll() {
        show(Bukkit.getOnlinePlayers());
    }

    public void show(@NonNull Audience audience) {
        audience.openBook(build());
    }

    public void show(@NonNull Collection<? extends Audience> audiences) {
        Audience.audience(audiences).openBook(build());
    }

}
