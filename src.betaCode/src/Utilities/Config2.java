package Utilities;

import View.DeckColor;
import View.GraphicQuality;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.util.HashMap;


public class Config2 {

    private int effectsVolume;
    private int musicVolume;
    private DeckColor deckStyle;
    private GraphicQuality graphicQuality;
    private double scalingPercentage;
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
    public Config2()
    {
        if(!loadConfig())
            assignDefaultValues();
    }
    public void assignDefaultValues()
    {
        this.effectsVolume = 50;
        this.musicVolume = 50;
        this.deckStyle = DeckColor.WHITE;
        this.graphicQuality = GraphicQuality.LOW;
        this.scalingPercentage = 1;
    }

    public Config2(int effectsVolume, int musicVolume, DeckColor deckStyle,GraphicQuality graphicQuality, double scalingPercentage)
    {
        this.effectsVolume = effectsVolume;
        this.musicVolume = musicVolume;
        this.deckStyle = deckStyle;
        this.graphicQuality = graphicQuality;
    }

    public boolean saveConfig()
    {
        HashMap<String,Object> info = new HashMap<>();

        info.put("effectsVolume",effectsVolume);
        info.put("musicVolume",musicVolume);
        info.put("deckStyle",deckStyle.VALUE);
        info.put("graphicQuality",graphicQuality.VALUE);
        info.put("scalingPercentage",scalingPercentage);

        FileManager fm = new FileManager();
        return fm.writeJson(new HashMap[]{info},fileName);
    }
    public boolean loadConfig()
    {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            JSONObject info = new JSONObject(br.readLine());
            effectsVolume = Integer.parseInt(info.getString("effectsVolume"));
            musicVolume = Integer.parseInt( info.getString("musicVolume"));
            graphicQuality = GraphicQuality.valueOf(info.getString("graphicQuality"));
            deckStyle = DeckColor.valueOf(info.getString("deckStyle"));
            scalingPercentage = Double.parseDouble(info.getString("scalingPercentage"));
            return true;
        }
        catch (IOException e)
        {
            assignDefaultValues();
            e.printStackTrace();
            return false;
        }
    }

    //#region ---getters and setters
    public int getEffectsVolume() { return effectsVolume; }
    public int getMusicVolume() { return musicVolume; }
    public DeckColor getDeckStyle() { return deckStyle; }
    public GraphicQuality getGraphicQuality() { return graphicQuality; }
    public void setMusicVolume(int musicVolume) { this.musicVolume = musicVolume; }
    public void setEffectsVolume(int effectsVolume) { this.effectsVolume = effectsVolume; }
    public void setDeckStyle(DeckColor deckStyle) { this.deckStyle = deckStyle; }
    public void setGraphicQuality(GraphicQuality graphicQuality) { this.graphicQuality = graphicQuality; }
}
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
