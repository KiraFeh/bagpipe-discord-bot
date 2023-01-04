package commands;

import org.javacord.api.event.message.MessageCreateEvent;

public class Repeat extends command {
    // repeats the command arguments that the user sent
    public void executeCommand(MessageCreateEvent event, String[] args) {
        String s = event.getMessageContent().substring(7);
        if (args.length == 1) { // if no arguments
            event.getChannel().sendMessage("*No value*");
            System.out.println(s);
        }
        else {
            event.getChannel().sendMessage(s.substring(1));
            System.out.println(s.substring(1)); // print s to console to show result
        }
    }
}
