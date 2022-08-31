package Model.Player;

import Utilities.FileManager;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerManager
{
    private static final String filePath = "resources/saves.json";

    public static HumanPlayer findPlayerByNicknameOrDefault(String nickname)
    {
        try
        {
            FileManager fm = new FileManager();
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
