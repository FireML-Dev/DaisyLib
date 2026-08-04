package uk.firedev.daisylib.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.IncomingWebhookClient;
import net.dv8tion.jda.api.entities.WebhookClient;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.DaisyLib;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DiscordBot {

    private final Map<String, IncomingWebhookClient> webhookCache = new HashMap<>();

    private JDA bot;

    public DiscordBot() {}

    public void load(@Nullable String token) {
        if (bot != null) {
            return;
        }
        if (token == null) {
            throw new RuntimeException("Failed to load discord bot: No token provided.");
        }
        DaisyLib.get().getLogging().info("Loading discord bot.");
        try {
            JDABuilder builder = JDABuilder.create(token, getGatewayIntents())
                .setMemberCachePolicy(getMemberCachePolicy())
                .enableCache(getCacheFlags());
            this.bot = builder.build();
            awaitReady();
            bot.updateCommands().addCommands(getCommands()).queue();
            bot.addEventListener(getListeners().toArray());
            DaisyLib.get().getLogging().info("Loaded discord bot: " + bot.getSelfUser().getName());
        } catch (InvalidTokenException exception) {
            throw new RuntimeException("Failed to load discord bot.", exception);
        }
    }

    private void awaitReady() {
        if (!shouldAwaitReady()) {
            return;
        }
        try {
            this.bot.awaitReady();
        } catch (InterruptedException exception) {
            DaisyLib.get().getLogging().error("Waiting for bot to load was interrupted!", exception);
        }
    }

    public @NonNull JDA getBot() {
        if (bot == null) {
            throw new IllegalStateException("DiscordBot is not loaded.");
        }
        return this.bot;
    }

    public void sendMessage(long channelId, @NonNull String message) {
        MessageChannel channel = getBot().getChannelById(MessageChannel.class, channelId);
        if (channel == null) {
            DaisyLib.get().getLogging().warn(channelId + " is not a valid MessageChannel.");
            return;
        }
        channel.sendMessage(message).queue();
    }

    public @NonNull IncomingWebhookClient getWebhook(@NonNull String url) {
        return webhookCache.computeIfAbsent(url, k -> WebhookClient.createClient(getBot(), k));
    }

    /**
     * Should we call {@link JDA#awaitReady()} when the bot is created?
     * <p>
     * This will block the main thread. Should only be used during startup.
     */
    public abstract boolean shouldAwaitReady();

    public abstract @NonNull EnumSet<@NonNull GatewayIntent> getGatewayIntents();

    public abstract @NonNull Collection<@NonNull CacheFlag> getCacheFlags();

    public abstract @NonNull MemberCachePolicy getMemberCachePolicy();

    public abstract @NonNull List<@NonNull CommandData> getCommands();

    public abstract @NonNull Collection<? extends EventListener> getListeners();

}
