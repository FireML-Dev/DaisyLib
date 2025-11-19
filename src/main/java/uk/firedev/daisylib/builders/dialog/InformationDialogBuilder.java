package uk.firedev.daisylib.builders.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.dialog.DialogLike;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class InformationDialogBuilder {

    private final Replacer replacer = Replacer.replacer();
    private @NotNull Component title = Component.text("Information");
    private final @NotNull List<Object> content = new ArrayList<>();

    protected InformationDialogBuilder() {}

    // Class Things

    public InformationDialogBuilder addReplacer(@NotNull Replacer replacer) {
        this.replacer.merge(replacer);
        return this;
    }

    public InformationDialogBuilder addReplacement(@NotNull String variable, @NotNull Object replacement) {
        this.replacer.addReplacement(variable, replacement);
        return this;
    }

    public InformationDialogBuilder addReplacements(@NotNull Map<String, Object> replacements) {
        this.replacer.addReplacements(replacements);
        return this;
    }

    public @NotNull Replacer getReplacer() {
        return this.replacer;
    }

    public InformationDialogBuilder withTitle(@NotNull Object title) {
        this.title = ComponentMessage.componentMessage(title).get();
        return this;
    }

    public InformationDialogBuilder withContent(@NotNull List<Object> content) {
        this.content.clear();
        this.content.addAll(content);
        return this;
    }

    public InformationDialogBuilder addContent(@NotNull Object content) {
        this.content.add(content);
        return this;
    }

    // Building

    public DialogLike build() {
        return Dialog.create(builder -> builder.empty()
            .base(
                DialogBase.builder(ComponentMessage.componentMessage(title).replace(replacer).get())
                    .canCloseWithEscape(true)
                    .afterAction(DialogBase.DialogAfterAction.CLOSE)
                    .body(getBodies())
                    .build()
            )
            .type(DialogType.notice(
                ActionButton.builder(Component.text("Exit")).build()
            ))
        );
    }

    private List<? extends DialogBody> getBodies() {
        return ComponentMessage.componentMessage(content).replace(replacer).get().stream()
            .map(DialogBody::plainMessage)
            .toList();
    }

}
