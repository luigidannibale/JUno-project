package Utilities;

import Model.Player.HumanPlayer;
import View.Elements.DeckColor;
import View.Elements.GraphicQuality;

import java.awt.*;
import java.util.HashMap;


public class Config
{
    public static int effectsVolume;
    public static int musicVolume;
    public static DeckColor deckStyle;
    public static GraphicQuality graphicQuality;
    public static HumanPlayer savedPlayer;
    public static double scalingPercentage;
    private final String fileName = "config.json";

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
    public Config()
    {
        loadConfigOrDefault();
    }

    public void assignDefaultValues()
    {
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

    public boolean saveConfig()
    {
        HashMap<String, Object> info = new HashMap<>();

        info.put("effectsVolume", effectsVolume);
        info.put("musicVolume", musicVolume);
        info.put("deckStyle", deckStyle.name());
        info.put("graphicQuality", graphicQuality.name());

        HashMap<String, Object> info2 = new HashMap<>();
        info2.put("savedPlayer",savedPlayer);
        info2.put("config",info);

        FileManager fm = new FileManager();
        return fm.writeJson(new HashMap[]{info2}, fileName);
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
            var datas = fm.readJson(fileName);
            HashMap<Object,Object> info = (HashMap<Object,Object>) datas.stream().filter(hashMap-> ((HumanPlayer) hashMap.get("player"))==savedPlayer).toList().get(0);

            effectsVolume = (int)info.get("effectsVolume");
            musicVolume = (int)info.get("musicVolume");
            graphicQuality = GraphicQuality.valueOf((String) info.get("graphicQuality"));
            deckStyle = DeckColor.valueOf((String)info.get("deckStyle"));
            refreshScalingPercentage();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
