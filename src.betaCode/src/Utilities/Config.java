package Utilities;

import View.DeckColor;
import View.GraphicQuality;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;


public class Config {
    public static int effectsVolume;
    public static int musicVolume;
    public static DeckColor deckStyle;
    public static GraphicQuality graphicQuality;
    public static double scalingPercentage = 0;
    private final String fileName = "config.json";

    /**
     * Tries to read datas from file, if something goes wrong: <br/>
     * assigns default values :
     * <ul>
     *  <li>effectsVolume = 50;</li>
     *  <li>musicVolume = 50;</li>
     *  <li>deckStyle = DeckColor.WHITE;</li>
     *  <li>graphicQuality = GraphicQuality.LOW;</li>
     * </ul>
     */
    public Config()
    {
        if (!loadConfig())
            assignDefaultValues();
    }

    public void assignDefaultValues()
    {
        effectsVolume = 50;
        musicVolume = 50;
        deckStyle = DeckColor.WHITE;
        graphicQuality = GraphicQuality.LOW;
        if(scalingPercentage == 0) scalingPercentage = 1;
    }

    public Config(int effectsVolume, int musicVolume, DeckColor deckStyle, GraphicQuality graphicQuality, double scalingPercentage)
    {
        this.effectsVolume = effectsVolume;
        this.musicVolume = musicVolume;
        this.deckStyle = deckStyle;
        this.graphicQuality = graphicQuality;
        this.scalingPercentage = scalingPercentage;
    }

    public boolean saveConfig()
    {
        HashMap<String, Object> info = new HashMap<>();

        info.put("effectsVolume", effectsVolume);
        info.put("musicVolume", musicVolume);
        info.put("deckStyle", deckStyle.VALUE);
        info.put("graphicQuality", graphicQuality.VALUE);
        info.put("scalingPercentage", scalingPercentage);

        FileManager fm = new FileManager();
        return fm.writeJson(new HashMap[]{info}, fileName);
    }

    public boolean loadConfig()
    {
        try
        {
            FileManager fm = new FileManager();
            var info = fm.readJson(fileName).get(0);
            effectsVolume = (int)info.get("effectsVolume");
            musicVolume = (int)info.get("musicVolume");
            graphicQuality = GraphicQuality.valueOf((String) info.get("graphicQuality"));
            deckStyle = DeckColor.valueOf((String)info.get("deckStyle"));
            scalingPercentage = (double) info.get("scalingPercentage");
            return true;
        }
        catch (Exception e)
        {
            assignDefaultValues();
            //e.printStackTrace();
            return false;
        }
    }
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
