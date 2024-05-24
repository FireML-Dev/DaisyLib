package uk.firedev.daisylib.builders;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;
import uk.firedev.daisylib.message.string.StringReplacer;

import java.util.ArrayList;
import java.util.List;
public class BookBuilder {

    private Component title = null;
    private Component author = null;
    private List<Component> pages = new ArrayList<>();

    public BookBuilder withTitle(@NotNull Component title, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            title = replacer.replace(title);
        }
        this.title = title;
        return this;
    }

    public BookBuilder withStringTitle(@NotNull String title, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            title = replacer.replace(title);
        }
        this.title = new ComponentMessage(title).getMessage();
        return this;
    }

    public BookBuilder withAuthor(@NotNull Component author) {
        this.author = author;
        return this;
    }

    public BookBuilder withStringAuthor(@NotNull String author) {
        this.author = new ComponentMessage(author).getMessage();
        return this;
    }

    public BookBuilder withPages(@NotNull List<Component> pages, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            pages = replacer.replace(pages);
        }
        this.pages = pages;
        return this;
    }

    public BookBuilder withStringPages(@NotNull List<String> pages, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            pages = replacer.replace(pages);
        }
        this.pages = pages.stream().map(page -> new ComponentMessage(page).getMessage()).toList();
        return this;
    }

    public BookBuilder addPage(@NotNull Component page, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            page = replacer.replace(page);
        }
        this.pages.add(page);
        return this;
    }

    public BookBuilder addStringPage(@NotNull String page, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            page = replacer.replace(page);
        }
        this.pages.add(new ComponentMessage(page).getMessage());
        return this;
    }

    public BookBuilder addPages(@NotNull List<Component> pages, @Nullable ComponentReplacer replacer) {
        if (replacer != null) {
            pages = replacer.replace(pages);
        }
        this.pages.addAll(pages.stream().map(page -> new ComponentMessage(page).getMessage()).toList());
        return this;
    }

    public BookBuilder addStringPages(@NotNull List<String> pages, @Nullable StringReplacer replacer) {
        if (replacer != null) {
            pages = replacer.replace(pages);
        }
        this.pages.addAll(pages.stream().map(page -> new ComponentMessage(page).getMessage()).toList());
        return this;
    }

    public Book build() { return Book.book(this.title, this.author, this.pages); }

    public void showAll() { Audience.audience(Bukkit.getOnlinePlayers()).openBook(build()); }

    public void show(Player player) { player.openBook(build()); }

    public void show(List<Player> players) { Audience.audience(players).openBook(build()); }

}
