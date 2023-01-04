package commands;

import org.javacord.api.event.message.MessageCreateEvent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// reads from a string line returned by wolfram alpha's short answers api
public class Wolfram extends command {
    public static final String wolframAppID = System.getenv("wolframAppID");
    public void executeCommand(MessageCreateEvent event, String[] args) {
        // get the rest of the string in the command
        String query = event.getMessageContent().substring(8).strip().toLowerCase().replace(' ', '+');

        if (!query.isBlank()) { // if command non-empty, try to query it in wolfram alpha

            // create URL using short answer query, part of the message, appid, and metric units
            query = "https://api.wolframalpha.com/v1/result?i=" + query + "&appid=" + wolframAppID + "&units=metric";
            System.out.println("The query to wolfram: " + query);

            try {
                URL url = new URL(query); // open a connection to a site
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET"); // GET is used to request info from a server
                conn.connect();

                if (conn.getResponseCode() != 200) { // if response code was not OK (200)
                    throw new RuntimeException("Http Response Code: " + conn.getResponseCode());
                } else {
                    StringBuilder lines = new StringBuilder(); // store the lines of the webpage response as a string
                    Scanner scanner = new Scanner(url.openStream());
                    while (scanner.hasNext())
                        lines.append(scanner.nextLine());
                    scanner.close();
                    event.getChannel().sendMessage(lines.toString());
                }
            } catch (RuntimeException | IOException e) { // throw exceptions here
                event.getChannel().sendMessage("Error while attempting to query Wolfram Alpha.");
                throw new RuntimeException(e);
            }
        }
        else
            event.getChannel().sendMessage("Please include a query with the command.");
    }
}
