import commands.command;
import org.javacord.api.event.message.MessageCreateEvent;

public class commandHandler {
    private final MessageCreateEvent event;
    private final String[] commandString;
    public commandHandler(MessageCreateEvent someMessageEvent) {
        event = someMessageEvent;
        commandString = event.getMessageContent().substring(1).split(" ");
    }

    public void tryCommand() {
        try {
            commandMapper map = new commandMapper();
            command c = map.getCommandFromMap(commandString[0]);
            c.executeCommand(event, commandString);
        }
        catch (NullPointerException e) {
            event.getChannel().sendMessage("The command was invalid.");
        }
    }
}