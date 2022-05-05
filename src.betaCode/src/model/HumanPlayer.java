package model;

import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HumanPlayer extends Player{

    private static final String filePath = "file.json";
    private int matchWon;
    private int matchLost;
    private int level; //float and the decimal part is in a progress bar

    public HumanPlayer(String nickname){
        super.name = nickname;
        if (!getProfile(filePath))
            updateProfile(filePath);


    }

    @Override
    public void shoutUno() {}

    public boolean getProfile(String filePath)
    {
        try (Reader file = new FileReader(filePath)) {
            //We can write any JSONArray or JSONObject instance to the file
            String text = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            System.out.println(text);


        } catch (IOException e) {
            return false;

        }

        return false;
    }

    public boolean updateProfile(String filePath)
    {
        JSONObject player = new JSONObject();

        player.put("matchWon", matchWon);
        player.put("matchLost", matchLost);
        player.put("level", level);
        JSONObject player2 = new JSONObject();

        player2.put("matchWon", matchWon);
        player2.put("matchLost", matchLost);
        player2.put("level", level);
        JSONObject employeeObject2 = new JSONObject();
        employeeObject2.put(name, player);
        employeeObject2.put("danno", player2);
        //Add employees to list


        //Write JSON file
        try (FileWriter file = new FileWriter(filePath)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(employeeObject2.toString());
            file.flush();

        } catch (IOException e) {
            return false;

        }

        return true;
    }

    public int getMatchWon() { return matchWon; }
    public int getMatchLost() {return matchLost; }
    public int getLevel() { return level; }
}
