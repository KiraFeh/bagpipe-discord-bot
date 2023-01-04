import commands.*;

import java.util.HashMap;

public class commandMapper {
    HashMap<String, command> mapOfCommands = new HashMap<>();
    public commandMapper() {
        mapOfCommands.put("ping", new Ping());
        mapOfCommands.put("repeat", new Repeat());
        mapOfCommands.put("meme", new Meme());
        mapOfCommands.put("wolfram", new Wolfram());
        mapOfCommands.put("covid", new Covid());
    }

    public command getCommandFromMap(String s) {
        return mapOfCommands.get(s);
    }
}
