package Utilities;

import Model.Player.PlayerManager;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;

import java.io.IOException;
import java.util.*;

public class DataAccessManager
{
    private static final String PLAYER_CONFIG_JSON = "config.json";
    private static final String INIT_JSON = "init.json";
    public boolean saveConfig()  { return savePlayerConfig() && saveInit(); }

    public boolean savePlayerConfig()
    {
        assert(Config.loggedPlayer != null):"savedPlayer is null in saveplayerconfig ";

        JsonFileManager fm = new JsonFileManager();
        try
        {
            //ArrayList<HashMap<Object,Object>> fileLines = fm.readJson(PLAYER_CONFIG_JSON);
            ArrayList<HashMap<Object,Object>> a = new ArrayList<>();
            a.addAll(fm.readJson(PLAYER_CONFIG_JSON).stream().filter(l->!(((HashMap<Object, Object>) l.get("player")).get("name").equals(Config.loggedPlayer.getName()))).toList());
            a.add(Config.getPlayerConfig());
            return fm.overWriteJson(a, PLAYER_CONFIG_JSON);
        }
        catch (IOException e) { return false; }
    }

    public boolean saveInit() { return new JsonFileManager().overWriteJson(Arrays.stream((HashMap<Object,Object>[])new HashMap[]{Config.getPlayerConfig()}).toList(), INIT_JSON); }

    public boolean loadConfig()
    {
        try
        {
            JsonFileManager fm = new JsonFileManager();
            var datas = fm.readJson(INIT_JSON).get(0);

            HashMap player = (HashMap) datas.get("player");
            Config.loggedPlayer = PlayerManager.findPlayerByNicknameOrDefault((String) player.get("name"));

            HashMap<Object, Object> config = (HashMap) datas.get("config");
            Config.effectsVolume = (int)config.get("effectsVolume");
            Config.musicVolume = (int)config.get("musicVolume");
            Config.graphicQuality = GraphicQuality.valueOf((String) config.get("graphicQuality"));
            Config.deckStyle = DeckColor.valueOf((String)config.get("deckStyle"));
            Config.refreshScalingPercentage();
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Cant load config : " + e);
            return false;
        }
    }
    public boolean loadConfigOrDefault()
    {
        boolean success = loadConfig();
        if(!success) Config.assignDefaultValues();
        return success;
    }
}
