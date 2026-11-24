package uk.firedev.daisylib.reward.types.message;

import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.messages.message.MessageType;

public class ActionBarRewardType extends MessageRewardBase {

    @Override
    public @NonNull MessageType getMessageType() {
        return MessageType.ACTION_BAR;
    }

    @Override
    public @NonNull String getIdentifier() {
        return "ActionBar";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

}
