package uk.firedev.daisylib.internal.config;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.config.ConfigBase;
import uk.firedev.daisylib.internal.DaisyLibPlugin;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;

@ApiStatus.Internal
public class MessageConfig extends ConfigBase {

    public MessageConfig() {
        super("messages.yml", "messages.yml", DaisyLibPlugin.getInstance());
    }

    public @NonNull ComponentSingleMessage getPrefix() {
        // Call super here to avoid infinite recursion.
        return super.getComponentMessage("prefix", "<gray>[DaisyLib]</gray> ").toSingleMessage();
    }

    public @NonNull Replacer getPrefixReplacer() {
        return Replacer.replacer().addReplacement("{prefix}", getPrefix());
    }

    public @NonNull ComponentMessage getReloaded() {
        return getComponentMessage("main-command.reloaded", "{prefix}<#F0E68C>Successfully reloaded the plugin.")
            .replace(getPrefixReplacer());
    }

    @Override
    public @NonNull ComponentMessage getComponentMessage(@NonNull String path, @NonNull Object def) {
        return super.getComponentMessage(path, def).replace(getPrefixReplacer());
    }

}
