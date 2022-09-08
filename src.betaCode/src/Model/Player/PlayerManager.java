package Model.Player;

import Controller.Utilities.JsonFileManager;

import java.util.HashMap;
import java.util.List;

/**
 * Class used to store and retrieve data in this application model.
 *
 * @author D'annibale Luigi, Venturini Daniele
 */
public class PlayerManager
{
    /**
     * KEYS for stats
     * <table>
     *     <tr>
     *         <th>Key</th>
     *         <th>String representation</th>
     *     </tr>
     *     <tr>
     *         <td>NAME</td>
     *         <td>"name"</td>
     *     </tr>
     *     <tr>
     *         <td>PASSWORD</td>
     *         <td>"password"</td>
     *     </tr>
     *     <tr>
     *         <td>STATS</td>
     *         <td>"stats"</td>
     *     </tr>
     * </table>
     *
     * @author D'annibale Luigi, Venturini Daniele
     */
    public enum KEYS
    {
        NAME("name"),
        PASSWORD("password"),
        STATS("stats");
        public final String VALUE;
        KEYS(String val) { VALUE = val; }
    }
    private static final String FILE_PATH = "resources/saves.json";

    /**
     * Searches for an existing {@link Player} with the given nane.
     * @param name
     * @return the found player, if no player is found a default player is returned instead.
     */
    public static HumanPlayer findPlayerByNicknameOrDefault(String name)
    {
        try
        {
            return new HumanPlayer (new JsonFileManager().readJson(FILE_PATH)
                    .stream()
                    .filter(objectObjectHashMap ->objectObjectHashMap.get(KEYS.NAME.VALUE).equals(name))
                    .findFirst().get());
        }
        catch (Exception e)
        {
            return new HumanPlayer(getHashMap( "default","", new Stats().defaultValues()));
        }
    }

    /**
     * @param name
     * @param password
     * @param stats
     * @return an {@link HashMap} representation of the given values that uses {@link KEYS}.VALUE as keys
     */
    private static HashMap<Object, Object> getHashMap(String name, String password, HashMap stats)
    {
        return new HashMap<>()
        {{
            put(KEYS.NAME.VALUE, name);
            put(KEYS.PASSWORD.VALUE, password);
            put(KEYS.STATS.VALUE,stats);
        }};
    }

    /**
     * Saves the {@link Player} on the database.
     * @param playerToSave
     * @return true if the operation is successful, false otherwise
     */
    public static boolean savePlayer(HumanPlayer playerToSave)
    {
        try
        {
            HashMap<Object,Object> datas = new HashMap<>(){{
               put(KEYS.NAME.VALUE,playerToSave.getName());
               put(KEYS.PASSWORD.VALUE,playerToSave.getPassword());
               put(KEYS.STATS,playerToSave.getStats().getValues());
            }};
            return new JsonFileManager().writeJson(List.of(datas), FILE_PATH);
        }
        catch (Exception e)  { return false; }
    }

    /**
     * Removes the old {@link Player} values and then saves the updated values.
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
     * Removes the old {@link Player} values and then saves the updated values.
     * @param playerToSave
     * @return true if the operation is successful, false otherwise
     */
    public static boolean updatePlayer(HumanPlayer playerToSave)
    {
        if (!removePlayer(playerToSave.getName(), playerToSave.getPassword())) return false;
        return savePlayer(playerToSave);
    }

    /**
     * Removes the {@link Player} from the database.
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
            lines = lines.stream().filter(hashMap -> !hashMap.get(KEYS.NAME.VALUE).equals(name) && !hashMap.get(KEYS.PASSWORD.VALUE).equals(password)).toList();
            return fm.overWriteJson(lines, FILE_PATH);
        }
        catch (Exception e){return false;}
    }

}
