import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

import java.util.concurrent.TimeUnit;

public class bagpipe {
    // get tokens and IDs from env variables
    public static final String discordToken = System.getenv("discordToken");

    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder() // login with discord bot token
                .setToken(discordToken)
                .setAllNonPrivilegedIntents()
                .addIntents(Intent.MESSAGE_CONTENT)
                .login().join();

        // execute commands when a message starting with "!" is sent
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser() && event.getMessageContent().startsWith("!")) {
                commandHandler handler = new commandHandler(event);
                handler.tryCommand();
            }
        });

        // removes a message when a thumbs down emoji is added as a reaction
        api.addReactionAddListener(event -> {
            if (event.getEmoji().equalsEmoji("👎")) {
                event.deleteMessage();
            }
        }).removeAfter(30, TimeUnit.MINUTES);
    }
}