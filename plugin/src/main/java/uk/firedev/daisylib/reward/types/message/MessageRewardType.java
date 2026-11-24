package uk.firedev.daisylib.reward.types.message;

import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.messages.message.MessageType;

public class MessageRewardType extends MessageRewardBase {

    @Override
    public @NonNull String getIdentifier() {
        return "Message";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull MessageType getMessageType() {
        return MessageType.CHAT;
    }

}
