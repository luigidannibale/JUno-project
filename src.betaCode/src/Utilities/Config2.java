package Utilities;

import View.DeckColor;
import View.GraphicQuality;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;


public class Config2 {

    private int effectsVolume;
    private int musicVolume;
    private DeckColor deckStyle;
    private GraphicQuality graphicQuality;
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
        if(!readFromFile())
            assignDefaultValues();
    }
    public void assignDefaultValues()
    {
        this.effectsVolume = 50;
        this.musicVolume = 50;
        this.deckStyle = DeckColor.WHITE;
        this.graphicQuality = GraphicQuality.LOW;
    }

    public Config2(int effectsVolume, int musicVolume, DeckColor deckStyle,GraphicQuality graphicQuality)
    {
        this.effectsVolume = effectsVolume;
        this.musicVolume = musicVolume;
        this.deckStyle = deckStyle;
        this.graphicQuality = graphicQuality;
    }

    public boolean writeOnFile()
    {
        JSONObject info = new JSONObject();

        info.put("effectsVolume",effectsVolume);
        info.put("musicVolume",musicVolume);
        info.put("deckStyle",deckStyle.VALUE);
        info.put("graphicQuality",graphicQuality.VALUE);

        try (FileWriter file = new FileWriter(fileName))
        {
            file.write(info.toString());
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean readFromFile()
    {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            JSONObject info = new JSONObject(br.readLine());
            effectsVolume = Integer.parseInt((String) info.get("effectsVolume"));
            musicVolume = Integer.parseInt((String) info.get("musicVolume"));
            graphicQuality = GraphicQuality.valueOf((String)info.get("graphicQuality"));
            deckStyle = DeckColor.valueOf((String)info.get("deckStyle"));
            return true;
        } catch (IOException e){
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
