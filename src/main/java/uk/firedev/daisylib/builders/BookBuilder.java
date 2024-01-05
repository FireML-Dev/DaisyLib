package uk.firedev.daisylib.builders;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.firedev.daisylib.utils.ComponentUtils;

import java.util.ArrayList;
import java.util.List;

public class BookBuilder {

    private Component title = null;
    private Component author = null;
    private List<Component> pages = new ArrayList<>();

    public BookBuilder withTitle(Component title) {
        this.title = title;
        return this;
    }

    public BookBuilder withStringTitle(String title) {
        this.title = ComponentUtils.parseComponent(title);
        return this;
    }

    public BookBuilder withAuthor(Component author) {
        this.author = author;
        return this;
    }

    public BookBuilder withStringAuthor(String author) {
        this.author = ComponentUtils.parseComponent(author);
        return this;
    }

    public BookBuilder withPages(List<Component> pages) {
        this.pages = pages;
        return this;
    }

    public BookBuilder withStringPages(List<String> pages) {
        this.pages = ComponentUtils.parseComponentList(pages);
        return this;
    }

    public BookBuilder addPage(Component page) {
        this.pages.add(page);
        return this;
    }

    public BookBuilder addStringPage(String page) {
        this.pages.add(ComponentUtils.parseComponent(page));
        return this;
    }

    public BookBuilder addPages(List<Component> pages) {
        this.pages.addAll(pages);
        return this;
    }

    public BookBuilder addStringPages(List<String> pages) {
        this.pages.addAll(ComponentUtils.parseComponentList(pages));
        return this;
    }

    public Book build() { return Book.book(this.title, this.author, this.pages); }

    public void showAll() { Bukkit.getOnlinePlayers().forEach(this::show); }

    public void show(Player player) { player.openBook(build()); }

    public void show(List<Player> players) { players.forEach(this::show); }

}
