package uk.firedev.daisylib.builders;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookBuilder {

    private Component title = null;
    private Component author = null;
    private List<Component> pages = new ArrayList<>();

    public BookBuilder withTitle(@NotNull Component title, String... replacements) {
        this.title = ComponentUtils.parsePlaceholders(title, replacements);
        return this;
    }

    public BookBuilder withTitle(@NotNull Component title, Map<String, Component> replacements) {
        this.title = ComponentUtils.parsePlaceholders(title, replacements);
        return this;
    }

    public BookBuilder withStringTitle(@NotNull String title, String... replacements) {
        this.title = ComponentUtils.parseComponent(title, replacements);
        return this;
    }

    public BookBuilder withStringTitle(@NotNull String title, Map<String, Component> replacements) {
        this.title = ComponentUtils.parseComponent(title, replacements);
        return this;
    }

    public BookBuilder withAuthor(@NotNull Component author) {
        this.author = author;
        return this;
    }

    public BookBuilder withStringAuthor(@NotNull String author) {
        this.author = ComponentUtils.parseComponent(author);
        return this;
    }

    public BookBuilder withPages(@NotNull List<Component> pages, String... replacements) {
        this.pages = ComponentUtils.parsePlaceholders(pages, replacements);
        return this;
    }

    public BookBuilder withPages(@NotNull List<Component> pages, Map<String, Component> replacements) {
        this.pages = ComponentUtils.parsePlaceholders(pages, replacements);
        return this;
    }

    public BookBuilder withStringPages(@NotNull List<String> pages, String... replacements) {
        this.pages = ComponentUtils.parseComponentList(pages, replacements);
        return this;
    }

    public BookBuilder withStringPages(@NotNull List<String> pages, Map<String, Component> replacements) {
        this.pages = ComponentUtils.parseComponentList(pages, replacements);
        return this;
    }

    public BookBuilder addPage(@NotNull Component page, String... replacements) {
        this.pages.add(ComponentUtils.parsePlaceholders(page, replacements));
        return this;
    }

    public BookBuilder addPage(@NotNull Component page, Map<String, Component> replacements) {
        this.pages.add(ComponentUtils.parsePlaceholders(page, replacements));
        return this;
    }

    public BookBuilder addStringPage(@NotNull String page, String... replacements) {
        this.pages.add(ComponentUtils.parseComponent(page, replacements));
        return this;
    }

    public BookBuilder addStringPage(@NotNull String page, Map<String, Component> replacements) {
        this.pages.add(ComponentUtils.parseComponent(page, replacements));
        return this;
    }

    public BookBuilder addPages(@NotNull List<Component> pages, String... replacements) {
        this.pages.addAll(ComponentUtils.parsePlaceholders(pages, replacements));
        return this;
    }

    public BookBuilder addPages(@NotNull List<Component> pages, Map<String, Component> replacements) {
        this.pages.addAll(ComponentUtils.parsePlaceholders(pages, replacements));
        return this;
    }

    public BookBuilder addStringPages(@NotNull List<String> pages, String... replacements) {
        this.pages.addAll(ComponentUtils.parseComponentList(pages, replacements));
        return this;
    }

    public BookBuilder addStringPages(@NotNull List<String> pages, Map<String, Component> replacements) {
        this.pages.addAll(ComponentUtils.parseComponentList(pages, replacements));
        return this;
    }

    public Book build() { return Book.book(this.title, this.author, this.pages); }

    public void showAll() { Bukkit.getOnlinePlayers().forEach(this::show); }

    public void show(Player player) { player.openBook(build()); }

    public void show(List<Player> players) { players.forEach(this::show); }

}
