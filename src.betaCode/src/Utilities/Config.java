package Utilities;

import Model.Player.HumanPlayer;
import Model.Player.Player;
import Model.Player.PlayerManager;
import Model.Player.Stats;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class Config
{
    public static int effectsVolume;
    public static int musicVolume;
    public static DeckColor deckStyle;
    public static GraphicQuality graphicQuality;
    public static HumanPlayer savedPlayer;
    public static double scalingPercentage;
    private final String fileName = "config.json";
    private final String initFile = "init.json";
    /**
     * Tries to read datas from file, if something goes wrong
     * assigns the folowing default values :
     * <ul>
     *  <li>effectsVolume = 50;</li>
     *  <li>musicVolume = 50;</li>
     *  <li>deckStyle = DeckColor.WHITE;</li>
     *  <li>graphicQuality = GraphicQuality.LOW;</li>
     * </ul>
     */
    public Config()  { loadConfigOrDefault(); }

    public void assignDefaultValues()
    {
        savedPlayer = new HumanPlayer("default","");
        effectsVolume = 50;
        musicVolume = 50;
        deckStyle = DeckColor.WHITE;
        graphicQuality = GraphicQuality.LOW;
        refreshScalingPercentage();
    }

    public Config(int effectsVolume, int musicVolume, DeckColor deckStyle, GraphicQuality graphicQuality)
    {
        this.effectsVolume = effectsVolume;
        this.musicVolume = musicVolume;
        this.deckStyle = deckStyle;
        this.graphicQuality = graphicQuality;
        refreshScalingPercentage();
    }
    public void refreshScalingPercentage() { scalingPercentage = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1920; }

    public boolean saveConfig()  { return savePlayerConfig() && saveInit(); }

    public boolean savePlayerConfig()
    {
        assert(savedPlayer != null):"savedPlayer is null in saveplayerconfig ";
        HashMap<String, Object> info = new HashMap<>();

        info.put("effectsVolume", effectsVolume);
        info.put("musicVolume", musicVolume);
        info.put("deckStyle", deckStyle.name());
        info.put("graphicQuality", graphicQuality.name());

        HashMap<Object, Object> newLine = new HashMap<>();
        newLine.put("player",new JSONObject(savedPlayer));
        newLine.put("config",info);


        FileManager fm = new FileManager();
        try
        {
            ArrayList<HashMap<Object,Object>> fileLines = fm.readJson(fileName);
            try
            {//updates the player setup, if there is not a setup for the player yet it thors nosuchelement exception
                fileLines.set(fileLines.indexOf(fileLines.stream().filter(line -> ((JSONObject) line.get("player")).get("name") == savedPlayer.getName()).findAny().get()), newLine);
            }
            catch (NoSuchElementException e)
            {// creates the setup for the player
                fileLines.add(newLine);
            }

            HashMap[] a = new HashMap[fileLines.size()];
            int i = 0;
            for (var line:fileLines) {
                System.out.println(line);
                a[i++] = line;
            }

            System.out.println(a);
            return fm.writeJson(a,fileName);
        }
        catch (IOException e) { return false; }
    }

    public boolean saveInit()
    {
        HashMap<Object, Object> config = new HashMap<>();
        config.put("effectsVolume", effectsVolume);
        config.put("musicVolume", musicVolume);
        config.put("deckStyle", deckStyle.name());
        config.put("graphicQuality", graphicQuality.name());

        HashMap<Object, Object> player =savedPlayer.getHashmap();

        HashMap<Object, Object> data[] = new HashMap[]{new HashMap<>(){{
            put("config",config);
            put("player",player);
        }}};

        FileManager fm = new FileManager();
        return fm.writeJson(data, initFile);
    }

    public boolean loadConfigOrDefault()
    {
        boolean success = loadConfig();
        if(!success) assignDefaultValues();
        return success;
    }
    public boolean loadConfig()
    {
        try
        {
            FileManager fm = new FileManager();
            var datas = fm.readJson(initFile).get(0);

            JSONObject player = (JSONObject) datas.get("player");
            var playerData = PlayerManager.findPlayerByNickname((String) player.get("name"));

            savedPlayer = new HumanPlayer((String) playerData.get("name"),
                                            (String) playerData.get("password"),
                                            (JSONObject) playerData.get("stats"));
            JSONObject config = (JSONObject) datas.get("config");
            effectsVolume = (int)config.get("effectsVolume");
            musicVolume = (int)config.get("musicVolume");
            graphicQuality = GraphicQuality.valueOf((String) config.get("graphicQuality"));
            deckStyle = DeckColor.valueOf((String)config.get("deckStyle"));
            refreshScalingPercentage();
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Cant load config : " + e);
            return false;
        }
    }

    public void savePlayer(HumanPlayer playerToSave){ savedPlayer = playerToSave; }
}
    //#region ---getters and setters
//    public int getEffectsVolume() { return effectsVolume; }
//    public int getMusicVolume() { return musicVolume; }
//    public DeckColor getDeckStyle() { return deckStyle; }
//    public GraphicQuality getGraphicQuality() { return graphicQuality; }
//    public void setMusicVolume(int musicVolume) { this.musicVolume = musicVolume; }
//    public void setEffectsVolume(int effectsVolume) { this.effectsVolume = effectsVolume; }
//    public void setDeckStyle(DeckColor deckStyle) { this.deckStyle = deckStyle; }
//    public void setGraphicQuality(GraphicQuality graphicQuality) { this.graphicQuality = graphicQuality; }
//    public boolean writeOnFile()
//    {
//        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
//        {
//            //Music volume
//            bw.write(effectsVolume);
//            bw.newLine();
//            //Effects volume
//            bw.write(musicVolume);
//            bw.newLine();
//            //DeckColor
//            bw.write(deckStyle.VALUE);
//            bw.newLine();
//            //Graphics
//            bw.write(graphicQuality.VALUE);
//            bw.newLine();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//    public boolean readFromFile()
//    {
//        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
//        {
//            effectsVolume = Integer.parseInt(br.readLine());
//            musicVolume = Integer.parseInt(br.readLine());
//            deckStyle = DeckColor.valueOf(br.readLine());
//            graphicQuality = GraphicQuality.valueOf(br.readLine());
//            return true;
//        } catch (IOException e){
//            e.printStackTrace();
//            return false;
//        }
//    }
