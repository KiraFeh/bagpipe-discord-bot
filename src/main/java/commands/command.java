package commands;

import org.javacord.api.event.message.MessageCreateEvent;

public abstract class command {
    public abstract void executeCommand(MessageCreateEvent event, String[] args);
}