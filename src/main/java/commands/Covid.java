package commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Covid extends command {
    // reads from a json file returned by a covid-19 website's api and sends it in a message
    public void executeCommand(MessageCreateEvent event, String[] args) {
        // get the country name from the commands.command
        String cntryName = event.getMessageContent().substring(6).strip().toLowerCase().replace(' ', '-');

        try {
            URL url = new URL("https://api.covid19api.com/summary"); // open a connection to a site
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // GET is used to request info from a server
            conn.connect();

            if (conn.getResponseCode() != 200) { // if response code was not OK (200)
                throw new RuntimeException("Http Response Code: " + conn.getResponseCode());
            }
            else {
                String lines = ""; // store the lines of the JSON file as a string
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    lines += scanner.nextLine();
                }
                scanner.close();

                JSONParser jsonparser = new JSONParser(); // create a jsonparser object to parse the json file
                JSONObject obj = (JSONObject) jsonparser.parse(lines); // create jsonobject for reading

                JSONArray countries = (JSONArray) obj.get("Countries"); // save the countries array as a JSONArray for reading

                if (countries != null) { // check if website is currently caching, or down
                    boolean found = false;

                    for (int i = 0; i < countries.size(); i++) { // print totaldeaths and totalconfirmed
                        JSONObject country = (JSONObject) countries.get(i);
                        if (country.get("Slug").equals(cntryName)) {
                            found = true;
                            String tc = country.get("TotalConfirmed").toString(), td = country.get("TotalDeaths").toString();
                            String nc = country.get("NewConfirmed").toString(), nd = country.get("NewDeaths").toString();
                            event.getChannel().sendMessage("__**" + country.get("Country") + " Covid Statistics**__");
                            event.getChannel().sendMessage("Total Confirmed Cases: " + tc + "\nTotal Deaths: " + td);
                            event.getChannel().sendMessage("New Confirmed: " + nc + "\nNew Deaths: " + nd);
                        }
                    }
                    if (!found) // if country could not be found
                        event.getChannel().sendMessage("Country not found. Please try again with a valid country name.");
                }
                else // if data from covid api site unavailable
                    event.getChannel().sendMessage("Covid-19 statistics are unavailable at the moment.\nPlease wait for https://api.covid19api.com/summary to come back online.");
            }

        } catch (IOException | ParseException e) { // throw exceptions here
            event.getChannel().sendMessage("Error while attempting to read JSON file.");
            throw new RuntimeException(e);
        }
    }
}
