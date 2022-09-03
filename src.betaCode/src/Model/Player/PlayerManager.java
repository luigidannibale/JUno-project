package Model.Player;

import Utilities.JsonFileManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
    public static boolean savePlayer(HumanPlayer playerToSave)
    {
        try
        {
            JsonFileManager fm = new JsonFileManager();
            System.out.println("try saving player in saves");
            HashMap<Object,Object> datas = new HashMap<>(){{
               put("name",playerToSave.getName());
                put("password",playerToSave.getPassword());
                put("stats",playerToSave.getStats().getJsonStats());
            }};
            System.out.println(datas);

            return fm.writeJson(List.of(datas),filePath);
        }
        catch (IOException e)  { return false; }
    }

    public static boolean updatePlayer(HumanPlayer playerToSave, String oldName, String oldPassword)
    {
        if (!removePlayer(oldName,oldPassword)) return false;
        return savePlayer(playerToSave);
    }
    public static boolean removePlayer(String name, String password)
    {
        JsonFileManager fm = new JsonFileManager();
        try
        {
            List<HashMap<Object,Object>> lines = fm.readJson(filePath);
            lines = lines.stream().filter(hashMap -> !hashMap.get("name").equals(name) && !hashMap.get("password").equals(password)).toList();
            return fm.overWriteJson(lines,filePath);
        }
        catch (Exception e){return false;}
    }
    //public static boolean checkPassword(HashMap player, String password)  { return player.get("password") == password; }


}
