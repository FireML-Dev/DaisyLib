package uk.firedev.daisylib.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import uk.firedev.daisylib.local.config.MessageConfig;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public interface InformationDialog {

    @NotNull Replacer replacer();

    @NotNull String title();

    @NotNull List<String> content();

    default @NotNull Dialog get() {
        return Dialog.create(builder -> builder.empty()
            .base(
                DialogBase.builder(ComponentMessage.componentMessage(title()).replace(replacer()).get())
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

    default void open(@NotNull Audience audience) {
        audience.showDialog(get());
    }

    private List<? extends DialogBody> getBodies() {
        return ComponentMessage.componentMessage(content()).replace(replacer()).get().stream()
            .map(DialogBody::plainMessage)
            .toList();
    }

}
