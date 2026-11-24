package uk.firedev.daisylib.reward.types.message;

import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.messages.message.MessageType;

public class SubtitleRewardType extends MessageRewardBase {

    @Override
    public @NonNull MessageType getMessageType() {
        return MessageType.SUBTITLE;
    }

    @Override
    public @NonNull String getIdentifier() {
        return "Subtitle";
    }

    @Override
    public @NonNull String getAuthor() {
        return "FireML";
    }

}
