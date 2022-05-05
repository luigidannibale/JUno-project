package model;


import java.io.FileWriter;
import java.io.IOException;

public class HumanPlayer extends Player{

    private static final String filePath = "";
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
        return false;
    }

    public boolean updateProfile(String filePath)
    {
        JSONObject player = new JSONObject();
        player.put("matchWon", 0);
        player.put("matchLost", 0);
        player.put("level", 0);

        JSONObject employeeObject2 = new JSONObject();
        employeeObject2.put(name, player);

        //Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.add(employeeObject2);

        //Write JSON file
        try (FileWriter file = new FileWriter(filePath)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            return false;
            e.printStackTrace();
        }

        return true;
    }

    public int getMatchWon() { return matchWon; }
    public int getMatchLost() {return matchLost; }
    public int getLevel() { return level; }
}
