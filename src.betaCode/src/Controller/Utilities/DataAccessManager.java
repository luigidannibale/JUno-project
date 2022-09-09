package Controller.Utilities;

import Model.Players.HumanPlayer;
import Model.Players.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class acts as the data layer of the application,  <br/>
 * and provides all the methods to access and store data. <br/>
 * Works along the {@link PlayerManager}.
 * @author D'annibale Luigi, Venturini Daniele
 */
public class DataAccessManager
{

    private static final String PLAYER_CONFIG_JSON = "config.json";
    private static final String INIT_JSON = "init.json";

    //MODEL DELEGATION OF DATA
    /**
     * Delegates the storing of player (model) data to the model.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean saveModelProfile(HumanPlayer playerToSave)
    { return PlayerManager.findPlayerByNicknameOrDefault(playerToSave.getName()).getName().equals(playerToSave.getName()) ? PlayerManager.updatePlayer(playerToSave) : PlayerManager.savePlayer(playerToSave); }

    /**
     * Delegates the storing of player (model) data to the model.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean updateModelProfile(String oldName, String oldPassword)
    { return PlayerManager.updatePlayer(Config.loggedPlayer,oldName,oldPassword); }

    /**
     * Delegates the retrieving of player (model) data to the model.
     * @return true if the operation is successful, false otherwise.
     */
    public HumanPlayer getModelProfile(String name)
    { return PlayerManager.findPlayerByNicknameOrDefault(name); }
    //------------------------

    //INITIALIZATION DATA
    /**
     * Stores player personal config as default for next start.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean saveInit(HumanPlayer player)
    { return new JsonFileManager().overWriteJson(Arrays.stream((HashMap<Object,Object>[])new HashMap[]{Config.getPlayerConfig(player)}).toList(), INIT_JSON); }

    /**
     *Loads stored player config when the application starts, if something goes wrong loads the default.
     *@return true if the operation loaded stored player, false if loaded default.
     */
    public boolean loadInitOrDefault()
    {
        boolean success = loadInit();
        if(!success) Config.assignDefaultValues();
        return success;
    }
    //------------------------

    //CONFIG DATA
    /**
     * Loads stored player config when this logs in.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean loadPlayerProfile(String name)
    {
        try
        {
            JsonFileManager fm = new JsonFileManager();
            HashMap<Object, Object> datas = fm.readJson(PLAYER_CONFIG_JSON).stream().filter(hashMap -> ((HashMap)hashMap.get(Config.KEYS.LOGGED_PLAYER.VALUE)).get(PlayerManager.KEYS.NAME.VALUE).equals(name)).toList().get(0);
            loadConfig(datas);
            return true;
        }
        catch (Exception e)
        { return false; }
    }

    /**
     * Updates the player config by the old name. <br/>
     * (The name can remain the same but some values are changed, in that case it's possible to give as oldName the actual name)
     * @param player
     * @param oldName
     * @return true if the operation is successful, false otherwise
     */
    public boolean updatePlayerConfig(HumanPlayer player, String oldName)
    {
        JsonFileManager fm = new JsonFileManager();
        try
        {
            ArrayList<HashMap<Object, Object>> fileLines = new ArrayList<>(fm.readJson(PLAYER_CONFIG_JSON).stream().filter(l -> !(((HashMap<Object, Object>) l.get(Config.KEYS.LOGGED_PLAYER.VALUE)).get(PlayerManager.KEYS.NAME.VALUE).toString().equals(oldName))).toList());
            fileLines.add(Config.getPlayerConfig(player));
            return fm.overWriteJson(fileLines, PLAYER_CONFIG_JSON);
        }
        catch (Exception e)
        { return false; }
    }

    /**
     * This method saves the player personal config, it sets it as default config for next start.
     * @return true if the operation is successful, false otherwise
     */
    public boolean saveProfile(HumanPlayer player)
    {
        if (player.getName().equals("default")) return false;
        return savePlayerConfig(player) && saveInit(player);
    }

    /**
     * Updates the player config and his profile by the old name, and old password. <br/>
     *(The name can remain the same but some values are changed, in that case it's possible to give as oldName the actual name)
     * @param player
     * @param oldName
     * @param oldPassword
     * @return true if the operation is successful, false otherwise
     */
    public boolean updateProfile(HumanPlayer player, String oldName, String oldPassword)
    {
        if (player.getName().equals("default")) return false;
        return updatePlayerConfig(player, oldName) && updateModelProfile(oldName, oldPassword) && saveInit(player);
    }

    /**
     * Stores player personal config.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean savePlayerConfig(HumanPlayer player)
    { return updatePlayerConfig(player, player.getName()); }
    //------------------------

    /**
     * Loads stored player config.
     * @return true if the operation is successful, false otherwise.
     */
    private boolean loadInit()
    {
        try
        {
            JsonFileManager fm = new JsonFileManager();
            HashMap<Object, Object> datas = fm.readJson(INIT_JSON).get(0);
            loadConfig(datas);
            return true;
        }
        catch (Exception e)
        { return false; }
    }

    /**
     * Assigns the data (player stats are retrieved using Model methods) to config
     * @param data
     * @throws Exception if anything goes wrong
     */
    private void loadConfig(HashMap<Object, Object> data) throws Exception
    {
        HashMap player = (HashMap) data.get(Config.KEYS.LOGGED_PLAYER.VALUE);
        HumanPlayer foundPlayer = getModelProfile((String) player.get(PlayerManager.KEYS.NAME.VALUE));
        if (!foundPlayer.getName().equals(player.get(PlayerManager.KEYS.NAME.VALUE))) throw new Exception("Player Not Found");
        Config.loggedPlayer = foundPlayer;

        HashMap<Object, Object> config = (HashMap) data.get(Config.KEYS.CONFIG.VALUE);

        Config.setHashMap(config);
        Config.refreshScalingPercentage();
    }

}
