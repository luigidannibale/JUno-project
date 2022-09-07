package Model.Player;

import Utilities.JsonFileManager;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PlayerManager
{
    public enum KEYS
    {
        NAME("name"),
        PASSWORD("password"),
        STATS("stats");
        String value;

        KEYS(String val) {value = val;}
        @Override
        public String toString() { return value; }
    }
    private static final String FILE_PATH = "resources/saves.json";

    /**
     * Seaerches for an existing played with the given nane,
     * if the player is found it is returned else a default player is returned.
     * @param name
     * @return true if the operation is successful, false otherwise
     */
    public static HumanPlayer findPlayerByNicknameOrDefault(String name)
    {
        try
        {
            return new HumanPlayer (new JsonFileManager().readJson(FILE_PATH)
                    .stream()
                    .filter(objectObjectHashMap ->objectObjectHashMap.get(KEYS.NAME.toString()).equals(name))
                    .findFirst().get());
        }
        catch (Exception e)
        {
            return new HumanPlayer(getHashMap( "default","", new Stats().defaultValues()));
        }
    }

    @NotNull
    private static HashMap<Object, Object> getHashMap(String name, String password, HashMap stats)
    {
        return new HashMap<>()
        {{
            put(KEYS.NAME.toString(), name);
            put(KEYS.PASSWORD.toString(), password);
            put(KEYS.STATS.toString(),stats);
        }};
    }

    /**
     * Saves the player on the database.
     * @param playerToSave
     * @return true if the operation is successful, false otherwise
     */
    public static boolean savePlayer(HumanPlayer playerToSave)
    {
        try
        {
            HashMap<Object,Object> datas = new HashMap<>(){{
               put("name",playerToSave.getName());
               put("password",playerToSave.getPassword());
               put("stats",playerToSave.getStats().getValues());
            }};
            return new JsonFileManager().writeJson(List.of(datas), FILE_PATH);
        }
        catch (Exception e)  { return false; }
    }

    /**
     * Removes the old player values and then saves the updated values.
     * @param playerToSave
     * @param oldName
     * @param oldPassword
     * @return true if the operation is successful, false otherwise
     */
    public static boolean updatePlayer(HumanPlayer playerToSave, String oldName, String oldPassword)
    {
        if (!removePlayer(oldName,oldPassword)) return false;
        return savePlayer(playerToSave);
    }
    /**
     * Removes the old player values and then saves the updated values.
     * @param playerToSave
     * @return true if the operation is successful, false otherwise
     */
    public static boolean updatePlayer(HumanPlayer playerToSave)
    {
        if (!removePlayer(playerToSave.getName(), playerToSave.getPassword())) return false;
        return savePlayer(playerToSave);
    }

    /**
     * Removes the player from the database.
     * @param name
     * @param password
     * @return true if the operation is successful, false otherwise
     */
    public static boolean removePlayer(String name, String password)
    {
        JsonFileManager fm = new JsonFileManager();
        try
        {
            List<HashMap<Object,Object>> lines = fm.readJson(FILE_PATH);
            lines = lines.stream().filter(hashMap -> !hashMap.get("name").equals(name) && !hashMap.get("password").equals(password)).toList();
            return fm.overWriteJson(lines, FILE_PATH);
        }
        catch (Exception e){return false;}
    }

}
