package commands;

import org.javacord.api.event.message.MessageCreateEvent;

public class Meme extends command {
    // joke command that sends a humorous gif
    public void executeCommand(MessageCreateEvent event, String[] args) {
        event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/1043957183898980362/1043957527764803584/computers.gif");
    }
}
