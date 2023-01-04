package commands;

import org.javacord.api.event.message.MessageCreateEvent;

public class Ping extends command {
    // basic command for checking that the bot is responding to message events by sending a reply "Pong!"
    public void executeCommand(MessageCreateEvent event, String[] args) {
        event.getChannel().sendMessage("Pong!");
    }
}
