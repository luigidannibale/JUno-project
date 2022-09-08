package Controller.Utilities;

import Model.Player.HumanPlayer;
import Model.Player.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataAccessManager
{

    private static final String PLAYER_CONFIG_JSON = "config.json";
    private static final String INIT_JSON = "init.json";

    //Model delegation of data
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

    //Init data
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

    //Config data
    /**
     * Loads stored player config when this logs in.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean loadPlayerProfile(String name)
    {
        try
        {
            JsonFileManager fm = new JsonFileManager();
            HashMap<Object, Object> datas = fm.readJson(PLAYER_CONFIG_JSON).stream().filter(hashMap -> ((HashMap)hashMap.get("player")).get("name").equals(name)).toList().get(0);
            loadConfig(datas);
            return true;
        }
        catch (Exception e)
        { return false; }
    }


    public boolean updatePlayerConfig(HumanPlayer player, String oldName)
    {
        JsonFileManager fm = new JsonFileManager();
        try {
            ArrayList<HashMap<Object, Object>> fileLines = new ArrayList<>(fm.readJson(PLAYER_CONFIG_JSON).stream().filter(l -> !(((HashMap<Object, Object>) l.get("player")).get("name").toString().equals(oldName))).toList());
            fileLines.add(Config.getPlayerConfig(player));
            return fm.overWriteJson(fileLines, PLAYER_CONFIG_JSON);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method saves the player personal config, it sets it as default config for next start.
     * @return
     */
    public boolean saveProfile(HumanPlayer player)
    {
        if (player.getName().equals("default")) return false;
        return savePlayerConfig(player) && saveInit(player);
    }

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
    {
        return updatePlayerConfig(player, player.getName());
//        JsonFileManager fm = new JsonFileManager();
//        try
//        {
//            ArrayList<HashMap<Object, Object>> fileLines = new ArrayList<>(fm.readJson(PLAYER_CONFIG_JSON).stream().filter(l -> !(((HashMap<Object, Object>) l.get("player")).get("name").toString().equals(player.getName()))).toList());
//            fileLines.add(Config.getPlayerConfig(player));
//            return fm.overWriteJson(fileLines, PLAYER_CONFIG_JSON);
//        }
//        catch (Exception e){return false;}
    }
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
     * Assigns the data (player stats are retrieved using Model methods) to config, if something goes wrong throws exception.
     * @param data
     * @throws Exception
     */
    private void loadConfig(HashMap<Object, Object> data) throws Exception
    {
        HashMap player = (HashMap) data.get("player");
        HumanPlayer foundPlayer = getModelProfile((String) player.get("name"));
        if (!foundPlayer.getName().equals(player.get("name"))) throw new Exception("Player Not Found");
        Config.loggedPlayer = foundPlayer;

        HashMap<Object, Object> config = (HashMap) data.get("config");

        Config.setHashMap(config);
        Config.refreshScalingPercentage();
    }

}
