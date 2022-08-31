package Utilities;

import Model.Player.PlayerManager;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DataAccessManager
{
    private static final String PLAYER_CONFIG_JSON = "config.json";
    private static final String INIT_JSON = "init.json";
    public boolean saveConfig()  { return savePlayerConfig() && saveInit(); }

    public boolean savePlayerConfig()
    {
        assert(Config.savedPlayer != null):"savedPlayer is null in saveplayerconfig ";

        HashMap<Object, Object> currentConfig = Config.getPropertiesHashMapRapresentation();
        //HashMap<Object, Object> playerConfig = new HashMap<>();

//        playerConfig.put("player",Config.savedPlayer.getHashmapNamePassword());
//        playerConfig.put("config",currentConfig);


        JsonFileManager fm = new JsonFileManager();
        try
        {
            ArrayList<HashMap<Object,Object>> fileLines = fm.readJson(PLAYER_CONFIG_JSON);
            Optional<HashMap<Object,Object>> optionalPreviousPlayerConfig = fileLines.stream()
                    .filter(line -> ((JSONObject) line.get("player")).get("name") == Config.savedPlayer.getName())
                    .findAny();
            if (optionalPreviousPlayerConfig.isPresent()) fileLines.remove(optionalPreviousPlayerConfig.get());
            fileLines.add(Config.getPlayerConfig());
            //fileLines.forEach(l -> System.out.println(l));
            HashMap<Object, Object>[] a = new HashMap[fileLines.size()];
            int i = 0;
            //c'è qualcosa che non va qui nel salvataggio su file
            for (var l:fileLines) a[i++] = l;
            for (var l :a)
                System.out.println(l);
            return fm.writeJson(a, PLAYER_CONFIG_JSON);
            //return fm.writeJson((HashMap<Object, Object>[]) fileLines.toArray(), PLAYER_CONFIG_JSON);
        }
        catch (IOException e) { return false; }
    }

    public boolean saveInit() { return new JsonFileManager().writeJson(new HashMap[]{Config.getPlayerConfig()}, INIT_JSON); }

    public boolean loadConfig()
    {
        try
        {
            JsonFileManager fm = new JsonFileManager();
            var datas = fm.readJson(INIT_JSON).get(0);

            JSONObject player = (JSONObject) datas.get("player");
            Config.savedPlayer = PlayerManager.findPlayerByNicknameOrDefault((String) player.get("name"));

            JSONObject config = (JSONObject) datas.get("config");
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
