package Model.Player;

import Utilities.JsonFileManager;

import java.util.HashMap;

public class PlayerManager
{
    private static final String filePath = "resources/saves.json";

    public static HumanPlayer findPlayerByNicknameOrDefault(String name)
    {
        try
        {
            JsonFileManager fm = new JsonFileManager();

            return new HumanPlayer (fm.readJson(filePath)
                    .stream()
                    .filter(objectObjectHashMap ->objectObjectHashMap.get("name").equals(name))
                    .findFirst().get());
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            return new HumanPlayer( new HashMap<>(){{
                put("name","default");
                put("password","");
                put("stats", new Stats().defaultValues());
            }});
        }
    }


    public static boolean checkPassword(HashMap player, String password)  { return player.get("password") == password; }


}
