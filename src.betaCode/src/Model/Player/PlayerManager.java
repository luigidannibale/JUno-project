package Model.Player;

import Utilities.JsonFileManager;
import org.json.JSONObject;

import java.util.HashMap;

public class PlayerManager
{
    private static final String filePath = "resources/saves.json";

    public static HumanPlayer findPlayerByNicknameOrDefault(String nickname)
    {
        try
        {
            JsonFileManager fm = new JsonFileManager();
            return new HumanPlayer ((HashMap) fm.readJson(filePath)
                    .stream()
                    .filter(objectObjectHashMap ->objectObjectHashMap.get("nickname").equals(nickname))
                    .toArray()[0]);
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            return new HumanPlayer( new HashMap<>(){{
                put("name","default");
                put("password","");
                put("stats",new JSONObject(new Stats().defaultValues()));
            }});
        }
    }


    public static boolean checkPassword(HashMap player, String password)  { return player.get("password") == password; }


}
